package com.davenport.gamecollector;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;

public class GameListFragment extends Fragment implements OnScrollListener {
	
	private Platform _platform;
	List<game> lstGames = new ArrayList<game>();
	List<String> names = new ArrayList<String>();
	ArrayAdapter<String> adapter; 
	int offset = 0;
	boolean loading = false;
	int limit = 100;
	//String APIKEY = "?api_key=90fbab5cb7aa63ea95938c3a39f19d2049308e40";
	//String Website = "http://api.giantbomb.com";
	String PlatformId = "";
	
	GameListFragment(){
		
	}
	
	GameListFragment(Platform platform){
		_platform = platform;
		
		if(_platform != null){
			PlatformId = _platform.name.replace(' ', '+');
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		GridView gridview = (GridView) getView().findViewById(R.id.gridView1);
		gridview.setOnScrollListener(this);
		
		DownloadWebPageTask task = new DownloadWebPageTask();
		task.execute(new String[] { "http://thegamesdb.net/api/PlatformGames.php?platform=" + PlatformId });
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.game_list, container, false);
		return view;
	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		//boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
		
		//if(loadMore && !loading)
		//{
		//	loading = true;
		//	DownloadWebPageTask task = new DownloadWebPageTask();
		//	task.execute(new String[] { Website + "/games/" + APIKEY + "&field_list=name,id&platforms=" + PlatformId + "&sort=name&offset=" + offset + "&limit=" + limit + "&format=xml" });
		//	offset += limit;
		//}
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
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
			
			ParseGames sp = new ParseGames(result);
			sp.parseDocument();
			lstGames.addAll(sp.ReturnList());
			
			names = new ArrayList<String>();
			
			for(game gm : sp.ReturnList()){
				
				if(gm.GameTitle != "")
				{
					String name = gm.GameTitle;
					names.add(name);
				}
			}
			
			if(adapter == null)
			{
				adapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_list_item_1, names);
				//setListAdapter(adapter);
				GridView gridview = (GridView) getView().findViewById(R.id.gridView1);
			    gridview.setAdapter(adapter);
				
				loading = false;
			}
			else
			{
				adapter.addAll(names);
				loading = false;
			}
		}
	}
}
