package com.qsd.jmwh.module.home.message.presenter;

import com.qsd.jmwh.module.home.message.bean.SystemCountBean;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019-06-24
 */
public interface SystemMessageViewer extends Viewer {



  void getSystemMessageCount(SystemCountBean systemCountBean);
}
