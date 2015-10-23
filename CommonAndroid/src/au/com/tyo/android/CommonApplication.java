package au.com.tyo.android;

import java.util.Observer;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public interface CommonApplication extends Observer {
	
//	void initializeUserInterface(CommonUI ui);
	
	void onResume();
	
	void onDestroy();
	
	void onCreate();
	
	void onPause();
	
	boolean onKeyDown(int keyCode, KeyEvent event);

	boolean onKeyUp(int keyCode, KeyEvent event);
	
	void onStop();
	
	Context getContext();
	
	void setContext(Context context);
	
	void quitOrRestart(boolean restart);
	
	void sendMessage(int msgId);
	
	void sendMessage(int msgId, Object content);

	NotificationManager getNotificationManager();

	Class getSplashScreenClass();

	Class getPreferenceActivityClass();

	Class getMainActivityClass();

	boolean hasAd();

	void startSplashScreenActivity(Context context);

	void startMainActivity();
	
	void setActivityContext(Activity activity);
	
	void onSaveInstanceState(Bundle savedInstanceState);

	void onRestoreInstanceState(Bundle savedInstanceState);

//	void onScaleChanged(float oldScale, float newScale);

	Activity getActivityContext();

	void initializeOnMainThread(Context context);

	void initializeOnBackground(Context context);

	void setAdStatus(Context context);

	boolean onOptionsItemSelected(Activity activity, MenuItem item);

	String getVersion();

	boolean onCreateOptionsMenu(Menu menu);

	void onPrepareOptionsMenu(Menu menu);

	void onActivityResult(int requestCode, int resultCode, Intent data);

	boolean onKeyLongPress(int keyCode, KeyEvent event);

	void onPostCreate(Bundle savedInstanceState);

	void showInfo();
}
