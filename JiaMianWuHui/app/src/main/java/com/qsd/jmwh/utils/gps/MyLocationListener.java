package com.qsd.jmwh.utils.gps;

import android.annotation.SuppressLint;
import android.util.Log;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.xuexiang.xhttp2.XHttpProxy;

/**
 * @author yudneghao
 * @date 2019-07-23
 */
public class MyLocationListener extends BDAbstractLocationListener {
  @SuppressLint("CheckResult") @Override public void onReceiveLocation(BDLocation location) {
    //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
    //以下只列举部分获取经纬度相关（常用）的结果信息
    //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

    double latitude = location.getLatitude();    //获取纬度信息
    double longitude = location.getLongitude();    //获取经度信息
    float radius = location.getRadius();    //获取定位精度，默认值为0.0f

    String coorType = location.getCoorType();
    //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

    int errorCode = location.getLocType();
    if (location != null) {
      XHttpProxy.proxy(ApiServices.class)
          .modifyLngAndLat(latitude, longitude,
              location.getCity() == null ? "" : location.getCity())
          .subscribeWith(new TipRequestSubscriber<Object>() {
            @Override protected void onSuccess(Object o) {
              storeToLocal(location);
            }
          });
      Log.e("======>", latitude + "===" + longitude + "===" + location.getCity());
    }
  }

  private void storeToLocal(BDLocation location) {
    UserProfile.getInstance().setLat((float) location.getLatitude());
    UserProfile.getInstance().setLng((float) location.getLongitude());
    UserProfile.getInstance().setCityName(location.getCity());
  }
}
