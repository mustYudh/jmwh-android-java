package com.qsd.jmwh.module.home.user.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.user.adapter.MineRadilLoveUserGvAdapter;
import com.qsd.jmwh.module.home.user.bean.WomenVideoBean;
import com.qsd.jmwh.module.home.user.presenter.AuthCenterPresenter;
import com.qsd.jmwh.module.home.user.presenter.AuthCenterViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.ImageLoader;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
    MineRadilLoveUserGvAdapter.nAuthType = vedioBean.nCheckStatus;

    //`nCheckStatus` int(10) NOT NULL DEFAULT '0' COMMENT '0：未审核，1、已审核 2、审核未通过，3、审核中',
    TextView textView = bindView(R.id.uploading);
    if (vedioBean.nCheckStatus == 0) {
      textView.setText("未审核");
      textView.setClickable(false);
    }
    if (vedioBean.nCheckStatus == 1) {
      textView.setText("已审核");
      textView.setClickable(false);
      textView.setClickable(false);
    } else if (vedioBean.nCheckStatus == 2) {
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
            MediaBean mediaBean = event.getResult().get(0);
            mPresenter.uploadAuthVideo(mediaBean.getOriginalPath(),
                getVideoThumbnail(mediaBean.getOriginalPath()).getPath());
          }
        });
  }

  public File getVideoThumbnail(String filePath) {
    PackageManager packageManager = getActivity().getPackageManager();
    PackageInfo packInfo = null;
    Bitmap b = null;
    File file = null;
    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
    try {
      packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
      String outputDir = Environment.getDataDirectory().getPath()
          + "/data/"
          + packInfo.packageName
          + "/files/"
          + System.currentTimeMillis()
          + ".png";
      file = new File(outputDir);
      retriever.setDataSource(filePath);
      b = retriever.getFrameAtTime();
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
      b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
      bos.flush();
      bos.close();
    } catch (Exception e) {
      e.printStackTrace();
      retriever.release();
    }
    return file;
  }

  @Override public void uploadSuccess() {
    mPresenter.getAuthInf();
    ToastUtils.show("上传成功");
  }

  @Override public void setAuthCode(String code) {
    bindText(R.id.auth_code, "假面舞会 " + code);
  }
}
