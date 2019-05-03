package com.qsd.jmwh.thrid.baidu;

import android.annotation.SuppressLint;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.xuexiang.xhttp2.XHttpProxy;

public class LocationListener extends BDAbstractLocationListener {


    @SuppressLint("CheckResult")
    @Override
    public void onReceiveLocation(BDLocation location) {
        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        //以下只列举部分获取经纬度相关（常用）的结果信息
        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
        if (location == null || location.getLocType() == BDLocation.TypeServerError) {
            Log.e("=======>", "失败");
        } else {
            XHttpProxy.proxy(ApiServices.class)
                    .modifyLngAndLat(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()))
                    .subscribeWith(new NoTipRequestSubscriber<Object>() {
                        @Override
                        protected void onSuccess(Object o) {

                        }
                    });
        }

    }
}
