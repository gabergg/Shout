package com.example.shout;

public class SearchInput {
	
	double latitude;
	double longitude;
	double sw_latitude;
	double sw_longitude;
	double ne_latitude;
	double ne_longitude;
	
	String terms;
	String location;
	int limit;
	int offset;
	String category_filter;
	double radius_filter; //meters
	Boolean deals_filter;
	
	public SearchInput(){}

}