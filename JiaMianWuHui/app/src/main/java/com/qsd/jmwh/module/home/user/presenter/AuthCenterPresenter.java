package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.bean.WomenVideoBean;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

/**
 * @author yudneghao
 * @date 2019-05-17
 */
@SuppressLint("CheckResult")
public class AuthCenterPresenter extends BaseViewPresenter<AuthCenterViewer> {

  public AuthCenterPresenter(AuthCenterViewer viewer) {
    super(viewer);
  }

   public void getAuthInf() {
    XHttpProxy.proxy(OtherApiServices.class).getWomenVideo().subscribeWith(new TipRequestSubscriber<WomenVideoBean>() {
      @Override protected void onSuccess(WomenVideoBean vedioBean) {
        assert getViewer() != null;
        if (vedioBean != null) {
          getViewer().getInfo(vedioBean);
        }

      }
    });
  }

  public void uploadAuthVideo(String path) {
    PersistenceResponse response = UploadImage.uploadImage(getActivity(), UserProfile.getInstance().getObjectName("auth","mp4"), path);
    XHttpProxy.proxy(OtherApiServices.class).userAuthByVideo(response.cloudUrl,"http://img0.imgtn.bdimg.com/it/u=1251096059,3284062985&fm=26&gp=0.jpg").subscribeWith(
        new TipRequestSubscriber<Object>() {
          @Override protected void onSuccess(Object o) {
            ToastUtils.show("上传成");
            assert getViewer() != null;
            getViewer().uploadSuccess();
          }
        });
  }
}
