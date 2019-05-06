package com.yu.location;

import java.io.Serializable;


public class ILocation implements Serializable {

  public double longitude = 0;
  public double latitude = 0;
  public String cityName;

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public String getCityName() {
    return cityName;
  }
  public void setCityName(String cityName) {
    this.cityName = cityName;
  }
  public boolean isEmpty() {
    return Math.abs(longitude) < 0.01 || Math.abs(latitude) < 0.01;
  }

  @Override public String toString() {
    return "ILocation{" + "longitude=" + longitude + ", latitude=" + latitude + '}';
  }
}