package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.user.bean.WomenVideoBean;
import com.qsd.jmwh.module.home.user.presenter.AuthCenterPresenter;
import com.qsd.jmwh.module.home.user.presenter.AuthCenterViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.ImageLoader;

public class AuthCenterActivity extends BaseBarActivity implements AuthCenterViewer {
  @PresenterLifeCycle private AuthCenterPresenter mPresenter = new AuthCenterPresenter(this);

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.auth_center_activity_layout);
  }

  @Override protected void loadData() {
    setTitle("认证中心");
    mPresenter.getAuthInf();
    mPresenter.getAuthCode();
    bindView(R.id.uploading, view -> uploadingVideo());
  }

  @Override public void getInfo(WomenVideoBean vedioBean) {
    ImageView view = bindView(R.id.cover_img);
    Button button = bindView(R.id.play_button);
    if (!TextUtils.isEmpty(vedioBean.sFileCoverUrl)) {
      view.setVisibility(View.VISIBLE);
      ImageLoader.loadCenterCrop(getActivity(), vedioBean.sFileCoverUrl, view);
      button.setVisibility(View.VISIBLE);
      button.setOnClickListener(v -> getLaunchHelper().startActivity(
          PlayVideoActivity.getIntent(getActivity(), vedioBean.sFileUrl, vedioBean.sFileCoverUrl)));
    }

    TextView textView = bindView(R.id.uploading);
    if (vedioBean.nCheckStatus == 0) {
      textView.setText("认证通过");
      textView.setClickable(false);
    } else if (vedioBean.nCheckStatus == 1) {
      textView.setText("更新认证");
    } else if (vedioBean.nCheckStatus == 3) {
      textView.setText("审核中（24小时内）");
      ToastUtils.show("未通过请您耐心等待");
      textView.setClickable(false);
    } else {
      textView.setText("认证失败");
    }
  }

  private void uploadingVideo() {
    RxGalleryFinalApi instance = RxGalleryFinalApi.getInstance(getActivity());
    instance.openRadioSelectVideo(getActivity(),
        new RxBusResultDisposable<ImageMultipleResultEvent>() {
          @Override protected void onEvent(ImageMultipleResultEvent event) throws Exception {
            mPresenter.uploadAuthVideo(event.getResult().get(0).getOriginalPath());
          }
        });
  }

  @Override public void uploadSuccess() {
    mPresenter.getAuthInf();
    ToastUtils.show("上传成功");
  }

  @Override public void setAuthCode(String code) {
    bindText(R.id.auth_code, "假面舞会 " + code);
  }
}
