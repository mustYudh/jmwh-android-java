package com.qsd.jmwh.module.home.user;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.denghao.control.view.utils.UpdataCurrentFragment;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseFragment;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.dialog.ShareDialog;
import com.qsd.jmwh.module.home.user.activity.EditUserInfoActivity;
import com.qsd.jmwh.module.home.user.activity.MineBlackMenuActivity;
import com.qsd.jmwh.module.home.user.activity.MineLikeActivity;
import com.qsd.jmwh.module.home.user.activity.MineRadioListActivity;
import com.qsd.jmwh.module.home.user.activity.MoneyBagActivity;
import com.qsd.jmwh.module.home.user.activity.PhotoDestroySelectActivity;
import com.qsd.jmwh.module.home.user.activity.PrivacySettingActivity;
import com.qsd.jmwh.module.home.user.activity.SettingActivity;
import com.qsd.jmwh.module.home.user.adapter.DestroyPhotoTag;
import com.qsd.jmwh.module.home.user.bean.UserCenterInfo;
import com.qsd.jmwh.module.home.user.dialog.EvaluationDialog;
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
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class UserFragment extends BaseFragment implements UserViewer, View.OnClickListener, UpdataCurrentFragment {

    @PresenterLifeCycle
    private UserPresenter mPresenter = new UserPresenter(this);
    private String selectPhotoUrl;
    private String header;
    private String sNickName;

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
        bindView(R.id.my_evaluation, this);
        bindView(R.id.my_radio, this);
        bindView(R.id.my_like, this);
        bindView(R.id.black_list, this);
        bindView(R.id.my_evaluation, this);
        bindView(R.id.share, this);
        bindView(R.id.nDestroyImgCount, this);
        bindView(R.id.setting_money_img, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vip:
                getLaunchHelper().startActivity(ToByVipActivity.getIntent(getActivity(),
                        UserProfile.getInstance().getAppAccount(),
                        UserProfile.getInstance().getAppToken()
                        , false));
                break;
            case R.id.money_bag:
                getLaunchHelper().startActivity(MoneyBagActivity.class);
                break;
            case R.id.edit_user_info:
                getLaunchHelper().startActivity(EditUserInfoActivity.class);
                break;
            case R.id.privacy_setting:
                getLaunchHelper().startActivity(PrivacySettingActivity.getIntent(getActivity(), sNickName, header));
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
                                selectPhotoUrl = imageRadioResultEvent.getResult().getThumbnailSmallPath();
                            }
                        }).onCropImageResult(new IRadioImageCheckedListener() {
                    @Override
                    public void cropAfter(Object t) {
                        if (!TextUtils.isEmpty(selectPhotoUrl)) {
                            mPresenter.setHeader(selectPhotoUrl, +UserProfile.getInstance().getAppAccount() + "/head_" + UUID.randomUUID().toString() + ".jpg", UserProfile.getInstance().getAppAccount() + "");
                        }
                    }

                    @Override
                    public boolean isActivityFinish() {
                        return true;
                    }
                });
                break;
            case R.id.my_radio:
                getLaunchHelper().startActivity(MineRadioListActivity.class);
                break;
            case R.id.my_like:
                getLaunchHelper().startActivity(MineLikeActivity.class);
                break;
            case R.id.black_list:
                getLaunchHelper().startActivity(MineBlackMenuActivity.class);
                break;
            case R.id.my_evaluation:
                EvaluationDialog dialog = new EvaluationDialog(getActivity(), header, sNickName, UserProfile.getInstance().getAppAccount());
                dialog.showPopupWindow();
                break;
            case R.id.share:
                ShareDialog shareDialog = new ShareDialog(getActivity());
                shareDialog.showPopupWindow();
                break;
            case R.id.nDestroyImgCount:
                mPresenter.destroyImgBrowsingHis();
                break;
            case R.id.setting_money_img:
                SelectHintPop selectHintPop = new SelectHintPop(getActivity());
                selectHintPop.setTitle("设置红包照片")
                        .setMessage("最多可以选择2张照片作为红包照片，用户必须向你发红包才能查看（红包金额随机，每个不超过3元）")
                        .setSingleButton("继续", v1 -> {
                            selectHintPop.dismiss();
                        })
                        .setBottomButton("取消", v12 -> selectHintPop.dismiss()).showPopupWindow();
                break;
            default:
        }
    }

    private void addPhoto() {
        RxGalleryFinalApi instance = RxGalleryFinalApi.getInstance(getActivity());
        instance.openSingGalleryRadioImgDefault(
                new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                        String imgUrl = imageRadioResultEvent.getResult().getOriginalPath();
                        if (!TextUtils.isEmpty(imgUrl)) {
                            getLaunchHelper().startActivity(PhotoDestroySelectActivity.getIntent(getActivity(), imgUrl));
                        }
                    }
                });
    }

    @Override
    public void setUserInfo(UserCenterInfo userInfo) {
        bindView(R.id.vip, UserProfile.getInstance().getSex() == 1);
        UserCenterInfo.CdoUserBean cdoUser = userInfo.cdoUser;
        UserCenterInfo.CdoWalletDataBean walletData = userInfo.cdoWalletData;
        sNickName = cdoUser.sNickName;
        header = cdoUser.sUserHeadPic;
        bindText(R.id.sNickName, sNickName);
        bindText(R.id.sDateRange, "约会范围：" + cdoUser.sDateRange);
        bindText(R.id.job_age_loc, cdoUser.sCity + " · " + cdoUser.sJob + " · " + cdoUser.sAge);
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
        @DrawableRes int result;
        if (authType == 0) {
            result = R.drawable.ic_not_auth;
        } else if (authType == 3) {
            bindView(R.id.video_auth, true);
            result = R.drawable.ic_video_auth;
        } else {
            if (UserProfile.getInstance().getSex() == 1) {
                result = R.drawable.ic_reliable;
            } else {
                result = R.drawable.ic_info_auth;
            }
        }
        if (UserProfile.getInstance().getSex() == 0) {
            UserItemView auth = bindView(R.id.auth, true);
            auth.setHint(authType == 0 ? "经过认证的女生更受欢迎哦~" : "更新认证");
        }

        ImageView authStatus = bindView(R.id.auth_type);
        authStatus.setImageResource(result);
        ImageView header = bindView(R.id.header);
        ImageLoader.loadCenterCrop(getActivity(), cdoUser.sUserHeadPic, header, R.mipmap.ic_launcher);
        ImageLoader.blurTransformation(getActivity(), cdoUser.sUserHeadPic, bindView(R.id.header_bg), 4, 10);
        UserItemView money = bindView(R.id.money_bag);
        money.setHint(walletData.nMaskBallCoin + "假面币");
        UserItemView appVersion = bindView(R.id.app_version);
        appVersion.setHint(getAppVersion(Objects.requireNonNull(getActivity())));
        UserProfile.getInstance().setPhoneNo(cdoUser.sMobile);
        boolean show = userInfo.cdoimgList.size() > 0;
        bindView(R.id.setting_money_img, show && UserProfile.getInstance().getSex() == 0);
        bindView(R.id.upload_img_root, !show);
        if (show) {
            GridView recyclerView = bindView(R.id.user_center_photo);
            recyclerView.setVisibility(View.VISIBLE);
            UserCenterInfo.CdoimgListBean userCenterMyInfo = new UserCenterInfo.CdoimgListBean();
            userCenterMyInfo.last = true;
            userInfo.cdoimgList.add(userCenterMyInfo);
            DestroyPhotoTag destroyPhotoTag = new DestroyPhotoTag(userInfo.cdoimgList);
            recyclerView.setAdapter(destroyPhotoTag);
            destroyPhotoTag.setAddImageListener(new DestroyPhotoTag.AddImageListener() {
                @Override
                public void clickAdd() {
                    addPhoto();
                }

                @Override
                public void clickImage(UserCenterInfo.CdoimgListBean cdoimgListBean) {
                    getLaunchHelper().startActivity(PhotoDestroySelectActivity.getIntent(getActivity(), cdoimgListBean));
                }
            });
        }


    }

    @Override
    public void setUserHeaderSuccess(String url) {
        ImageView header = bindView(R.id.header);
        ImageLoader.loadCenterCrop(getActivity(), url, header, R.mipmap.ic_launcher);
    }

    @Override
    public void refreshData() {
        update(getArguments());
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
        Log.e("aaaa", "走了吗");
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
