package com.qsd.jmwh.module.login.presenter;

import com.qsd.jmwh.module.login.bean.LoginInfo;
import com.yu.common.mvp.Viewer;

public interface LoginViewer extends Viewer {
    void handleLoginResult(LoginInfo loginInfo);
}
