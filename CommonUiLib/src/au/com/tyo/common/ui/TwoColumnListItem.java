/*
 * Copyright (C) 2015 TYONLINE TECHNOLOGY PTY. LTD.
 *
 */

package au.com.tyo.common.ui;

import java.text.DecimalFormat;

import android.content.Context;
import android.database.MatrixCursor;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;

public class TwoColumnListItem extends LinearLayout {

	public TwoColumnListItem(Context context) {
		super(context);
	}

	public void initListView()
	{
	    final String   AuthorName    = "Author: ";
	    final String   CopyrightName = "CopyRight: ";
	    final String   PriceName     = "Price: ";

	    final String[] matrix  = { "_id", "name", "value" };
	    final String[] columns = { "name", "value" };
	    final int[]    layouts = { android.R.id.text1, android.R.id.text2 };

	    MatrixCursor  cursor = new MatrixCursor(matrix);

	    DecimalFormat formatter = new DecimalFormat("##,##0.00");
	    
	    int key = 0;

//	    cursor.addRow(new Object[] { key++, AuthorName, mAuthor });
//	    cursor.addRow(new Object[] { key++, CopyrightName, mCopyright });
//	    cursor.addRow(new Object[] { key++, PriceName,
//	            "$" + formatter.format(mPrice) });

//	    SimpleCursorAdapter data =
//	        new SimpleCursorAdapter(this,
//	                R.layout.viewlist_two_items,
//	                cursor,
//	                columns,
//	                layouts);
//
//	    setListAdapter( data );

	}   // end of initListView()
}
