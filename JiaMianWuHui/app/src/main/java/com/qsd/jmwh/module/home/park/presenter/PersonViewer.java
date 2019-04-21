package com.qsd.jmwh.module.home.park.presenter;

import com.qsd.jmwh.module.home.park.bean.HomePersonListBean;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface PersonViewer extends Viewer {
    void getDataSuccess(HomePersonListBean homePersonListBean);
}