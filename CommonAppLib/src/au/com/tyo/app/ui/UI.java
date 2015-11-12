/*
 * Copyright (C) 2015 TYONLINE TECHNOLOGY PTY. LTD.
 *
 */

package au.com.tyo.app.ui;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @author Eric Tang <eric.tang@tyo.com.au>
 */

public interface UI {

	boolean recreationRequried();

	void assignMainUiContainer(FrameLayout frameLayout);

	void initializeUi(Context context);

	SearchInputView getSearchInputView();

	void setSuggestionViewVisibility(boolean b);

	SuggestionView getSuggestionView();

	void onSearchInputFocusStatus(boolean focused);

	void onConfigurationChanged(Configuration newConfig);

	void hideAd();

	void showAd();

	View getMainUi();

	void onNetworkDisonnected();

	void onNetworkConnected();

	void initializeUi(View v);

	void onAdLoaded();

	void setupActionBar(Object bar);

	void hideMainProgressBar();

	void hideSuggestionView();

}
