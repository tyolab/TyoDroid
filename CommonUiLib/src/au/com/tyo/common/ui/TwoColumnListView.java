/*
 * Copyright (C) 2015 TYONLINE TECHNOLOGY PTY. LTD.
 *
 */

package au.com.tyo.common.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView.OnItemClickListener;


public class TwoColumnListView extends CommonListView {


	public TwoColumnListView(Context context) {
		super(context);
		
		init(context);
	}

	public TwoColumnListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		init(context);
	}

	@SuppressLint("NewApi")
	public TwoColumnListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		init(context);
	}
	
	private void init(Context context) {
		
	}
	
}
