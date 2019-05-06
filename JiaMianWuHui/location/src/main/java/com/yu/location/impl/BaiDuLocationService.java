package com.yu.location.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * @author chenwei
 */
class BaiDuLocationService {

  private LocationClient client = null;

  private Handler mHandler;

  private BDLocationListener mBDLocationListener;

  BaiDuLocationService(Context context) {
    mHandler = new Handler(Looper.getMainLooper());
    client = new LocationClient(context);
    client.setLocOption(getDefaultLocationClientOption());
  }

  /***
   * @return DefaultLocationClientOption
   */
  private LocationClientOption getDefaultLocationClientOption() {
    LocationClientOption mOption = new LocationClientOption();
    mOption.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
    mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
    //mOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
    mOption.setIsNeedAddress(false);//可选，设置是否需要地址信息，默认不需要
    mOption.setIsNeedLocationDescribe(false);//可选，设置是否需要地址描述
    mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
    mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
    mOption.setIsNeedLocationPoiList(false);
    mOption.setIgnoreKillProcess(
        true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
    mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
    mOption.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
    mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
    return mOption;
  }

  public void start(final BDLocationListener listener) {
    mHandler.removeCallbacksAndMessages(null);

    boolean isStop = mBDLocationListener == null;

    if (mBDLocationListener != null) {
      client.unRegisterLocationListener(mBDLocationListener);
    }

    mBDLocationListener = listener;

    if (mBDLocationListener != null) {
      client.registerLocationListener(mBDLocationListener);
    }
    if (isStop) {
      client.start();
    } else {
      client.restart();
    }
    mHandler.postDelayed(new Runnable() {
      @Override public void run() {
        if (mBDLocationListener != null) {
          mBDLocationListener.onReceiveLocation(null);
        }
      }
    }, isStop ? 6000 : 7000);
  }

  public void stop() {
    mHandler.removeCallbacksAndMessages(null);
    if (mBDLocationListener != null) {
      client.unRegisterLocationListener(mBDLocationListener);
    }
    mBDLocationListener = null;
    if (client != null) {
      client.stop();
    }
  }
}