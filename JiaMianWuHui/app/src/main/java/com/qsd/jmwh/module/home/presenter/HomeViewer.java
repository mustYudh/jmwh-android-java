package com.qsd.jmwh.module.home.presenter;

import com.qsd.jmwh.module.home.message.bean.SystemCountBean;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface HomeViewer extends Viewer {
  void getSystemMessageCount(SystemCountBean systemCountBean);
}