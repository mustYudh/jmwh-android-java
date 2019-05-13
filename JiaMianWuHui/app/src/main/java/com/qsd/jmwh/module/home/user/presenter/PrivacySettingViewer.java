package com.qsd.jmwh.module.home.user.presenter;

import com.qsd.jmwh.module.home.user.bean.PrivacySettingStatusBean;
import com.yu.common.mvp.Viewer;

public interface PrivacySettingViewer extends Viewer {

    void getPrivacySettingStatus(PrivacySettingStatusBean statusBean);
    void setSuccess(int type,int status);

}
