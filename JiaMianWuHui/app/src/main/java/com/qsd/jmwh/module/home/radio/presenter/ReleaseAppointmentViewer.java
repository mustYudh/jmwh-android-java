package com.qsd.jmwh.module.home.radio.presenter;

import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface ReleaseAppointmentViewer extends Viewer {
    void addDatingSuccess();

    void getConfigDataSuccess(GetRadioConfigListBean configListBean);
}