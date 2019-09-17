package com.qsd.jmwh.module.register.presenter;

import com.qsd.jmwh.module.home.user.bean.GoodsInfoBean;
import com.qsd.jmwh.module.register.bean.VipInfoBean;
import com.yu.common.mvp.Viewer;
import java.util.List;

public interface ToByVipViewer  extends Viewer {
    void getVipInfo(VipInfoBean vipInfoBean);




    void setGoodsInfo(List<GoodsInfoBean.CdoListBean> goods);

}
