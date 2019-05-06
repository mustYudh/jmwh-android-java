package com.yu.location.impl;

import android.content.Context;
import android.util.Log;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.yu.location.ILocation;
import com.yu.location.ILocationClient;
import com.yu.location.LocationListener;


public class ILocationClientImpl implements ILocationClient {
  private static final String TAG = "ILocationClientImpl";

  private static volatile ILocationClientImpl mInstance;

  private final BaiDuLocationService baiDuLocationService;
  private volatile LocationListener locationListener;

  private boolean ifNullUseIp;
  private long requestId;

  public synchronized static ILocationClientImpl getInstance(Context context) {
    if (mInstance == null) {
      synchronized (ILocationClientImpl.class) {
        if (mInstance == null) {
          mInstance = new ILocationClientImpl(context);
        }
      }
    }

    return mInstance;
  }

  private ILocationClientImpl(Context context) {
    this.baiDuLocationService = new BaiDuLocationService(context);
  }

  @Override public void start(LocationListener locationListener) {
    start(locationListener, false);
  }

  @Override public void start(LocationListener locationListener, boolean ifNullUseIp) {
    this.ifNullUseIp = ifNullUseIp;
    this.locationListener = locationListener;
    this.requestId = System.nanoTime();
    baiDuLocationService.start(bdLocationListener);
  }

  @Override public void stop() {
    this.ifNullUseIp = false;
    this.locationListener = null;
    baiDuLocationService.stop();
  }

  private final BDLocationListener bdLocationListener = new BDLocationListener() {
    @Override public void onReceiveLocation(BDLocation location) {
      final ILocation resultLoc = new ILocation();

      if (null != location) {
        resultLoc.latitude = location.getLatitude();
        resultLoc.longitude = location.getLongitude();
        Log.d(TAG, "onReceiveLocation location locType "
            + location.getLocType()
            + ";  resultLoc:"
            + resultLoc);
      }

      if (resultLoc.isEmpty() && ifNullUseIp) {
        IpGetLocation.getLocationByIP(new IpLocCallback() {
          @Override public void getLoc(long id, ILocation iLocation) {
            if (id == requestId) {
              receive(iLocation);
              Log.d(TAG, "onReceiveLocation location by Ip " + iLocation);
            }
          }
        }, requestId);
      } else {
        receive(resultLoc);
      }
    }
  };

  private void receive(ILocation iLocation) {
    if (locationListener != null) {
      locationListener.onReceive(iLocation);
    }
    stop();
  }
}