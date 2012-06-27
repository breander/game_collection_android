package com.davenport.gamecollector;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainTabsFragment extends Fragment implements OnTabChangeListener{
	public static final String TAB_CONSOLE_DESCRIPTION = "Console Description";
	public static final String TAB_GAMES = "Games";
	public static final String TAB_GAMES_DESCRIPTION = "Games Description";
	
	private View mRoot;
	private TabHost mTabHost;
	private int mCurrentTab;
	
	private Platform _platform;
	
	MainTabsFragment(){
		
	}
	
	MainTabsFragment(Platform platform){
		_platform = platform;
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		mRoot = inflater.inflate(R.layout.main_tabs, null);
		mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
		setupTabs();
		return mRoot;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		
		mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(mCurrentTab);
		onTabChanged(TAB_CONSOLE_DESCRIPTION);
	}
	
	public void setupTabs()
	{
		mTabHost.setup();
		mTabHost.addTab(newTab(TAB_CONSOLE_DESCRIPTION, R.string.tab_console_description, R.id.tab1));
		mTabHost.addTab(newTab(TAB_GAMES, R.string.tab_games, R.id.tab2));
		mTabHost.addTab(newTab(TAB_GAMES_DESCRIPTION, R.string.tab_games_description, R.id.tab3));
	}
	
	private TabSpec newTab(String tag, int labelId, int tabContentId){
		View indicator = LayoutInflater.from(getActivity()).inflate(R.layout.tab, (ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
		((TextView) indicator.findViewById(R.id.text)).setText(labelId);
		
		TabSpec tabSpec = mTabHost.newTabSpec(tag);
		tabSpec.setIndicator(indicator);
		tabSpec.setContent(tabContentId);
		return tabSpec;
	}
	
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		FragmentManager fm = getFragmentManager();
		
		if(TAB_CONSOLE_DESCRIPTION.equals(tabId)) {
			fm.beginTransaction().replace(R.id.tab1, new ConsoleDescriptionFragment(_platform)).commit();
			return;
		}
		if(TAB_GAMES.equals(tabId)){
			fm.beginTransaction().replace(R.id.tab2, new GameListFragment(_platform)).commit();
			return;
		}
		if(TAB_GAMES_DESCRIPTION.equals(tabId)){
			fm.beginTransaction().replace(R.id.tab3, new GameDescriptionFragment(_platform)).commit();
			return;
		}
	}
}
