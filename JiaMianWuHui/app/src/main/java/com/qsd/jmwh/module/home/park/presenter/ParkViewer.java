package com.qsd.jmwh.module.home.park.presenter;

import com.qsd.jmwh.module.register.bean.RangeData;
import com.yu.common.mvp.Viewer;

import java.util.List;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface ParkViewer extends Viewer {
    void setCity(List<RangeData.Range> provinces,int type);
}