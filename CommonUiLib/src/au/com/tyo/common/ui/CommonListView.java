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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class CommonListView extends RelativeLayout /*implements OnItemClickListener*/ implements OnScrollListener {
	
	protected ListView list;
	
	private int lastVisible;
	
	private onLastItemVisibleListener lastItemVisibleListener;
	
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
		lastItemVisibleListener = null;
	}
	
	public static interface onLastItemVisibleListener {
		
		public void onLastItemVisible();
		
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
	}
	
	public void setOnLastItemVisibleListener(onLastItemVisibleListener listener) {
		this.lastItemVisibleListener = listener;
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
		list.setOnScrollListener(this);
	}
	
	public boolean lookForListView(int resId) {
		list = (ListView) findViewById(resId);
		if (null != list)
			list.setOnScrollListener(this);
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
		return isLastItemVisible(list.getCount(), last) && isLastItemVisible(list);
	}
	
	public static boolean isLastItemVisible(ListView list) {
		return list.getChildAt(list.getLastVisiblePosition()).getBottom() <= list.getHeight();
	}
	
	public static boolean isLastItemVisible(int numberOfItems, int lastVisible) {
		return (lastVisible == numberOfItems - 1);
	}
	
	public boolean doesItemsFitInScreen() {
		lastVisible = list.getLastVisiblePosition();
		return isLastItemVisible(list.getCount(), lastVisible) && isLastItemVisible(list);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView lv, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		switch(lv.getId()) {
        	case android.R.id.list:
        		lastVisible = list.getLastVisiblePosition();
        		
        		final int lastItem = firstVisibleItem + visibleItemCount;
        		if (lastItem == totalItemCount) {
        			if (null != lastItemVisibleListener)
        				lastItemVisibleListener.onLastItemVisible();
        		}
        		break;
		}
	}
	
	public void removeDivider() {
		list.setDivider(null);
		list.setDividerHeight(0);
	}
	
//	@Override 
//	public boolean onTouchEvent(MotionEvent ev) {
//	   for(int i = 0; i < this.getChildCount(); i++){
//	      this.getChildAt(i).dispatchTouchEvent(ev);
//	   } 
//	   return true;
//	} 

}
