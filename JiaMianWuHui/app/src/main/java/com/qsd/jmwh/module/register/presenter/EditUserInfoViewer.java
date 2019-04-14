package com.qsd.jmwh.module.register.presenter;

import com.yu.common.mvp.Viewer;

public interface EditUserInfoViewer extends Viewer {
    void setUserHeaderSuccess(String url);
    void commitUserInfo();
}
