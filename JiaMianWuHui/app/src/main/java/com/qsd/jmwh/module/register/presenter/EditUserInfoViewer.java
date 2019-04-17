package com.qsd.jmwh.module.register.presenter;

import com.yu.common.mvp.Viewer;

import java.util.List;

public interface EditUserInfoViewer extends Viewer {
    void setUserHeaderSuccess(String url);
    void showDateProjectList(List<String> list);
    void commitUserInfo();
}
