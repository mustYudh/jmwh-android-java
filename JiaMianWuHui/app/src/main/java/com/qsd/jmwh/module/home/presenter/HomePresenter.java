package com.qsd.jmwh.module.home.presenter;

import android.os.Handler;
import com.qsd.jmwh.utils.LocationHelper;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019/3/7
 */


public class HomePresenter extends BaseViewPresenter<HomeViewer> {

    private Handler handler=new Handler();
    private Runnable runnable = new Runnable() {
        @Override public void run() {
            LocationHelper.getInstance(getActivity()).requestLocationToLocal();
            handler.postDelayed(this,1000 * 60 * 5);
        }
    };

    public HomePresenter(HomeViewer viewer) {
        super(viewer);
    }

    public void modifyLngAndLat() {
        handler.postDelayed(runnable, 1000 * 60 * 5);
    }

    @Override public void willDestroy() {
        super.willDestroy();
        handler.removeCallbacks(runnable);
    }
}