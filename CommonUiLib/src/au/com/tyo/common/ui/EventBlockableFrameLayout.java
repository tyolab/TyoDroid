/*
 * Copyright (C) 2015 TYONLINE TECHNOLOGY PTY. LTD.
 *
 */

package au.com.tyo.common.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class EventBlockableFrameLayout extends FrameLayout {
	
	private boolean toBlock = false;
	
	public EventBlockableFrameLayout(Context context) {
		super(context);

	}

	public EventBlockableFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public EventBlockableFrameLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
	}
	
	@SuppressLint("NewApi")
	public EventBlockableFrameLayout(Context context, AttributeSet attrs,
			int defStyle, int defStyleRes) {
		super(context, attrs, defStyle, defStyleRes);
		
	}
	
	public boolean isToBlock() {
		return toBlock;
	}

	public void setToBlock(boolean toBlock) {
		this.toBlock = toBlock;
	}

	@Override
	public boolean onInterceptTouchEvent (MotionEvent ev){
	    return toBlock;
	}
}
