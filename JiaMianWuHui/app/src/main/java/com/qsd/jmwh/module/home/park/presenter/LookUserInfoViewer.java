package com.qsd.jmwh.module.home.park.presenter;

import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.yu.common.mvp.Viewer;

public interface LookUserInfoViewer extends Viewer {

    void setUserInfo(OtherUserInfoBean userCenterInfo);

    void refreshData();

}
