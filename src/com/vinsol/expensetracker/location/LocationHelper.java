package com.vinsol.expensetracker.location;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationHelper {
	
	////////////     **********    Class Used to get Location Latitude and Longitude Data from Location providers   **********         /////////// 
	
    Timer mTimer;
    LocationManager mLocationManager;
    LocationResult mLocationResult;
    boolean mCheckGPS_Status=false;
    boolean mCheckNetwork_Status=false;
    Geocoder mGeocoder;
    
    
    public boolean getLocation(Context context, LocationResult result)
    {
        //LocationResult callback class to pass location value from MyLocation to user code.
        mLocationResult=result;
        if(mLocationManager==null)
            mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //exceptions will be thrown if provider is not permitted.
        try{
        	mCheckGPS_Status=mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch(Exception ex){}
        
        try{
        	mCheckNetwork_Status=mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch(Exception ex){}

        //don't start listeners if no provider is enabled
        if(!mCheckGPS_Status && !mCheckNetwork_Status)
            return false;

        if(mCheckGPS_Status)
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        if(mCheckNetwork_Status)
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
        mTimer=new Timer();
        mTimer.schedule(new GetLastLocation(), 60000);
        return true;
    }

    
    //////     ******   Listener for Gps Provider   *******   ////////
    
    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            mTimer.cancel();
            mLocationResult.gotLocation(location);
            mLocationManager.removeUpdates(this);
            mLocationManager.removeUpdates(locationListenerNetwork);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    //////******   Listener for Network Provider   *******   ////////
    
    
    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            mTimer.cancel();
            mLocationResult.gotLocation(location);
            mLocationManager.removeUpdates(this);
            mLocationManager.removeUpdates(locationListenerGps);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    
    /////////    ********    To get last known location   ********   ///////////
    
    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
             mLocationManager.removeUpdates(locationListenerGps);
             mLocationManager.removeUpdates(locationListenerNetwork);

             Location net_loc=null, gps_loc=null;
             if(mCheckGPS_Status)
                 gps_loc=mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             if(mCheckNetwork_Status)
                 net_loc=mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

             //if there are both values use the latest one
             if(gps_loc!=null && net_loc!=null){
                 if(gps_loc.getTime()>net_loc.getTime()){
                     mLocationResult.gotLocation(gps_loc);
                 }
                 else {
                     mLocationResult.gotLocation(net_loc);
                 }
                 return;
             }

             if(gps_loc!=null){
                mLocationResult.gotLocation(gps_loc);
                return;
             }
             if(net_loc!=null){
                 mLocationResult.gotLocation(net_loc);
                 return;
             }
        }
    }

    
    ///////////    ********    LocationResult Abstract Class   *********    ///////////
    
    public static abstract class LocationResult{
        public abstract void gotLocation(Location location);
    }
}