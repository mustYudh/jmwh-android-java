package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.bean.PushSettingBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class PushSettingPresenter extends BaseViewPresenter<PushSettingViewer> {

    public PushSettingPresenter(PushSettingViewer viewer) {
        super(viewer);
    }


    public void getPushSettingStatus() {
        XHttpProxy.proxy(OtherApiServices.class).getUserConfig().subscribeWith(new TipRequestSubscriber<PushSettingBean>() {
            @Override
            protected void onSuccess(PushSettingBean pushSettingBean) {
                assert getViewer() != null;
                getViewer().getUserPushSetting(pushSettingBean);
            }
        });
    }

    public void changePushConfig(int nSetting1,int nSetting2,int nSetting3,int nSetting4) {
        XHttpProxy.proxy(OtherApiServices.class)
                .setUserCofig(nSetting1,nSetting2,nSetting3,nSetting4)
                .subscribeWith(new TipRequestSubscriber<PushSettingBean>() {
            @Override
            protected void onSuccess(PushSettingBean pushSettingBean) {

            }
        });
    }
}
