package com.qsd.jmwh.module.home.user.presenter;

import com.qsd.jmwh.module.home.user.bean.UserCenterInfo;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface UserViewer extends Viewer {
    void setUserInfo(UserCenterInfo userInfo);

    void setUserHeaderSuccess(String url);
}