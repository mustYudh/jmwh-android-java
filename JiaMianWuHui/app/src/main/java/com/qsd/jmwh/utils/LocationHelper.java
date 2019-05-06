package com.qsd.jmwh.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import com.qsd.jmwh.APP;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.location.ILocation;
import com.yu.location.impl.ILocationClientImpl;

public class LocationHelper {

  private static LocationHelper instance;

  private ILocationClientImpl client;

  private LocationHelper(Context context) {
    client = ILocationClientImpl.getInstance(
        context == null ? APP.getInstance() : context.getApplicationContext());
  }

  public static LocationHelper getInstance(Context context) {
    if (instance == null) {
      instance = new LocationHelper(context);
    }
    return instance;
  }

  @SuppressLint("CheckResult") private void startForCity() {
    client.start(pLocation -> {
      if (pLocation != null) {
        XHttpProxy.proxy(ApiServices.class)
            .modifyLngAndLat(pLocation.latitude, pLocation.longitude, pLocation.cityName)
            .subscribeWith(new TipRequestSubscriber<Object>() {
              @Override protected void onSuccess(Object o) {
                storeToLocal(pLocation);
              }
            });
      }
    }, true);
  }

  /**
   * 保存到本地
   */
  public void requestLocationToLocal() {
    startForCity();
  }

  /**
   * 保存到本地
   */
  private void storeToLocal(ILocation location) {
    if (location != null && !location.isEmpty()) {
      UserProfile.getInstance().setLat((float) location.getLatitude());
      UserProfile.getInstance().setLng((float) location.getLongitude());
      UserProfile.getInstance().setCityName(location.getCityName());
    }
  }
}
