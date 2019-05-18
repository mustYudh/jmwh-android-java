package com.qsd.jmwh.module.home.user.presenter;

import com.qsd.jmwh.module.home.user.bean.AccountBalance;
import com.qsd.jmwh.module.home.user.bean.MaskBallCoinBean;
import com.yu.common.mvp.Viewer;

public interface JiaMianCoinViewer extends Viewer {

    void getInfo(AccountBalance accountBalance);

    void coinConvertMoney(MaskBallCoinBean maskBallCoinBean);
}
