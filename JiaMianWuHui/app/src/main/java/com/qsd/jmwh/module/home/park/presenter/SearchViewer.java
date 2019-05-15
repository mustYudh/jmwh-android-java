package com.qsd.jmwh.module.home.park.presenter;

import com.qsd.jmwh.module.home.park.bean.HomePersonListBean;
import com.yu.common.mvp.Viewer;
import com.yu.common.ui.DelayClickImageView;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface SearchViewer extends Viewer {
    void getDataSuccess(HomePersonListBean homePersonListBean);

    void addLoveUserSuccess(int position, DelayClickImageView iv_love);

    void delLoveUserSuccess(int position, DelayClickImageView iv_love);
}