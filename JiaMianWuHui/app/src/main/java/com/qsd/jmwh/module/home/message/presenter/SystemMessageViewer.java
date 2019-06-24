package com.qsd.jmwh.module.home.message.presenter;

import com.qsd.jmwh.module.home.message.bean.SystemCountBean;
import com.qsd.jmwh.module.home.message.bean.SystemMessageBean;
import com.yu.common.mvp.Viewer;
import java.util.List;

/**
 * @author yudneghao
 * @date 2019-06-24
 */
public interface SystemMessageViewer extends Viewer {

  void getSystemMessage(List<SystemMessageBean.CdoListBean> cdoList);

  void getBroadcastMessage(List<SystemMessageBean.CdoListBean> cdoList);

  void getEarningsMessage(List<SystemMessageBean.CdoListBean> cdoList);

  void getEvaluateMessage(List<SystemMessageBean.CdoListBean> cdoList);

  void getSystemMessageCount(SystemCountBean systemCountBean);
}
