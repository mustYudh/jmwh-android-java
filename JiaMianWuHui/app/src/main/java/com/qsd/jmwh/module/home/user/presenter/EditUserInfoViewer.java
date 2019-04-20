package com.qsd.jmwh.module.home.user.presenter;

import com.yu.common.mvp.Viewer;

import java.util.List;

public interface EditUserInfoViewer extends Viewer {
    void showDateProjectList(List<String> list);
    void commitUserInfo();
}
