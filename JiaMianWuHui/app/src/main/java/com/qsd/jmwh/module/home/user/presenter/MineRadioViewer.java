package com.qsd.jmwh.module.home.user.presenter;

import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.qsd.jmwh.module.home.radio.bean.LocalHomeRadioListBean;
import com.qsd.jmwh.module.home.user.bean.MineRadioListBean;
import com.qsd.jmwh.module.home.user.bean.UserCenterInfo;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface MineRadioViewer extends Viewer {
    void getMineRadioList(MineRadioListBean mineRadioListBean);

    void getModifyStatus(LocalHomeRadioListBean item);

    void getConfigDataSuccess(GetRadioConfigListBean configListBean);

    void getUserInfo(UserCenterInfo userCenterMyInfo);
}