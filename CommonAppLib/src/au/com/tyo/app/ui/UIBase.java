/*
 * Copyright (C) 2015 TYONLINE TECHNOLOGY PTY. LTD.
 *
 */

package au.com.tyo.app.ui;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import au.com.tyo.app.Controller;
import au.com.tyo.app.R;

public class UIBase implements UI {
	
	private static final String LOG_TAG = "GUI";

	protected View mainUi;
	
	protected ViewGroup footerView;
	
	protected ViewGroup headerView;
	
	protected AllAdView ad;
	
	private Controller controller;
	
	private SuggestionView suggestionView;
	
	private SearchView searchView;
	
	private ViewGroup contentView;
	
	private ViewGroup bodyView;
	
	private boolean alwaysShowSearchView;
	
	protected Context context;

	public UIBase(Controller controller) {
		this.controller = controller;
		alwaysShowSearchView = true;
	}

	@Override
	public boolean recreationRequried() {
		return false;
	}

	@Override
	public void assignMainUiContainer(FrameLayout frameLayout) {
		if (mainUi.getParent() != null)
			((ViewGroup) mainUi.getParent()).removeView(mainUi);
		mainUi.setVisibility(View.VISIBLE);
        frameLayout.addView(mainUi);
	}
	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void initializeUi(View v) {
		mainUi = v;
		initializeUi(v.getContext());
	}

	@Override
	public void initializeUi(Context context) {
		if (null == mainUi)
			mainUi = LayoutInflater.from(context).inflate(R.layout.activity_main, null);
        this.context = context;
        
		setupComponents();
		
		if (!alwaysShowSearchView)
			hideSearchView();
	}

	private void hideSearchView() {
		if (null != searchView)
			searchView.setVisibility(View.GONE);
	}

	@Override
	public SearchInputView getSearchInputView() {
		return searchView.getSearchInputView();
	}

	@Override
	public void setSuggestionViewVisibility(boolean b) {
		if (b) {
			ad.hide();
			suggestionView.setVisibility(View.VISIBLE);
			contentView.setVisibility(View.GONE);
		}
		else {
			ad.show();
			contentView.setVisibility(View.VISIBLE);
			suggestionView.setVisibility(View.GONE);
		}
	}

	@Override
	public SuggestionView getSuggestionView() {
		return suggestionView;
	}

	/**
	 * do whatever it needs to be done, for example, when search input is focused
	 * lock the drawer views
	 */
	@Override
	public void onSearchInputFocusStatus(boolean focused) {
		
}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (null != ad)
			ad.loadBannerAd();
	}
	
	public void setupComponents() {
		bodyView = (ViewGroup) mainUi.findViewById(R.id.body_view);
		
        footerView = (ViewGroup) mainUi.findViewById(R.id.footer_view);
        headerView = (ViewGroup) mainUi.findViewById(R.id.header_view);
        
        if (hasSearchBar())
        	setupSearchView();
        
		ad = (AllAdView) mainUi.findViewById(R.id.all_ad_view);
		if (null != ad) {
			addAdView();
			
			if (controller.getSettings().hasAd())
				ad.loadBannerAd();
		}
		
		if (!controller.getNetworkMonitor().hasInternet())
			onNetworkDisonnected();
		
	}
	
	private boolean hasSearchBar() {
		return null != mainUi.findViewById(R.id.search_nav_bar);
	}
	
	/* 
	 * not all the need search function 
	 * keep these line for future use
	 */
	public void setupSearchView() {
		contentView = (ViewGroup) mainUi.findViewById(R.id.content_view);
		
		searchView = (SearchView) mainUi.findViewById(R.id.search_nav_bar);	
	    searchView.setupComponents(controller);
		
		suggestionView = (SuggestionView) mainUi.findViewById(R.id.suggestion_view);
		suggestionView.setupComponents(controller);
	}

	public ViewGroup getFooterView() {
		return footerView;
	}

	public ViewGroup getHeaderView() {
		return headerView;
	}

	public SearchView getSearchView() {
		return searchView;
	}

	public ViewGroup getContentView() {
		return contentView;
	}

	public ViewGroup getBodyView() {
		return bodyView;
	}

	protected void addAdView() {
		ad.initialize(controller, footerView);
	}

	@Override
	public void hideAd() {
		ad.hide();
	}

	@Override
	public void showAd() {
		ad.show();
	}
	
	protected void showDialog(Dialog dialog) {
		if(dialog != null && ! controller.getActivityContext().isFinishing())
			dialog.show();
	}

	@Override
	public View getMainUi() {
		return mainUi;
	}

	@Override
	public void onNetworkDisonnected() {
//		footerView.setVisibility(View.GONE);
		hideAd();
	}

	@Override
	public void onNetworkConnected() {
//		footerView.setVisibility(View.VISIBLE);
		showAd();
	}

	@Override
	public void onAdLoaded() {
		Log.d(LOG_TAG, "Ad loaded");
		showAd();
	}

	/**
	 * normally we set the action bar like this
	 */
	@Override
	public void setupActionBar(Object barObj) {
		
		if (barObj != null) {
			if (barObj instanceof android.app.ActionBar) {
				android.app.ActionBar bar = (ActionBar) barObj;
			}
			else if (barObj instanceof android.support.v7.app.ActionBar) {
				android.support.v7.app.ActionBar bar = (android.support.v7.app.ActionBar) barObj;
		        bar.setLogo(R.drawable.ic_logo);
		//      bar.setIcon(R.drawable.ic_launcher);
		      bar.setDisplayUseLogoEnabled(true);
		//      bar.setHomeButtonEnabled(true);
		//      bar.setDisplayHomeAsUpEnabled(true);
		//      bar.setDisplayShowCustomEnabled(true);
		      bar.setDisplayShowHomeEnabled(true);
		      bar.setDisplayShowTitleEnabled(false);
			}
		}
	}
}
