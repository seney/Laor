package com.hsns.laor.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by seney on 12/21/15.
 */
public class Location extends RealmObject {

    @PrimaryKey
    private int locationID;
    private double latitude;
    private double longitude;
    private String address;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
