package com.qsd.jmwh.module.home.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarFragment;
import com.qsd.jmwh.module.home.message.presenter.MessagePresenter;
import com.qsd.jmwh.module.home.message.presenter.MessageViewer;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class MessageFragment extends BaseBarFragment implements MessageViewer {

    @PresenterLifeCycle
    MessagePresenter mPresenter = new MessagePresenter(this);



    @Override
    protected int getContentViewId() {
        return R.layout.message_fragment;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        setTitle("消息中心");
        showBack(false);
        setRightMenu("推送设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
