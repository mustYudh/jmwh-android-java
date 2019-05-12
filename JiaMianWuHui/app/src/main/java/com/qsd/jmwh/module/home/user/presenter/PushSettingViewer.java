package com.qsd.jmwh.module.home.user.presenter;

import com.qsd.jmwh.module.home.user.bean.PushSettingBean;
import com.yu.common.mvp.Viewer;

public interface PushSettingViewer extends Viewer {
    void getUserPushSetting(PushSettingBean pushSettingBean);
}
