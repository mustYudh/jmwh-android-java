package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.user.bean.PushSettingBean;
import com.qsd.jmwh.module.home.user.presenter.PushSettingPresenter;
import com.qsd.jmwh.module.home.user.presenter.PushSettingViewer;
import com.qsd.jmwh.view.UserItemView;

public class PushSettingActivity extends BaseBarActivity implements PushSettingViewer {

    private PushSettingPresenter mPresenter = new PushSettingPresenter(this);
    private UserItemView setting1;
    private UserItemView setting2;
    private UserItemView setting3;
    private UserItemView setting4;

    private int setting1Status1;
    private int setting1Status2;
    private int setting1Status3;
    private int setting1Status4;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.push_setting_layout);
    }

    @Override
    protected void loadData() {
        setTitle("消息推送设置");
        mPresenter.getPushSettingStatus();
        setting1 = bindView(R.id.setting1);
        setting2 = bindView(R.id.setting2);
        setting3 = bindView(R.id.setting3);
        setting4 = bindView(R.id.setting4);
        setting1.setSwichlistener(switchStatus -> {
            setting1Status1 = switchStatus ? 1 : 0;
            mPresenter.changePushConfig(setting1Status1, setting1Status2, setting1Status3, setting1Status4);
        });
        setting2.setSwichlistener(switchStatus -> {
            setting1Status2 = switchStatus ? 1 : 0;
            mPresenter.changePushConfig(setting1Status1, setting1Status2, setting1Status3, setting1Status4);
        });
        setting3.setSwichlistener(switchStatus -> {
            setting1Status3 = switchStatus ? 1 : 0;
            mPresenter.changePushConfig(setting1Status1, setting1Status2, setting1Status3, setting1Status4);
        });
        setting4.setSwichlistener(switchStatus -> {
            setting1Status4 = switchStatus ? 1 : 0;
            mPresenter.changePushConfig(setting1Status1, setting1Status2, setting1Status3, setting1Status4);
        });
    }

    @Override
    public void getUserPushSetting(PushSettingBean pushSettingBean) {
        setting1Status1 = pushSettingBean.cdoData.nSetting1;
        setting1Status2 = pushSettingBean.cdoData.nSetting2;
        setting1Status3 = pushSettingBean.cdoData.nSetting3;
        setting1Status4 = pushSettingBean.cdoData.nSetting4;
        setting1.setSwichButton(setting1Status1 == 1);
        setting2.setSwichButton(setting1Status2 == 1);
        setting3.setSwichButton(setting1Status3 == 1);
        setting4.setSwichButton(setting1Status4 == 1);

    }
}
