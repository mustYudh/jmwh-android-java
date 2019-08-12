package com.qsd.jmwh.module.home.message.presenter;

import com.qsd.jmwh.module.home.message.bean.SystemCountBean;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019/3/7
 */
public interface MessageViewer extends Viewer {
  void getSystemMessageCount(SystemCountBean systemCountBean);

}