package au.com.tyo.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import au.com.tyo.Debug;

public abstract class CommonApplicationImpl  extends Application implements CommonApplication {
	
	protected Context context;
	
	protected Handler msgHandler;

	protected NotificationManager notificationManager;
	
	protected static Object instance;
	
	protected Class preferenceActivityClass;
	
	protected Class mainActivityClass;
	
	protected Class splashScreenClass;
	
	protected boolean hasAd;
	
	protected Activity mainActivity;
	
	protected String appName = "";

	protected String version = "0.9.9"; // that is the number to show something wrong
	
	protected int logoResId;
	
	protected int backKeyCount;
	
	public CommonApplicationImpl() {
		notificationManager = null;
	}
	
	public CommonApplicationImpl(Context context) {
		this.context = context;
		
		 notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
	}
	
	public static Object getInstance() {
		return instance;
	}
	
	public static void initializeInstance(Context context, Class<?> theClass) {
		if (instance == null)
			try {
				instance = theClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		
		CommonApplication ca = (CommonApplication) instance;
		if (ca.getContext() == null && context != null) {
			ca.setContext(context);
			
			ca.initializeOnMainThread(context);
			ca.initializeOnBackground(context);
		}
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch(keyCode) {
        case KeyEvent.KEYCODE_BACK:
            if (event.isTracking() && !event.isCanceled()) {
                onBackKeyPressed();
                return true;
            }
            break;
        }
		return false;
	}
	
	@SuppressLint("NewApi")
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean hasNoModifiers = true;
		
        if (AndroidUtils.getAndroidVersion() > android.os.Build.VERSION_CODES.HONEYCOMB /*>=11*/) {
	        hasNoModifiers = event.hasNoModifiers();
//	        ctrl = event.hasModifiers(KeyEvent.META_CTRL_ON);
//	        shift = event.hasModifiers(KeyEvent.META_SHIFT_ON);
        }
        
        switch(keyCode) {
        case KeyEvent.KEYCODE_VOLUME_UP:
        case KeyEvent.KEYCODE_VOLUME_DOWN:

        	break;
			
        case KeyEvent.KEYCODE_BACK:
        	if (hasNoModifiers) 
        		event.startTracking();
        	break;
		}
		return false;
	}
	
	protected void onBackKeyPressed() {
		quit();
	}
	
