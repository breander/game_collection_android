package com.davenport.gamecollector;

import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ConsoleDescriptionFragment extends Fragment implements LoaderCallbacks<Void> {
	private Platform _platform;
	
	ConsoleDescriptionFragment(){
		
	}
	
	ConsoleDescriptionFragment(Platform platform){
		_platform = platform;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		TextView title = (TextView) getView().findViewById(R.id.detailsText);
		if(title != null)
		{
			if(_platform != null){
				
				title.setText(_platform.name);
			}
			else
			{
				title.setText("Null");
			}
		}
		
		TextView desc = (TextView) getView().findViewById(R.id.descriptionText);
		if(desc != null)
		{
			if(_platform != null)
			{
				Spanned marked_up = Html.fromHtml(_platform.description);
				desc.setMovementMethod(new ScrollingMovementMethod());
				desc.scrollTo(0, 0);
				desc.setText(marked_up);
			}
			else
			{
				desc.setText("Null");
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.console_description, container, false);
		
		return view;
	}
	
	public void onLoadFinished(Loader<Void> loader, Void result){
		
	}

	public Loader<Void> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		
		
		
		return null;
	}

	public void onLoaderReset(Loader<Void> arg0) {
		// TODO Auto-generated method stub
		
	}
}