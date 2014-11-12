package com.example.shout;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class LatLong{
	private static LocationManager locationManager;
	private static Location location;
	private static Context llContext;
	
	public LatLong(Context context){
		llContext = context;
	}
	
	public static Location getLocation(){
		return location;
	}
	
	public static void updateLocation(){
        locationManager = (LocationManager)llContext.getSystemService(Context.LOCATION_SERVICE);

        String provider = LocationManager.GPS_PROVIDER;
        location = locationManager.getLastKnownLocation(provider);
    }
}