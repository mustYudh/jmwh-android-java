package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.dialog.net.NetLoadingDialog;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.bean.WomenVideoBean;
import com.qsd.jmwh.module.register.bean.UserAuthCodeBean;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019-05-17
 */
@SuppressLint("CheckResult") public class AuthCenterPresenter
    extends BaseViewPresenter<AuthCenterViewer> {

  @SuppressLint("HandlerLeak") private Handler mHandler = new Handler() {
    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.what == 1) {
        PersistenceResponse response = (PersistenceResponse) msg.obj;
        String sFileCoverUrl = response.cloudUrl + "?x-oss-process=video/snapshot,t_100,m_fast";
        XHttpProxy.proxy(OtherApiServices.class)
            .userAuthByVideo(response.cloudUrl, sFileCoverUrl)
            .subscribeWith(new NoTipRequestSubscriber<Object>() {
              @Override protected void onSuccess(Object o) {
                assert getViewer() != null;
                getViewer().uploadSuccess();
                NetLoadingDialog.dismissLoading();
              }

              @Override protected void onError(ApiException apiException) {
                NetLoadingDialog.dismissLoading();
              }
            });
      }
    }
  };

  public AuthCenterPresenter(AuthCenterViewer viewer) {
    super(viewer);
  }

  public void getAuthInf() {
    XHttpProxy.proxy(OtherApiServices.class)
        .getWomenVideo()
        .subscribeWith(new NoTipRequestSubscriber<WomenVideoBean>() {
          @Override protected void onSuccess(WomenVideoBean bean) {
            if (bean != null) {
              assert getViewer() != null;
              getViewer().getInfo(bean);
            }
          }
        });
  }

  public void uploadAuthVideo(String path) {
    NetLoadingDialog.showLoading(getActivity(),false);
    //PackageManager packageManager = getActivity().getPackageManager();
    //PackageInfo packInfo = null;
    //try {
    //  packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
    //} catch (PackageManager.NameNotFoundException e) {
    //  e.printStackTrace();
    //}
    //String outputDir =
    //    Environment.getDataDirectory().getPath() + "/data/" + packInfo.packageName + "/files/";
    //String destPath = outputDir + "/jmwh" + UUID.randomUUID().toString() + ".mp4";
    //VideoCompress.compressVideoMedium(path, destPath, new VideoCompress.CompressListener() {
    //  @Override public void onStart() {
    //    NetLoadingDialog.showLoading(getActivity(), false);
    //  }
    //
    //  @Override public void onSuccess() {
    //    new Thread(() -> {
    //      PersistenceResponse response = UploadImage.uploadImage(getActivity(),
    //          UserProfile.getInstance().getObjectName("auth", "mp4"), destPath);
    //      Message message = mHandler.obtainMessage();
    //      message.what = 1;
    //      message.obj = response;
    //      mHandler.sendMessage(message);
    //    }).start();
    //  }
    //
    //  @Override public void onFail() {
    //    NetLoadingDialog.dismissLoading();
    //  }
    //
    //  @Override public void onProgress(float percent) {
    //    Log.e("======>", percent + "");
    //  }
    //});
    new Thread(() -> {
      PersistenceResponse response = UploadImage.uploadImage(getActivity(),
          UserProfile.getInstance().getObjectName("auth", "mp4"), path);
      Message message = mHandler.obtainMessage();
      message.what = 1;
      message.obj = response;
      mHandler.sendMessage(message);
    }).start();
  }

  public void getAuthCode() {
    XHttpProxy.proxy(ApiServices.class)
        .getCod(UserProfile.getInstance().getUserId(),UserProfile.getInstance().getAppToken())
        .subscribeWith(new TipRequestSubscriber<UserAuthCodeBean>() {
          @Override
          protected void onSuccess(UserAuthCodeBean result) {
            assert getViewer() != null;
            getViewer().setAuthCode(result.sAuthCode);
          }
        });
  }
}
