package com.example.shout;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SeekBarActivity extends Activity implements
		OnSeekBarChangeListener {

	public final static String EXTRA_OUTPUT = "com.example.yelpyapper.OUTPUT";

	private SeekBar ratingBar;
	private SeekBar radiusBar;
	private TextView ratingProgress;
	private TextView radiusProgress;
	private static double[] radiusInc = { .5, 1, 2, 5, 10, 25 };
	private double rating;
	private double radius;
	private String output;
	private JSONObject myResults;
	private LatLong latlong;
	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seek_bar);

		latlong = new LatLong(this);
		LatLong.updateLocation();
		location = LatLong.getLocation();

		ratingProgress = (TextView) findViewById(R.id.ratingProgress);
		radiusProgress = (TextView) findViewById(R.id.radiusProgress);
		ratingProgress.setText("Minimum rating: .5 stars");
		radiusProgress.setText("Maximum distance: .5 miles");

		ratingBar = (SeekBar) findViewById(R.id.seekRating);
		radiusBar = (SeekBar) findViewById(R.id.seekRadius);
		ratingBar.setOnSeekBarChangeListener(this);
		radiusBar.setOnSeekBarChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seek_bar, menu);
		return true;
	}

	@Override
	public void onProgressChanged(SeekBar bar, int progress, boolean userTouched) {

		if (bar.equals(radiusBar)) {
			radius = radiusInc[progress];
			radiusProgress.setText("Maximum distance: " + radius);
		} else if (bar.equals(ratingBar)) {
			rating = (double) (progress + 1) / 2;
			ratingProgress.setText("Minimum rating: " + rating + " stars");
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

	public void findLocations(View view) {
		yelpSearch();
	}

	private void yelpSearch() {
		new AsyncTask<SearchInput, Void, JSONObject>() {
			@Override
			protected JSONObject doInBackground(SearchInput... input) {
				Yelp yelp = new Yelp(getString(R.string.consumer_key),
						getString(R.string.consumer_secret),
						getString(R.string.token),
						getString(R.string.token_secret));
				String result = yelp.search("Restaurants",
						location.getLatitude() + "," + location.getLongitude(),
						radius);

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

	private void processResult() throws JSONException {
		output = "";

		JSONArray resultArray = myResults.getJSONArray("businesses");

		for (int i = 0; i < resultArray.length(); i++) {
			JSONObject business = resultArray.getJSONObject(i);
			if (Double.parseDouble(business.get("rating").toString()) >= rating) {
				output += business.get("name") + " : " + business.get("rating")
						+ ", ";
			}
		}
		System.out.println(output);
		Intent intent = new Intent(this, ResultActivity.class);
		intent.putExtra(EXTRA_OUTPUT, output);
		startActivity(intent);
	}

}