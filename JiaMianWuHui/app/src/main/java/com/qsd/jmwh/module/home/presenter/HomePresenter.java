package com.qsd.jmwh.module.home.presenter;

import com.qsd.jmwh.utils.LocationHelper;
import com.yu.common.framework.BaseViewPresenter;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;

/**
 * @author yudneghao
 * @date 2019/3/7
 */


public class HomePresenter extends BaseViewPresenter<HomeViewer> {


    private Disposable disposable;

    public HomePresenter(HomeViewer viewer) {
        super(viewer);
    }

    public void modifyLngAndLat() {
        disposable = Observable.interval(0, 5, TimeUnit.MINUTES)
            .map(aLong -> aLong + 1)
            .subscribe(count -> LocationHelper.getInstance(getActivity()).requestLocationToLocal());

    }

    @Override public void willDestroy() {
        super.willDestroy();
      if (disposable != null) {
        disposable.dispose();
      }
    }
}