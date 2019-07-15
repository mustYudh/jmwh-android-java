package com.qsd.jmwh.module.register.presenter;

import com.qsd.jmwh.module.login.bean.LoginInfo;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019/4/12
 */
public interface RegisterViewer extends Viewer {
    void registerSuccess(LoginInfo registerBean);
}
