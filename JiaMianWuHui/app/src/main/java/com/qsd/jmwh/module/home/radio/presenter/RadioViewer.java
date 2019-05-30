package com.qsd.jmwh.module.home.radio.presenter;

import com.qsd.jmwh.module.home.radio.bean.GetDatingUserVipBean;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.qsd.jmwh.module.home.radio.bean.HomeRadioListBean;
import com.qsd.jmwh.module.home.radio.bean.LocalHomeRadioListBean;
import com.qsd.jmwh.module.register.bean.PayInfo;
import com.yu.common.mvp.Viewer;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface RadioViewer extends Viewer {
    void getDataSuccess(HomeRadioListBean homeRadioListBean);

    void getConfigDataSuccess(GetRadioConfigListBean configListBean);

    void addDatingLikeCountSuccess(DelayClickImageView iv_like, DelayClickTextView tv_like, int position, int is_like);

    void getDatingUserVIPSuccess(int type,GetDatingUserVipBean getDatingUserVipBean,LocalHomeRadioListBean item);

    void addDatingCommentCountSuccess(LocalHomeRadioListBean item);

    void addDatingEnrollSuccess(LocalHomeRadioListBean item);

    void getDatingUserVIPPaySuccess(GetDatingUserVipBean getDatingUserVipBean,String name);

    void getBuyDatingPaySignSuccess(PayInfo payInfo,String name);
}