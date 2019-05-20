package com.qsd.jmwh.module.home.user.presenter;

import com.qsd.jmwh.module.home.radio.bean.LocalHomeRadioListBean;
import com.qsd.jmwh.module.home.user.bean.MineRadioListBean;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface MineRadioViewer extends Viewer {
    void getMineRadioList(MineRadioListBean mineRadioListBean);

    void getModifyStatus();
}