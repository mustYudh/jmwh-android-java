package com.qsd.jmwh.module.home.message.presenter;

import com.qsd.jmwh.module.home.message.bean.SystemMessageBean;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019-06-24
 */
public interface MessageDetailVIewer extends Viewer {

  void getMessageDetail(SystemMessageBean bean);
}
