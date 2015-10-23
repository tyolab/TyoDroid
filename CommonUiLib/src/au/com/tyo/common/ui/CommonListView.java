/*
 * Copyright (C) 2015 TYONLINE TECHNOLOGY PTY. LTD.
 *
 */

package au.com.tyo.common.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CommonListView extends FrameLayout /*implements OnItemClickListener*/ {
	
	protected ListView list;
	
	public CommonListView(Context context) {
		super(context);
		init(context);
	}

	public CommonListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	@SuppressLint("NewApi")
	public CommonListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
//		createListView();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		
		/* stop something stealing the touch event */
//		setOnTouchListener(new View.OnTouchListener() {
//	        @Override
//	        public boolean onTouch(View view, MotionEvent motionEvent) {
//	            view.getParent().requestDisallowInterceptTouchEvent(true);
//	            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
//	            case MotionEvent.ACTION_UP:
//	                view.getParent().requestDisallowInterceptTouchEvent(false);
//	                break;
//	            }
//	            return false;
//	        }
//	    });
	}

	public ListAdapter getAdapter() {
		return list.getAdapter();
	}
	
	public void setAdapter(ListAdapter adapter) {
		list.invalidateViews();
		list.setAdapter(adapter);
	}
	
	public void createListView() {
		list = createListView(this.getContext(), this);
		list.requestFocus();
	}
	
	public boolean lookForListView(int resId) {
		list = (ListView) findViewById(resId);
		return list != null;
	}
	
	public static ListView createListView(Context context, ViewGroup parent) {
        LayoutInflater factory = LayoutInflater.from(context);
        ListView list = (ListView) factory.inflate(R.layout.common_list_view, null);
        if (null != parent)
        	parent.addView(list);
        
//      list = (ListView) findViewById(R.id.common_list_view);
      
//		list = new ListView(context);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		params.gravity = Gravity.CENTER_HORIZONTAL;	
		list.setLayoutParams(params);  
//		list.setOnTouchListener(new View.OnTouchListener() {
//			 
//		    public boolean onTouch(View v, MotionEvent event) {
//		        return (event.getAction() == MotionEvent.ACTION_MOVE);
//		    }
//		});
        return list;
	}
	
//	@Override
//	protected void onFinishInflate() {
//		super.onFinishInflate();
////        list = (ListView) findViewById(R.id.common_list_view);
////		this.addContentView(list);
//	}
	
	public static boolean doesItemsFitInScreen(ListView list) {
		if (list.getChildCount() == 0)
			return true;
		int last = list.getLastVisiblePosition();
		return last == list.getCount() - 1 && list.getChildAt(last).getBottom() <= list.getHeight();
	}
	
	public boolean doesItemsFitInScreen() {
		return doesItemsFitInScreen(list);
	}
	
//	@Override 
//	public boolean onTouchEvent(MotionEvent ev) {
//	   for(int i = 0; i < this.getChildCount(); i++){
//	      this.getChildAt(i).dispatchTouchEvent(ev);
//	   } 
//	   return true;
//	} 

}
