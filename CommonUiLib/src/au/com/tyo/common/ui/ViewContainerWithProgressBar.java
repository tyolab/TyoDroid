package au.com.tyo.common.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import au.com.tyo.common.ui.R;

public class ViewContainerWithProgressBar extends FrameLayout {
	
	private ViewGroup progressBarContainer;
	
	private ViewGroup viewContainer;
	
	private int viewContainerResId = R.id.view_container;
	
	private int progressBarContainerResId = R.id.container_progress_bar;

	public ViewContainerWithProgressBar(Context context) {
		super(context);

	}

	public ViewContainerWithProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public ViewContainerWithProgressBar(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
	}
	
	public int getViewContainerResId() {
		return viewContainerResId;
	}

	public void setViewContainerResId(int viewContainerResId) {
		this.viewContainerResId = viewContainerResId;
	}

	public int getProgressBarResId() {
		return progressBarContainerResId;
	}

	public void setProgressBarResId(int progressBarResId) {
		this.progressBarContainerResId = progressBarResId;
	}

	public void inflateFromDefaultLayoutResource() {
        LayoutInflater factory = LayoutInflater.from(this.getContext());
        factory.inflate(R.layout.container_and_progressbar, this);
	}

	public ViewGroup getViewContainer() {
		return viewContainer;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		if (null == findViewById(R.id.view_container))
			inflateFromDefaultLayoutResource();
		
		setupComponents();
	}
	
	public void setupComponents() {
		
       viewContainer = (ViewGroup) findViewById(viewContainerResId);
        
       progressBarContainer = (ViewGroup) findViewById(progressBarContainerResId);
       
       if (null != progressBarContainer) {
//    	   progressBarContainer.setVisibility(View.GONE);
//           FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//           params.gravity = Gravity.CENTER;
//           
//    	   progressBar.setLayoutParams(params);
       }
	}
	
	public void hideProgressBar() {
		if (null != progressBarContainer)
			progressBarContainer.setVisibility(View.GONE);
		viewContainer.setVisibility(View.VISIBLE);
	}
	
	public void showProgressBar() {
		viewContainer.setVisibility(View.GONE);
		if (null != progressBarContainer)
			progressBarContainer.setVisibility(View.VISIBLE);
	}
	
	public void addContentView(View view) {
		viewContainer.removeAllViews();
		viewContainer.addView(view);
	}
	
	public void addContentView(int resourceid) {
		viewContainer.removeAllViews();
		
        LayoutInflater factory = LayoutInflater.from(this.getContext());
        factory.inflate(resourceid, viewContainer);
	}
	
	public void hide() {
		this.setVisibility(GONE);
	}
	
	public void show() {
		this.setVisibility(VISIBLE);
	}
}
