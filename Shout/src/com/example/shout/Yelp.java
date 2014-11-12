package com.example.shout;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import android.location.Location;
import android.util.Log;

public class Yelp {

	OAuthService service;
	Token accessToken;

	public Yelp(String consumerKey, String consumerSecret, String token, String tokenSecret) {
		this.service = new ServiceBuilder().provider(YelpAPI2.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
		this.accessToken = new Token(token, tokenSecret);
	}

	public String search(String term, String location, double radius) {
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");

		request.addQuerystringParameter("term", term);
		request.addQuerystringParameter("ll", location);
		request.addQuerystringParameter("radius_filter", String.valueOf(radius*1609));
		request.addQuerystringParameter("category_filter", "bubbletea");
		this.service.signRequest(this.accessToken, request);
		Response response = request.send();  
		Log.i("Go body",String.valueOf(response.getCode()));
		System.out.println(location);
		System.out.println(radius);
		return response.getBody();
	}

	public String search(String term, Location location) {
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");

		request.addQuerystringParameter("term", term);
		request.addQuerystringParameter("ll", location.getLatitude() + "," + location.getLongitude());
		//request.addQuerystringParameter("category_filter", "bubbletea");
		request.addQuerystringParameter("radius_filter", "20000");
		this.service.signRequest(this.accessToken, request);
		Response response = request.send();  
		Log.i("Go body",String.valueOf(response.getCode()));
		return response.getBody();
	}
}