package com.qsd.jmwh.module.home.user.presenter;

import com.qsd.jmwh.module.home.user.bean.WomenVideoBean;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019-05-17
 */
public interface AuthCenterViewer extends Viewer {

  void getInfo(WomenVideoBean video);

  void uploadSuccess();

  void setAuthCode(String code);
}
