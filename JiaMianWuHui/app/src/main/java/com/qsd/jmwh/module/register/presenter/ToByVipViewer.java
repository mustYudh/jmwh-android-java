package com.qsd.jmwh.module.register.presenter;

import com.qsd.jmwh.module.home.user.bean.AccountBalance;
import com.qsd.jmwh.module.register.bean.VipInfoBean;
import com.yu.common.mvp.Viewer;

public interface ToByVipViewer  extends Viewer {
    void getVipInfo(VipInfoBean vipInfoBean);


    void coinConvertMoney(AccountBalance maskBallCoinBean);

}
