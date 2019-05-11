package com.qsd.jmwh.module.home.user.presenter;

import com.qsd.jmwh.module.home.user.bean.MineLikeBean;
import com.yu.common.mvp.Viewer;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface MineLikeViewer extends Viewer {
    void getMineLikeList(MineLikeBean mineLikeBean);

    void addLoveUserSuccess(boolean is_love, int position, DelayClickImageView iv_love);

    void addBlackUserSuccess(boolean is_black, int position, DelayClickTextView tv_black);
}