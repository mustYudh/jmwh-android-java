package com.qsd.jmwh.module.home.park.presenter;

import com.qsd.jmwh.module.home.user.bean.UserCenterInfo;
import com.yu.common.mvp.Viewer;

public interface LookUserInfoViewer extends Viewer {

    void setUserInfo(UserCenterInfo userCenterInfo);
}
