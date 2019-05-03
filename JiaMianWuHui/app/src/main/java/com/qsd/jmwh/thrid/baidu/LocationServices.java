package com.qsd.jmwh.thrid.baidu;

import android.annotation.SuppressLint;
import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

@SuppressLint("StaticFieldLeak")
public class LocationServices extends BDAbstractLocationListener {

    //通过volatile关键字来确保安全
    private volatile static LocationServices LOCATION_SERVICES;

    private LocationServices() {
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {

    }

    private static LocationClient client = null;
    private static LocationClientOption mOption;

    public static LocationServices getInstance(Context context) {
        if (LOCATION_SERVICES == null) {
            synchronized (LocationServices.class) {
                if (LOCATION_SERVICES == null) {
                    LOCATION_SERVICES = new LocationServices();
                    client = new LocationClient(context);
                    setLocationOption(getDefaultLocationClientOption());
                    registerListener(new LocationListener());
                }
            }
        }
        return LOCATION_SERVICES;
    }


    private static LocationClientOption getDefaultLocationClientOption() {
        if (mOption == null) {
            mOption = new LocationClientOption();
            mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            mOption.setCoorType("bd09ll");
            mOption.setScanSpan(0);
            mOption.setIsNeedAddress(true);
            mOption.setIsNeedLocationDescribe(true);
            mOption.setNeedDeviceDirect(false);
            mOption.setLocationNotify(false);
            mOption.setIgnoreKillProcess(true);
            mOption.setIsNeedLocationDescribe(true);
            mOption.setIsNeedLocationPoiList(true);
            mOption.SetIgnoreCacheException(false);
            mOption.setOpenGps(true);
            mOption.setIsNeedAltitude(false);
        }
        return mOption;
    }


    private static void registerListener(BDAbstractLocationListener bDAbstractLocationListener) {
        if (bDAbstractLocationListener == null) {
            return;
        }
        client.registerLocationListener(bDAbstractLocationListener);
    }


    private static void setLocationOption(LocationClientOption locationClientOption) {
        if (locationClientOption != null) {
            if (client.isStarted()) {
                client.stop();
            }
            client.setLocOption(locationClientOption);
        }
    }

    public LocationServices start() {
        synchronized (LocationServices.class) {
            if (!(client == null || client.isStarted())) {
                client.start();
            }
        }
        return this;
    }

    public void stop() {
        synchronized (LocationServices.class) {
            LOCATION_SERVICES = null;
            if (client != null && client.isStarted()) {
                client.stop();
            }
        }
    }

}
