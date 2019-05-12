package com.qsd.jmwh.module.home.park.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.qsd.jmwh.module.home.park.presenter.LookPhotoPresenter;
import com.qsd.jmwh.module.home.park.presenter.LookPhotoViewer;
import com.qsd.jmwh.module.register.ToByVipActivity;
import com.qsd.jmwh.utils.countdown.RxCountDown;
import com.qsd.jmwh.utils.countdown.RxCountDownAdapter;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.ImageLoader;

public class LookPhotoActivity extends BaseActivity implements LookPhotoViewer {
    private final static String DATA = "data";
    private final static String IS_VIP = "is_vip";
    private final static String USER_ID = "user_id";
    private ImageView photo;
    private TextView timeText;
    private TextView destroyTitle;
    private String url;
    private int nPayType;
    private int nBrowseInfType;
    @PresenterLifeCycle
    private LookPhotoPresenter mPresenter = new LookPhotoPresenter(this);
    private RxCountDown rxCountDown = new RxCountDown();
    private OtherUserInfoBean.CdoFileListDataBean data;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.look_photo_layout);
    }

    public static Intent getIntent(Context context, OtherUserInfoBean.CdoFileListDataBean data, boolean isVip, int userId) {
        Intent starter = new Intent(context, LookPhotoActivity.class);
        starter.putExtra(DATA, data);
        starter.putExtra(IS_VIP, isVip);
        starter.putExtra(USER_ID, userId);
        return starter;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void loadData() {
        data = (OtherUserInfoBean.CdoFileListDataBean) getIntent().getSerializableExtra(DATA);
        url = data.sFileUrl;
        int type = data.nFileType;
        photo = bindView(R.id.photo);
        timeText = bindView(R.id.right_menu);
        destroyTitle = bindView(R.id.title);
        if (type == 0) {
            bindView(R.id.destroy_img_toot, false);
            ImageLoader.loadCenterCrop(getActivity(), url, photo);
        } else if (type == 1) {
            showView(timeText, url, photo, false);
            nPayType = 0;
            nBrowseInfType = 1;
            lookPhoto();
        } else if (type == 2) {
            ImageView imageView = bindView(R.id.destroy_root_bg, true);
            imageView.setImageResource(R.drawable.ic_red_bag_bg);
            bindText(R.id.money, data.nFileFee + "");
            ImageLoader.blurTransformation(getActivity(), url, photo, 20, 3);
            bindView(R.id.red_bag_root, true);
            nPayType = data.nFileFee;
            nBrowseInfType = 3;
        }
        bindView(R.id.back, v -> onBackPressed());
        bindView(R.id.buy_vip, v -> {
                    LauncherHelper.from(getActivity()).startActivity(ToByVipActivity
                            .getIntent(getActivity(), UserProfile.getInstance().getAppAccount(),
                                    UserProfile.getInstance().getAppToken(), false));
                    finish();
                }
        );
        bindView(R.id.pay, v -> {
            SelectHintPop pay = new SelectHintPop(this);
            pay.setMessage("确认支付")
                    .setPositiveButton("确定", v1 -> {
                        mPresenter.pay(data.lFileId, data.nFileFee);
                        pay.dismiss();
                    })
                    .setNegativeButton("取消", v12 -> pay.dismiss())
                    .showPopupWindow();
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void lookPhoto() {
        boolean isVip = getIntent().getBooleanExtra(IS_VIP, false);
        rxCountDown.setCountDownTimeListener(new RxCountDownAdapter() {
            @Override
            public void onStart() {
                super.onStart();
                showView(timeText, url, photo, true);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onNext(Integer time) {
                super.onNext(time);
                timeText.setText(time + "S");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showView(timeText, url, photo, false);

            }

            @Override
            public void onComplete() {
                super.onComplete();
                showView(timeText, url, photo, false);
                destroyTitle.setText("照片已经焚毁");
                if (!isVip) {
                    bindText(R.id.vip_hint, "会员可延长查看时间至6秒");
                    bindView(R.id.buy_vip, true);
                } else {
                    bindView(R.id.vip_hint, false);
                }
            }
        });
        photo.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPresenter.addBrowsingHis(getIntent().getIntExtra(USER_ID, -1), data.lFileId, nPayType, nBrowseInfType);
                    break;
                case MotionEvent.ACTION_UP:
                    rxCountDown.stop();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    rxCountDown.stop();
                    break;
            }
            return true;
        });
    }

    public void startLookPhoto() {
        boolean isVip = getIntent().getBooleanExtra(IS_VIP, false);
        rxCountDown.start(isVip ? 6 : 2);
    }

    private void showView(TextView timeText, String url, ImageView photo, boolean show) {
        ImageView imageView = bindView(R.id.destroy_root_bg, true);
        if (show) {
            imageView.setVisibility(View.GONE);
            bindView(R.id.destroy_img_toot, false);
            ImageLoader.loadCenterCrop(getActivity(), url, photo);
        } else {
            imageView.setImageResource(R.drawable.ic_destroy_img_bg);
            imageView.setVisibility(View.VISIBLE);
            ImageLoader.blurTransformation(getActivity(), url, photo, 20, 3);
            bindView(R.id.destroy_img_toot, true);
            timeText.setText("");
        }
    }


    @Override
    public void paySuccess() {
        bindView(R.id.red_bag_root, false);
        showView(timeText, url, photo, false);
        lookPhoto();
    }
}
