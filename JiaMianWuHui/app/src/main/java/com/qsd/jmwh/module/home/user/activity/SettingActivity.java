package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.utils.GlideCacheUtil;
import com.qsd.jmwh.view.UserItemView;
import com.yu.common.toast.ToastUtils;

public class SettingActivity extends BaseBarActivity {

    private UserItemView phoneNumber;
    private UserItemView editPassword;
    private UserItemView push;
    private UserItemView logout;
    private UserItemView clearCache;
    private UserItemView agreement;
    GlideCacheUtil glideCacheUtil = GlideCacheUtil.getInstance();
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.setting_layout);
        initView();
    }

    private void initView() {
        phoneNumber = bindView(R.id.phone_number);
        editPassword = bindView(R.id.edit_password);
        agreement = bindView(R.id.agreement);
        logout = bindView(R.id.logout);
        clearCache = bindView(R.id.clear_cache);
        push = bindView(R.id.push);
        String cacheSize = glideCacheUtil.getCacheSize(getActivity());
        clearCache.setHint(cacheSize);
        initListener();
    }

    private void initListener() {
        push.setOnClickListener(v -> getLaunchHelper().startActivity(PushSettingActivity.class));
        phoneNumber.setOnClickListener(v -> getLaunchHelper().startActivity(SettingPhoneNumberActivity.class));
        editPassword.setOnClickListener(v -> getLaunchHelper().startActivity(EditPasswordActivity.class));
        agreement.setOnClickListener(v -> getLaunchHelper().startActivity(WebViewActivity.class));
        logout.setOnClickListener(v -> {
            SelectHintPop logoutPop = new SelectHintPop(getActivity());
            logoutPop.setTitle("温馨提示")
                    .setMessage("确认退出登录？")
                    .setPositiveButton("确定", v1 -> {
                        UserProfile.getInstance().clean();
                        logoutPop.dismiss();
                        finish();
                    })
                    .setNegativeButton("取消",v2 -> {
                        logoutPop.dismiss();
                    })
                    .showPopupWindow();
        });
        clearCache.setOnClickListener(v -> {
            glideCacheUtil.clearImageDiskCache(getActivity());
            ToastUtils.show("清除成功");
            clearCache.setHint("0.00Byte");
        });
    }

    @Override
    protected void loadData() {
        setTitle("设置");
    }


}
