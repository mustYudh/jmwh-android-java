package com.qsd.jmwh.module.home.user.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.ImageView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.user.bean.WomenVideoBean;
import com.qsd.jmwh.module.home.user.presenter.AuthCenterPresenter;
import com.qsd.jmwh.module.home.user.presenter.AuthCenterViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.ui.video.SurfaceVideoViewCreator;
import com.yu.common.utils.ImageLoader;

public class AuthCenterActivity extends BaseBarActivity implements AuthCenterViewer {
  @PresenterLifeCycle private AuthCenterPresenter mPresenter = new AuthCenterPresenter(this);

  private SurfaceVideoViewCreator surfaceVideoViewCreator;

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.auth_center_activity_layout);
  }

  @Override protected void loadData() {
    setTitle("认证中心");
    mPresenter.getAuthInf();
    bindView(R.id.uploading, view -> {
      uploadingVideo();
    });
  }

  @Override public void getInfo(WomenVideoBean vedioBean) {
    surfaceVideoViewCreator = new SurfaceVideoViewCreator(getActivity(), bindView(R.id.video)) {
      @Override protected Activity getActivity() {
        return AuthCenterActivity.this;
      }

      @Override protected boolean setAutoPlay() {
        return false;
      }

      @Override protected int getSurfaceWidth() {
        return 0;
      }

      @Override protected int getSurfaceHeight() {
        return 205;
      }

      @Override protected void setThumbImage(ImageView thumbImageView) {
        ImageLoader.loadCenterCrop(getActivity(), vedioBean.sFileCoverUrl, thumbImageView);
      }

      @Override protected String getSecondVideoCachePath() {
        return null;
      }

      @Override protected String getVideoPath() {
        return vedioBean.sFileUrl;
      }
    };
  }

  private void uploadingVideo() {
    RxGalleryFinalApi.getInstance(getActivity())
        .setType(RxGalleryFinalApi.SelectRXType.TYPE_VIDEO,
            RxGalleryFinalApi.SelectRXType.TYPE_SELECT_RADIO)
        .setVDRadioResultEvent(new RxBusResultDisposable<ImageRadioResultEvent>() {
          @Override protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
            mPresenter.uploadAuthVideo(imageRadioResultEvent.getResult().getOriginalPath());
          }
        })
        .open();
  }

  @Override public void uploadSuccess() {
    mPresenter.getAuthInf();
  }

  @Override protected void onPause() {
    super.onPause();
    surfaceVideoViewCreator.onPause();
  }

  @Override protected void onResume() {
    super.onResume();
    if (surfaceVideoViewCreator != null) {
      surfaceVideoViewCreator.onResume();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    surfaceVideoViewCreator.onDestroy();
  }

  @Override public boolean dispatchKeyEvent(KeyEvent event) {
    surfaceVideoViewCreator.onKeyEvent(event);
    return super.dispatchKeyEvent(event);
  }
}
