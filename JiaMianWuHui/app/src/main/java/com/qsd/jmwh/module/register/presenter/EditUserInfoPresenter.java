package com.qsd.jmwh.module.register.presenter;

import com.qsd.jmwh.module.register.bean.EditUserInfo;
import com.yu.common.framework.BaseViewPresenter;

public class EditUserInfoPresenter extends BaseViewPresenter<EditUserInfoViewer> {

    public EditUserInfoPresenter(EditUserInfoViewer viewer) {
        super(viewer);
    }

    public void setHeader(String url) {
        assert getViewer() != null;
        getViewer().setUserHeaderSuccess(url);
    }

    public void editUserInfo(EditUserInfo editUserInfo) {
        assert getViewer() != null;
        getViewer().commitUserInfo();
    }
}
