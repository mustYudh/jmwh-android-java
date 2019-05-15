package com.qsd.jmwh.module.home.user.presenter;

import com.qsd.jmwh.module.home.user.bean.AccountBalance;
import com.qsd.jmwh.module.home.user.bean.GoodsInfoBean;
import com.qsd.jmwh.module.home.user.bean.MaskBallCoinBean;
import com.yu.common.mvp.Viewer;

import java.util.List;

public interface JiaMianCoinViewer extends Viewer {

    void getInfo(AccountBalance accountBalance);

    void coinConvertMoney(MaskBallCoinBean maskBallCoinBean);


    void setGoodsInfo(List<GoodsInfoBean.CdoListBean> goods);
}
