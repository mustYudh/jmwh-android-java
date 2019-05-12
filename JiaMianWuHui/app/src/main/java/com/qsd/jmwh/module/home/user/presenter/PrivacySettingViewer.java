package com.qsd.jmwh.module.home.user.presenter;

import com.qsd.jmwh.module.home.user.bean.PrivacySettingStatusBean;
import com.yu.common.mvp.Viewer;

public interface PrivacySettingViewer extends Viewer {

    public void getPrivacySettingStatus(PrivacySettingStatusBean statusBean);

}