	protected void quit() {
		Dialog dialog = DialogFactory.createExitPromtDialog(context, this.getAppName(), 
				new DialogInterface.OnClickListener() {
	
					@Override
					public void onClick(DialogInterface dialog, int which) {
						quitOrRestart(false);
					}
					
				}, new DialogInterface.OnClickListener() {
	
					@Override
					public void onClick(DialogInterface dialog, int which) {
						backKeyCount = 0;
					}
					
				});
		showDialog(dialog);
	}
	
//	public void initializeUserInterface(CommonUI ui) {
//		Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
//		int width = display.getWidth();
//		int height = display.getHeight();
//		
//   		int rotation = display.getOrientation();
//   		if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270)
//   			ui.setOrientation(Configuration.ORIENTATION_LANDSCAPE);
//   		else
//   			ui.setOrientation(Configuration.ORIENTATION_PORTRAIT);
//		ui.setScreenSize(width, height);
//		
//	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		logoResId = R.drawable.ic_logo;
	}

	public Handler getMessageHandler() {
		return msgHandler;
	}

	public void setMessageHandler(Handler msgHandler) {
		this.msgHandler = msgHandler;
	}

	@Override
	public void sendMessage(int msgId) {
		sendMessage(msgId, null);
	}
	
	@Override
	public void sendMessage(int msgId, Object content) {
		if (msgHandler == null)
			return;
		
        Message msg = Message.obtain();
        msg.what = msgId;
        msg.obj = content;
        msgHandler.sendMessage(msg);
    }

	public Context getContext() {
		return context;
	}

	@Override
	public void setContext(Context context) {
		this.context = context;
	}
	
	@Override
	public NotificationManager getNotificationManager() {
		return notificationManager;
	}
	
	@Override
	public void quitOrRestart(boolean restart) {
		quitOrRestart(context, restart);
	}
	
	public static void quitOrRestart(Context context, boolean restart) {
    	Activity activity = ((Activity) context);
    	quitOrRestart(activity, restart);
	}
	
	@SuppressLint("NewApi")
	public static void quitOrRestart(Activity activity, boolean restart) {		
    	if (restart) {
    		if (AndroidUtils.getAndroidVersion() >= 11)
    			activity.recreate();
    		else {
				Intent intent = activity.getIntent();
    			activity.finish();
				activity.startActivity(intent);
    		}
    	}
    	else {
//    		activity.moveTaskToBack(true);
    		activity.finish();
    	}
	}
	
	@Override
	public Class getMainActivityClass() {
		return mainActivityClass;
	}

	public void setMainActivityClass(Class cls) {
		mainActivityClass = cls;
	}
	
	@Override
	public  Class getPreferenceActivityClass() {
		return preferenceActivityClass;
	}

	public void setPreferenceActivityClass(Class cls) {
		preferenceActivityClass = cls;
	}

	@Override
	public Class getSplashScreenClass() {
		return splashScreenClass;
	}

	public void setSplashScreenClass(Class splashScreenClass) {
		this.splashScreenClass = splashScreenClass;
	}	

	@Override
	public boolean hasAd() {
		return hasAd;
	}

	public void setHasAd(boolean hasAd) {
		this.hasAd = hasAd;
	}
	
	@Override
	public void startSplashScreenActivity(Context context) {
		startActivity(context, getSplashScreenClass());
	}

	@Override
	public void startMainActivity() {
		startActivity(getMainActivityClass());
	}
	
	public void startActivity(Class cls) {
		if (context == null)
			context = this.getApplicationContext();
		startActivity(context, cls);
	}
	
	public static void startActivity(Context context, Class cls) {
        Intent i = new Intent(context, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
	}
	
	@Override
	public void setActivityContext(Activity activity) {
		this.mainActivity = activity;
	}
	
	@Override
	public Activity getActivityContext() {
		return this.mainActivity;
	}
	
	@Override
	public void setAdStatus(Context context) {
		hasAd = context.getResources().getBoolean(R.bool.showAd);

		if (hasAd && !Debug.debugging) 
			hasAd = false;
	}

	public String getAppName() {
	    if ((appName == null || appName.length() == 0)) {
	    	Context appContext = context;
	    	if (appContext == null)
	    		appContext = getContext();
	    	
	    	if (null != appContext)
	    		appName = context.getResources().getString(R.string.app_name);
	    }
	    	
		return appName;
	}
	
	public String getAppNameWithVersion() {
    	Context appContext = context;
    	if (appContext == null)
    		appContext = getContext();
    	
    	if (null != appContext)
    		return String.format(getAppName() + " (%s)"/*context.getResources().getString(R.string.app_desc), getVersion()*/,  AndroidUtils.getPackageVersionName(appContext));
    	
    	return "";
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}	
	
	@Override
	public boolean onOptionsItemSelected(Activity activity, android.view.MenuItem item) {
	    int itemId = item.getItemId();
	    
	    if (itemId == R.id.menuItemAbout)
			showInfo();
		else
			return true;
		return false;
	}
	
	@Override
	public void showInfo() {
		showInfo(false);
	}
	
	protected void showInfo(boolean showAcknowledgement) {
    	// Inflate the about message contents
	    View messageView = ((Activity) context).getLayoutInflater().inflate(R.layout.info_dialog, null, false);
	    View acknowledgement = messageView.findViewById(R.id.acknowledge_view);
	    if (showAcknowledgement)
	    	acknowledgement.setVisibility(View.VISIBLE);

	    
	    String appDesc = getAppNameWithVersion();
	    
	    // When linking text, force to always use default color. This works
	    // around a pressed color state bug.
//	    TextView textView = (TextView) messageView.findViewById(R.id.info_credits);
//	    int defaultColor = textView.getTextColors().getDefaultColor();
//	    textView.setTextColor(defaultColor);
	
	    AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    builder.setIcon(logoResId);
	    builder.setTitle(appDesc);
	    builder.setView(messageView);
	    Dialog dialog = builder.create();
	    showDialog(dialog);
//	    builder.show();	
	}
	
	protected void showDialog(Dialog dialog) {
		if(dialog != null && !((Activity) context).isFinishing())
			dialog.show();
	}

	@Override
	public String getVersion() {
		return version;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		mainActivity.getMenuInflater().inflate(R.menu.common_menu, menu);
		return true;
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public void onPostCreate(Bundle savedInstanceState) {
	}
}
