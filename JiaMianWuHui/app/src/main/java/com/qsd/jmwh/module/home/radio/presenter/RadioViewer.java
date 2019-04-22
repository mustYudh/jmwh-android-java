package com.qsd.jmwh.module.home.radio.presenter;

import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.qsd.jmwh.module.home.radio.bean.HomeRadioListBean;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface RadioViewer extends Viewer {
    void getDataSuccess(HomeRadioListBean homeRadioListBean);

    void getConfigDataSuccess(GetRadioConfigListBean configListBean);
}