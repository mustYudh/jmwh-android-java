package com.qsd.jmwh.module.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.HomeActivity;
import com.qsd.jmwh.module.register.bean.EditUserInfo;
import com.qsd.jmwh.module.register.presenter.EditUserInfoPresenter;
import com.qsd.jmwh.module.register.presenter.EditUserInfoViewer;
import com.qsd.jmwh.view.NormaFormItemVIew;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.ImageLoader;

import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

public class EditUserDataActivity extends BaseBarActivity
    implements EditUserInfoViewer, View.OnClickListener {
  @PresenterLifeCycle EditUserInfoPresenter mPresenter = new EditUserInfoPresenter(this);

  private static final String TOKEN = "token";
  private static final String USER_ID = "user_id";

  private TextView headerHint;
  private ImageView selectHeader;
  private String userHeaderUrl;
  private NormaFormItemVIew location;
  private NormaFormItemVIew professional;
  private NormaFormItemVIew project;
  private NormaFormItemVIew height;
  private NormaFormItemVIew weight;

  public static Intent getIntent(Context context, String token, int lUserId) {
    Intent starter = new Intent(context, EditUserDataActivity.class);
    starter.putExtra(TOKEN, token);
    starter.putExtra(USER_ID, lUserId);
    return starter;
  }

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.edit_user_data_activity);
  }

  @Override protected void loadData() {
    setTitle("完善资料");
    initView();
    initListener();
  }

  private void initView() {
    headerHint = bindView(R.id.select_hint);
    selectHeader = bindView(R.id.header);
    location = bindView(R.id.location);
    professional = bindView(R.id.professional);
    project = bindView(R.id.project);
    height = bindView(R.id.height);
    weight = bindView(R.id.weight);
  }

  private void initListener() {
    selectHeader.setOnClickListener(this);
    bindView(R.id.login, this);
    bindView(R.id.agreement, this);
    initItemClickListener();
  }

  private void initItemClickListener() {
    location.setOnClickSeletedItem(v -> {
      getLaunchHelper().startActivity(DateRangeActivity.getIntent(getActivity(), getIntent().getStringExtra(TOKEN),
          getIntent().getIntExtra(USER_ID, -1)));
    });
    professional.setOnClickSeletedItem(v -> {

    });
    project.setOnClickSeletedItem(v -> {

    });
    height.setOnClickSeletedItem(v -> {
    });
    weight.setOnClickSeletedItem(v -> {
    });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.header:
        RxGalleryFinalApi.getInstance(EditUserDataActivity.this)
            .openGalleryRadioImgDefault(new RxBusResultDisposable<ImageRadioResultEvent>() {
              @Override protected void onEvent(ImageRadioResultEvent imageRadioResultEvent)
                  throws Exception {
                if (!TextUtils.isEmpty(imageRadioResultEvent.getResult().getThumbnailSmallPath())) {
                  mPresenter.setHeader(imageRadioResultEvent.getResult().getThumbnailSmallPath());
                }
              }
            });
        break;
      case R.id.login:
        EditUserInfo editUserInfo = new EditUserInfo();
        mPresenter.editUserInfo(editUserInfo);
        break;
      case R.id.agreement:
        ToastUtils.show("用户协议");
        break;
      default:
    }
  }

  @Override public void setUserHeaderSuccess(String url) {
    userHeaderUrl = url;
    headerHint.setVisibility(View.GONE);
    selectHeader.setVisibility(View.VISIBLE);
    ImageLoader.loadCenterCrop(EditUserDataActivity.this, url, selectHeader, R.mipmap.ic_launcher);
  }

  @Override public void commitUserInfo() {
    getLaunchHelper().startActivity(HomeActivity.class);
    finish();
  }
}
