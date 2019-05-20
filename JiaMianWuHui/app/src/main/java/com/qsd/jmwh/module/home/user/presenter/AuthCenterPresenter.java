package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.bean.WomenVideoBean;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.loading.LoadingDialog;

/**
 * @author yudneghao
 * @date 2019-05-17
 */
@SuppressLint("CheckResult") public class AuthCenterPresenter
    extends BaseViewPresenter<AuthCenterViewer> {

  public AuthCenterPresenter(AuthCenterViewer viewer) {
    super(viewer);
  }

  public void getAuthInf() {
    XHttpProxy.proxy(OtherApiServices.class)
        .getWomenVideo()
        .subscribeWith(new TipRequestSubscriber<WomenVideoBean>() {
          @Override protected void onSuccess(WomenVideoBean vedioBean) {
            assert getViewer() != null;
            if (vedioBean != null) {
              getViewer().getInfo(vedioBean);
            }
          }
        });
  }

  public void uploadAuthVideo(String path) {
    LoadingDialog.showNormalLoading(getActivity(), false);
    LoadingDialog.startLoading("正在上传");
    new Thread(() -> {
      PersistenceResponse response = UploadImage.uploadImage(getActivity(),
          UserProfile.getInstance().getObjectName("auth", "mp4"), path);
      String sFileCoverUrl = response.cloudUrl + "?x-oss-process=video/snapshot,t_100,m_fast";
      XHttpProxy.proxy(OtherApiServices.class)
          .userAuthByVideo(response.cloudUrl, sFileCoverUrl)
          .subscribeWith(new TipRequestSubscriber<Object>() {
            @Override protected void onSuccess(Object o) {
              assert getViewer() != null;
              getViewer().uploadSuccess();
            }

            @Override protected void onError(ApiException apiException) {
              super.onError(apiException);
              LoadingDialog.dismissLoading();
            }
          });
    }).start();
  }
}
