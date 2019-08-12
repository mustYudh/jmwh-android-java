package com.qsd.jmwh.module.home.message.presenter;

import android.view.View;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.module.home.message.bean.SystemCountBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019/3/7
 */


public class MessagePresenter extends BaseViewPresenter<MessageViewer> {

    public void getMessageCount() {
        XHttpProxy.proxy(OtherApiServices.class)
            .getMsgCount()
            .subscribeWith(new NoTipRequestSubscriber<SystemCountBean>() {
                @Override protected void onSuccess(SystemCountBean bean) {
                    assert getViewer() != null;
                    getViewer().getSystemMessageCount(bean);
                }
            });
    }
    public MessagePresenter(MessageViewer viewer) {
        super(viewer);
    }

    @Override
    public void createdView(View view) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void willDestroy() {

    }
}