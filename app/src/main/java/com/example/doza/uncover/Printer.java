package com.example.doza.uncover;

/**
 * Created by nrjoseph on 4/26/2017.
 */

public class Printer {
    double latitude,longitude;
    String location, note;
    public Printer(double lat, double lon, String l, String n){
        latitude = lat;
        longitude = lon;
        location = l;
        note = n;
    }
    public double getLat(){
        return latitude;
    }
    public double getLon(){
        return longitude;
    }
    public String getLocation(){
        return location;
    }
    public String getNote(){
        return note;
    }
}
