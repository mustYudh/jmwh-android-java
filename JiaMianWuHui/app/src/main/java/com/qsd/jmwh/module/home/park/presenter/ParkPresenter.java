package com.qsd.jmwh.module.home.park.presenter;


import android.annotation.SuppressLint;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.module.register.bean.RangeData;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019/3/7
 */


public class ParkPresenter extends BaseViewPresenter<ParkViewer> {


    public ParkPresenter(ParkViewer viewer) {
        super(viewer);
    }


    @SuppressLint("CheckResult")
    public void getRangeData(int nLevel, int lParentId, int lUserId, String token, int type) {
        if (lUserId != -1) {
            XHttpProxy.proxy(ApiServices.class)
                    .getDateRange(nLevel, lParentId, lUserId, token)
                    .subscribeWith(new NoTipRequestSubscriber<RangeData>() {
                        @Override
                        protected void onSuccess(RangeData rangeData) {
                            assert getViewer() != null;
                            if (rangeData.cdoList != null) {
                                getViewer().setCity(rangeData.cdoList, type);
                            }
                        }
                    });
        }
    }

}