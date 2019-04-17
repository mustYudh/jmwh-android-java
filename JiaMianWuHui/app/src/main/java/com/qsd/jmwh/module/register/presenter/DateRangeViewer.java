package com.qsd.jmwh.module.register.presenter;

import com.qsd.jmwh.module.register.bean.RangeData;
import com.yu.common.mvp.Viewer;
import java.util.List;

/**
 * @author yudneghao
 * @date 2019/4/17
 */
public interface DateRangeViewer extends Viewer {

  public void setProvince(List<RangeData.Range> provinces);

  public void setCity(List<RangeData.Range> provinces);
}
