package com.qsd.jmwh.module.home.user;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.denghao.control.view.utils.UpdataCurrentFragment;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseFragment;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.user.activity.EditUserInfoActivity;
import com.qsd.jmwh.module.home.user.activity.MoneyBagActivity;
import com.qsd.jmwh.module.home.user.activity.PrivacySettingActivity;
import com.qsd.jmwh.module.home.user.activity.SettingActivity;
import com.qsd.jmwh.module.home.user.bean.UserCenterMyInfo;
import com.qsd.jmwh.module.home.user.presenter.UserPresenter;
import com.qsd.jmwh.module.home.user.presenter.UserViewer;
import com.qsd.jmwh.module.register.ToByVipActivity;
import com.qsd.jmwh.module.splash.SplashActivity;
import com.qsd.jmwh.view.UserItemView;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.ImageLoader;

import java.util.Objects;
import java.util.UUID;

import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.utils.Logger;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class UserFragment extends BaseFragment implements UserViewer, View.OnClickListener, UpdataCurrentFragment {

    @PresenterLifeCycle
    private UserPresenter mPresenter = new UserPresenter(this);


    @Override
    protected int getContentViewId() {
        return R.layout.user_fragment;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        initListener();
    }

    private void initListener() {
        bindView(R.id.vip, this);
        bindView(R.id.money_bag, this);
        bindView(R.id.edit_user_info, this);
        bindView(R.id.privacy_setting, this);
        bindView(R.id.setting, this);
        bindView(R.id.add_photo, this);
        bindView(R.id.header, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vip:
                getLaunchHelper().startActivity(ToByVipActivity.getIntent(getActivity(),
                        UserProfile.getInstance().getAppAccount(),
                        UserProfile.getInstance().getAppToken()));
                break;
            case R.id.money_bag:
                getLaunchHelper().startActivity(MoneyBagActivity.class);
                break;
            case R.id.edit_user_info:
                getLaunchHelper().startActivity(EditUserInfoActivity.class);
                break;
            case R.id.privacy_setting:
                getLaunchHelper().startActivity(PrivacySettingActivity.class);
                break;
            case R.id.setting:
                getLaunchHelper().startActivity(SettingActivity.class);
                break;
            case R.id.add_photo:
                addPhoto();
                break;
            case R.id.header:
                RxGalleryFinalApi.getInstance(getActivity())
                        .openGalleryRadioImgDefault(new RxBusResultDisposable<ImageRadioResultEvent>() {
                            @Override
                            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                                if (!TextUtils.isEmpty(imageRadioResultEvent.getResult().getThumbnailSmallPath())) {
                                    mPresenter.setHeader(imageRadioResultEvent.getResult().getThumbnailSmallPath(),
                                            +UserProfile.getInstance().getAppAccount() + "/head_" + UUID.randomUUID().toString() + ".jpg", UserProfile.getInstance().getAppAccount() + "");
                                }
                            }
                        });
                break;
            default:
        }
    }

    private void addPhoto() {
        RxGalleryFinalApi
                .getInstance(getActivity())
                .setType(RxGalleryFinalApi.SelectRXType.TYPE_IMAGE, RxGalleryFinalApi.SelectRXType.TYPE_SELECT_MULTI)
                .setImageMultipleResultEvent(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        Logger.i("多选图片的回调");
                    }
                }).open();

    }

    @Override
    public void setUserInfo(UserCenterMyInfo userInfo) {
        UserCenterMyInfo.CdoUserBean cdoUser = userInfo.cdoUser;
        UserCenterMyInfo.CdoWalletDataBean walletData = userInfo.cdoWalletData;
        bindText(R.id.sNickName, cdoUser.sNickName);
        bindText(R.id.sDateRange, "约会范围：" + cdoUser.sDateRange);
        bindText(R.id.job_age_loc, cdoUser.sCity + " · " + cdoUser.sJob + " · " + cdoUser.sAge + "岁");
        bindText(R.id.auth_info, cdoUser.sAuthInfo);
        UserItemView vip = bindView(R.id.vip);
        vip.showTag(cdoUser.bVIP);
        if (!TextUtils.isEmpty(cdoUser.dVIPInvalidTime)) {
            vip.setHint(cdoUser.dVIPInvalidTime + "到期");
        }
        UserItemView nViewCount = bindView(R.id.nViewCount);
        nViewCount.setHint("有" + userInfo.nViewCount + "人看过你");
        UserItemView nDestroyImgCount = bindView(R.id.nDestroyImgCount);
        nDestroyImgCount.setHint("(有" + userInfo.nDestroyImgCount + "个人焚毁了你的照片) 一键恢复");
        int authType = Integer.parseInt(cdoUser.nAuthType);
        String result = authType == 0 ? "未认证" : "通过";
        bindText(R.id.auth_type, result);
        ImageView header = bindView(R.id.header);
        ImageLoader.loadCenterCrop(getActivity(), cdoUser.sUserHeadPic, header, R.mipmap.ic_launcher);
        UserItemView money = bindView(R.id.money_bag);
        money.setHint(walletData.nMoney + "元，" + walletData.nMaskBallCoin + "假面币");
        UserItemView appVersion = bindView(R.id.app_version);
        appVersion.setHint(getAppVersion(Objects.requireNonNull(getActivity())));
        UserProfile.getInstance().setPhoneNo(cdoUser.sMobile);
    }

    @Override
    public void setUserHeaderSuccess(String url) {
        ImageView header = bindView(R.id.header);
        ImageLoader.loadCenterCrop(getActivity(), url, header, R.mipmap.ic_launcher);
    }

    private static String getAppVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    @Override
    public void onResume() {
        super.onResume();
        update(getArguments());

    }

    @Override
    public void update(Bundle bundle) {
        if (UserProfile.getInstance().isAppLogin()) {
            mPresenter.getMyInfo();
        } else {
            getLaunchHelper().startActivity(SplashActivity.class);
            finish();
        }
    }
}
