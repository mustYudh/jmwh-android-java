package com.qsd.jmwh.module.home.park.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.adapter.UserPhotoAdapter;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.qsd.jmwh.module.home.park.presenter.LookUserInfoPresenter;
import com.qsd.jmwh.module.home.park.presenter.LookUserInfoViewer;
import com.qsd.jmwh.view.NormaFormItemVIew;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.ImageLoader;

import java.util.ArrayList;

public class LookUserInfoActivity extends BaseActivity implements LookUserInfoViewer, View.OnClickListener {

    @PresenterLifeCycle
    private LookUserInfoPresenter mPresenter = new LookUserInfoPresenter(this);

    private final static String USER_ID = "user_id";

    public static Intent getIntent(Context context, int userId) {
        Intent starter = new Intent(context, LookUserInfoActivity.class);
        starter.putExtra(USER_ID, userId);
        return starter;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.look_user_info_layout);
    }

    @Override
    protected void loadData() {
        initListener();
        mPresenter.getUserInfo(getIntent().getIntExtra(USER_ID, -1), UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng());
    }

    private void initListener() {
        bindView(R.id.back, this);
    }

    @Override
    public void setUserInfo(OtherUserInfoBean userCenterInfo) {
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
            bindView(R.id.social_account, false);
        }
        ImageLoader.loadCenterCrop(getActivity(), userData.sUserHeadPic, bindView(R.id.header));
        ImageLoader.blurTransformation(getActivity(), userData.sUserHeadPic, bindView(R.id.header_bg),4,10);
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
        bindText(R.id.job_age_loc, userData.sCity + " · " + userData.sJob + " · " + userData.sAge + " · " + userData.nOnLine + "m");
        bindText(R.id.sDateRange, "约会范围：" + userData.sDateRange + " · " + userData.nOffLineMin + "分钟前");
        int authType = userData.nAuthType;
        @DrawableRes int result;
        if (authType == 0) {
            result = R.drawable.ic_not_auth;
        } else if (authType == 3) {
            bindView(R.id.video_auth,true);
            result = R.drawable.ic_video_auth;
        } else {
            result = R.drawable.ic_info_auth;
        }
        ImageView authStatus = bindView(R.id.auth_type);
        authStatus.setImageResource(result);
        bindText(R.id.auth_info, userData.sAuthInfo);
        ArrayList<OtherUserInfoBean.CdoFileListDataBean> list = userCenterInfo.cdoFileListData;
        bindView(R.id.empty_view, list.size() == 0);
        GridView gridView = bindView(R.id.user_center_photo, list.size() > 0);
        gridView.setAdapter(new UserPhotoAdapter(list));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
