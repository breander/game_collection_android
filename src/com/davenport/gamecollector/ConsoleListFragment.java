package com.davenport.gamecollector;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.davenport.gamecollector.Platform;
import com.davenport.gamecollector.ParsePlatforms;
import com.davenport.gamecollector.MainTabsFragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class ConsoleListFragment extends android.app.ListFragment implements OnScrollListener {
	
	List<Platform> lstPlatforms = new ArrayList<Platform>();
	List<String> names = new ArrayList<String>();
	ArrayAdapter<String> adapter; 
	int offset = 0;
	boolean loading = false;
	int limit = 20;
	String APIKEY = "?api_key=90fbab5cb7aa63ea95938c3a39f19d2049308e40";
	String Website = "http://api.giantbomb.com";
	boolean mDualPane;
	int mCurCheckPosition = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getListView().setOnScrollListener(this);
		View detailsFrame = getActivity().findViewById(R.id.details);
		mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
		
		if(savedInstanceState != null) {
			mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
		}
		
		if(mDualPane){
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			//showDetails(mCurCheckPosition);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		
		showDetails(position);
	}
	
	private void showDetails(int index)
	{
		String item = (String) getListAdapter().getItem(index);
		
		if(mDualPane){
			getListView().setItemChecked(index, true);
			Platform selected = null;
			for(Platform plat : lstPlatforms){
				if(plat.name == item)
				{
					selected = plat;
				}
			}
			MainTabsFragment tabs = (MainTabsFragment) getFragmentManager().findFragmentById(R.id.details);
			
			//if(tabs == null)
			{
				tabs = new MainTabsFragment(selected);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.details, tabs);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		}
	}
	
	private class DownloadWebPageTask extends AsyncTask<String, Void, String>  {
		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			
	        for (String url : urls) {
	        	DefaultHttpClient client = new DefaultHttpClient();
	        	HttpGet httpGet = new HttpGet(url);
	        	try {
	        		HttpResponse execute = client.execute(httpGet);
	        		InputStream content = execute.getEntity().getContent();

	        		BufferedReader buffer = new BufferedReader(
	        				new InputStreamReader(content));
	        		String s = "";
	        		while ((s = buffer.readLine()) != null) {
	        			response += s;
	        			
					
	        		}

	        	} catch (Exception e) {
	        		e.printStackTrace();
	        	}
	        }
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			
			ParsePlatforms sp = new ParsePlatforms(result);
			sp.parseDocument();
			lstPlatforms.addAll(sp.ReturnList());
			
			names = new ArrayList<String>();
			
			for(Platform plat : sp.ReturnList()){
				
				if(plat.name != "")
				{
					String name = plat.name;
					names.add(name);
				}
			}
			
			if(adapter == null)
			{
				adapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_list_item_1, names);
				setListAdapter(adapter);
				loading = false;
			}
			else
			{
				adapter.addAll(names);
				loading = false;
			}
		}
	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
		
		if(loadMore && !loading)
		{
			loading = true;
			DownloadWebPageTask task = new DownloadWebPageTask();
			task.execute(new String[] { Website + "/platforms/" + APIKEY + "&offset=" + offset + "&limit=" + limit + "&format=xml" });
			offset += limit;
		}
		
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
}