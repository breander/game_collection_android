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
import android.os.AsyncTask;
import android.webkit.WebView;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.graphics.*;

public class ConsoleDescriptionFragment extends Fragment implements LoaderCallbacks<Void> {
	private Platform _platform;
	String APIKEY = "?api_key=90fbab5cb7aa63ea95938c3a39f19d2049308e40";
	String Website = "http://api.giantbomb.com";
	
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
		
		DownloadWebPageTask task = new DownloadWebPageTask();
		task.execute(new String[] { Website + "/platform/" + _platform.id + "/" + APIKEY + "&field_list=description&format=xml" });
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
			
			WebView desc = (WebView) getView().findViewById(R.id.descriptionText);
			if(desc != null)
			{
				if(_platform != null)
				{
					//result = result.replace("OK1110","W");
					
					
					
					//Spanned marked_up = Html.fromHtml(result);
					//desc.setMovementMethod(new ScrollingMovementMethod());
					desc.setBackgroundColor(Color.BLACK);
					desc.scrollTo(0, 0);
					desc.loadData(result, "text/html", null);
					//desc.setText(marked_up);
					//desc.setText(result);
					//desc.setBackgroundColor(Color.parseColor("#000000"));
					//desc.setBackgroundColor(Color.GRAY);
					desc.setFocusableInTouchMode(false);
					desc.setFocusable(false);
					
				}
				else
				{
					//desc.setText("Null");
				}
			}
			return;
			
			//ParsePlatforms sp = new ParsePlatforms(result);
			//sp.parseDocument();

			//for(Platform plat : sp.ReturnList()){

				//if(plat == null)
				//{
					//TextView desc = (TextView) getView().findViewById(R.id.descriptionText);
					//if(desc != null)
					//{
						//if(_platform != null)
						//{
							//Spanned marked_up = Html.fromHtml(plat.description);
							//desc.setMovementMethod(new ScrollingMovementMethod());
							//desc.scrollTo(0, 0);
							//desc.setText(marked_up);
						//}
						//else
						//{
							//desc.setText("Null");
						//}
					//}
					//break;
				//}
			//}
		}
	}
}
