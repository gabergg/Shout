package com.example.shout;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

public class SearchActivity extends Activity {

	public final static String EXTRA_OUTPUT = "com.example.yelpyapper.OUTPUT";
	protected JSONObject myResults;
	protected LatLong latlong;
	protected Double rating;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seek_bar_grabber);
		latlong = new LatLong(this);		
		latlong.updateLocation();
		final Location location = latlong.getLocation();
		Intent intent = getIntent();
		
		new AsyncTask<SearchInput, Void, JSONObject>() {
			@Override
			protected JSONObject doInBackground(SearchInput... input) {
				Yelp yelp = new Yelp(getString(R.string.consumer_key), getString(R.string.consumer_secret),
						getString(R.string.token), getString(R.string.token_secret));
				String result = yelp.search("Bubble+Tea", "hey", 5);		

				JSONObject response;
				try {
					response = new JSONObject(result);
				} catch (JSONException e) {
					e.printStackTrace();
					return null;
				}
				return response;
			}

			@Override
			protected void onPostExecute(JSONObject result) {
				myResults = result;
				try {
					processResult();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}.execute();
	}

	public void processResult() throws JSONException{
		System.out.println("made it here");
		String output = "";
		for(int i=0; i<myResults.getJSONArray("businesses").length(); i++){
			if(Double.parseDouble(myResults.getJSONArray("businesses").getJSONObject(i).get("rating").toString()) >= rating)	
				output += "  :  " + myResults.getJSONArray("businesses").getJSONObject(i).get("name");
		}
		
		Intent intent = new Intent(this, ResultActivity.class);
		intent.putExtra(EXTRA_OUTPUT, output);
		startActivity(intent);
	}


}