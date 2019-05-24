package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.bean.WomenVideoBean;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.vincent.videocompressor.VideoCompress;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.loading.LoadingDialog;
import java.util.UUID;

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
              }

              @Override protected void onError(ApiException apiException) {
                super.onError(apiException);
                LoadingDialog.dismissLoading();
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
    PackageManager packageManager = getActivity().getPackageManager();
    PackageInfo packInfo = null;
    try {
      packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    String outputDir =
        Environment.getDataDirectory().getPath() + "/data/" + packInfo.packageName + "/files/";
    String destPath = outputDir + "/mwh" + UUID.randomUUID().toString() + ".mp4";
    VideoCompress.compressVideoMedium(path, destPath, new VideoCompress.CompressListener() {
      @Override public void onStart() {
        LoadingDialog.showNormalLoading(getActivity(), false);
        LoadingDialog.startLoading("正在上传");
      }

      @Override public void onSuccess() {
        new Thread(() -> {
          PersistenceResponse response = UploadImage.uploadImage(getActivity(),
              UserProfile.getInstance().getObjectName("auth", "mp4"), destPath);
          Message message = mHandler.obtainMessage();
          message.what = 1;
          message.obj = response;
          mHandler.sendMessage(message);
        }).start();
      }

      @Override public void onFail() {
        LoadingDialog.dismissLoading();
      }

      @Override public void onProgress(float percent) {
        Log.e("======>", percent + "");
      }
    });
  }
}
