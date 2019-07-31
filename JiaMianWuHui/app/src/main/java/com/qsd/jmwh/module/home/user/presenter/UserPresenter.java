package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.dialog.net.NetLoadingDialog;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.activity.PlayVideoActivity;
import com.qsd.jmwh.module.home.user.bean.UserCenterInfo;
import com.qsd.jmwh.module.home.user.bean.WomenVideoBean;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.vincent.videocompressor.VideoCompress;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;
import java.util.UUID;

/**
 * @author yudneghao
 * @date 2019/3/7
 */

@SuppressLint("CheckResult") public class UserPresenter extends BaseViewPresenter<UserViewer> {

  public UserPresenter(UserViewer viewer) {
    super(viewer);
  }

  public void setHeader(String path, String objectName, String userId) {
    PersistenceResponse response = UploadImage.uploadImage(getActivity(), objectName, path);
    if (response.success) {
      XHttpProxy.proxy(ApiServices.class)
          .uploadHeader(userId, UserProfile.getInstance().getAppToken(), response.cloudUrl)
          .safeSubscribe(new TipRequestSubscriber<Object>() {
            @Override protected void onSuccess(Object o) {
              assert getViewer() != null;
              getViewer().setUserHeaderSuccess(response.cloudUrl);
            }
          });
    }
  }

  public void getMyInfo() {
    XHttpProxy.proxy(ApiServices.class)
        .getUserCenterInfo()
        .subscribeWith(new TipRequestSubscriber<UserCenterInfo>() {
          @Override protected void onSuccess(UserCenterInfo userCenterMyInfo) {
            assert getViewer() != null;
            getViewer().setUserInfo(userCenterMyInfo);
          }
        });
  }

  public void destroyImgBrowsingHis() {
    XHttpProxy.proxy(OtherApiServices.class)
        .destroyImgBrowsingHis(UserProfile.getInstance().getLat(),
            UserProfile.getInstance().getLng())
        .subscribeWith(new TipRequestSubscriber<Object>() {
          @Override protected void onSuccess(Object o) {
            ToastUtils.show("恢复成功");
            assert getViewer() != null;
            getViewer().refreshData();
          }
        });
  }

  @SuppressLint("HandlerLeak") private Handler mHandler = new Handler() {
    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.what == 1) {
        PersistenceResponse response = (PersistenceResponse) msg.obj;
        String sFileCoverUrl = response.cloudUrl + "?x-oss-process=video/snapshot,t_100,m_fast";
        XHttpProxy.proxy(ApiServices.class)
            .addFile(response.cloudUrl, 1, 0, 0, 0, sFileCoverUrl)
            .subscribeWith(new TipRequestSubscriber<Object>() {
              @Override protected void onSuccess(Object o) {
                assert getViewer() != null;
                getViewer().uploadVideoSuccess();
              }

              @Override protected void onError(ApiException apiException) {
                super.onError(apiException);
                NetLoadingDialog.dismissLoading();
              }
            });
      }
    }
  };

  public void uploadFile(String path) {
    PackageManager packageManager = getActivity().getPackageManager();
    PackageInfo packInfo = null;
    try {
      packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    String outputDir =
        Environment.getDataDirectory().getPath() + "/data/" + packInfo.packageName + "/files/";
    String destPath = outputDir + "/jmwh" + UUID.randomUUID().toString() + ".mp4";
    VideoCompress.compressVideoMedium(path, destPath, new VideoCompress.CompressListener() {
      @Override public void onStart() {
        NetLoadingDialog.showLoading(getActivity(), true);
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
        NetLoadingDialog.dismissLoading();
      }

      @Override public void onProgress(float percent) {

      }
    });
  }


  public void getAuthInf() {
    XHttpProxy.proxy(OtherApiServices.class)
        .getWomenVideo()
        .subscribeWith(new NoTipRequestSubscriber<WomenVideoBean>() {
          @Override protected void onSuccess(WomenVideoBean bean) {
            if (bean != null) {
              PlayVideoActivity.getIntent(getActivity(), bean.sFileUrl, bean.sFileCoverUrl);
            }
          }
        });
  }


}