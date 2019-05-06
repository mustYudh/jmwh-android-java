package com.qsd.jmwh.module.home.user.presenter;

import com.qsd.jmwh.module.home.user.bean.MineLikeBean;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface MineLikeViewer extends Viewer {
    void getMineLikeList(MineLikeBean mineLikeBean);
}