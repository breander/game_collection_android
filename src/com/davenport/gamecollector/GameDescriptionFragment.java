package com.davenport.gamecollector;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GameDescriptionFragment extends Fragment {
	
	private Platform _platform;
	
	GameDescriptionFragment(){
		
	}
	
	GameDescriptionFragment(Platform platform){
		_platform = platform;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.game_description, container, false);
		return view;
	}
}
