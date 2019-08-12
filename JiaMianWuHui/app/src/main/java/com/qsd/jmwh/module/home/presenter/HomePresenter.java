package com.qsd.jmwh.module.home.presenter;

import android.annotation.SuppressLint;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.module.home.message.bean.SystemCountBean;
import com.tencent.map.geolocation.TencentLocation;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019/3/7
 */

@SuppressLint("CheckResult")
public class HomePresenter extends BaseViewPresenter<HomeViewer> {




    public HomePresenter(HomeViewer viewer) {
        super(viewer);
    }



  public void modifyLngAndLat(TencentLocation location) {
    double longitude = location.getLongitude();
    double latitude = location.getLatitude();
    XHttpProxy.proxy(ApiServices.class)
        .modifyLngAndLat(longitude, latitude, location.getCity() == null ? "" : location.getCity())
        .subscribeWith(new NoTipRequestSubscriber<Object>() {
          @Override protected void onSuccess(Object o) {
            storeToLocal(location);
          }
        });
    }


  private void storeToLocal(TencentLocation location) {
    if (location != null) {
      UserProfile.getInstance().setLat((float) location.getLatitude());
      UserProfile.getInstance().setLng((float) location.getLongitude());
      UserProfile.getInstance().setCityName(location.getCity());
    }
  }

  @SuppressLint("CheckResult") public void getMessageCount() {
    XHttpProxy.proxy(OtherApiServices.class)
        .getMsgCount()
        .subscribeWith(new NoTipRequestSubscriber<SystemCountBean>() {
          @Override protected void onSuccess(SystemCountBean bean) {
            assert getViewer() != null;
            getViewer().getSystemMessageCount(bean);
          }
        });
  }


}