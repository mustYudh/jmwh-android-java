package com.qsd.jmwh.module.home.user.presenter;

import com.qsd.jmwh.module.home.user.bean.UserCenterMyInfo;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface UserViewer extends Viewer {
    void setUserInfo(UserCenterMyInfo userInfo);

    void setUserHeaderSuccess(String url);
}