package com.qsd.jmwh.module.home.park.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.qsd.jmwh.module.home.park.presenter.LookUserInfoPresenter;
import com.qsd.jmwh.module.home.park.presenter.LookUserInfoViewer;
import com.qsd.jmwh.view.NormaFormItemVIew;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.ImageLoader;

public class LookUserInfoActivity extends BaseActivity implements LookUserInfoViewer {

  @PresenterLifeCycle private LookUserInfoPresenter mPresenter = new LookUserInfoPresenter(this);

  private final static String USER_ID = "user_id";

  public static Intent getIntent(Context context, int userId) {
    Intent starter = new Intent(context, LookUserInfoActivity.class);
    starter.putExtra(USER_ID, userId);
    return starter;
  }

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.look_user_info_layout);
  }

  @Override protected void loadData() {
    mPresenter.getUserInfo(getIntent().getIntExtra(USER_ID, -1), UserProfile.getInstance().getLat(),
        UserProfile.getInstance().getLng());
  }

  @Override public void setUserInfo(OtherUserInfoBean userCenterInfo) {
    OtherUserInfoBean.CdoUserDataBean userData = userCenterInfo.cdoUserData;
    NormaFormItemVIew bust = bindView(R.id.bust);
    if (userData.nSex == 0) {
      bust.setContentText(userData.sBust);
      NormaFormItemVIew qq = bindView(R.id.qq);
      qq.setContent(userData.QQ);
      NormaFormItemVIew wechat = bindView(R.id.wechat);
      wechat.setContent(userData.WX);
    } else {
      bindView(R.id.bust, false);
      bindView(R.id.qq, false);
      bindView(R.id.we_chat, false);
    }
    ImageLoader.loadCenterCrop(getActivity(), userData.sUserHeadPic, bindView(R.id.header));
    bindText(R.id.sNickName, userData.sNickName);
    NormaFormItemVIew height = bindView(R.id.height);
    height.setContentText(userData.sHeight);
    NormaFormItemVIew weight = bindView(R.id.weight);
    weight.setContentText(userData.sWeight);
    bust.setContentText(userData.sBust);
    NormaFormItemVIew project = bindView(R.id.project);
    project.setContentText(userData.sDatePro);
    NormaFormItemVIew sIntroduce = bindView(R.id.sIntroduce);
    sIntroduce.setContentText(userData.sIntroduce);
  }
}
