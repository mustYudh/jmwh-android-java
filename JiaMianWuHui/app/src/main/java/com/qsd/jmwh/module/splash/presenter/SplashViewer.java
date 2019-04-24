package com.qsd.jmwh.module.splash.presenter;

import com.qsd.jmwh.module.login.bean.LoginInfo;
import com.yu.common.mvp.Viewer;

public interface SplashViewer extends Viewer {
    void authLoginSuccess(LoginInfo loginInfo);
}
