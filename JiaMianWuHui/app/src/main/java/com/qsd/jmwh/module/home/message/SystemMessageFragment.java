package com.qsd.jmwh.module.home.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseFragment;
import com.qsd.jmwh.module.home.message.bean.SystemCountBean;
import com.qsd.jmwh.module.home.message.bean.SystemMessageBean;
import com.qsd.jmwh.module.home.message.presenter.SystemMessagePresenter;
import com.qsd.jmwh.module.home.message.presenter.SystemMessageViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import java.util.List;

/**
 * @author yudneghao
 * @date 2019-06-23
 */
public class SystemMessageFragment extends BaseFragment implements SystemMessageViewer {

  @PresenterLifeCycle private SystemMessagePresenter mPresenter = new SystemMessagePresenter(this);

  @Override protected int getContentViewId() {
    return R.layout.fragment_system_message_layout;
  }

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    mPresenter.getMessageList(0);
    mPresenter.getMessageList(1);
    mPresenter.getMessageList(2);
    mPresenter.getMessageList(4);
    mPresenter.getMessageCount();
  }

  @Override protected void loadData() {

  }

  @Override public void getSystemMessage(List<SystemMessageBean.CdoListBean> cdoList) {
    if (cdoList != null && cdoList.size() > 0) {
      bindText(R.id.system_message, cdoList.get(0).sContent);
    } else {
      bindText(R.id.system_message, "暂无");
    }
    bindView(R.id.system_message_root,
        v -> getLaunchHelper().startActivity(MessageDetailActivity.getIntent(getContext(), 0)));
  }

  @Override public void getBroadcastMessage(List<SystemMessageBean.CdoListBean> cdoList) {
    if (cdoList != null && cdoList.size() > 0) {
      bindText(R.id.broadcast_message, cdoList.get(0).sContent);
    } else {
      bindText(R.id.broadcast_message, "暂无");
    }
    bindView(R.id.broadcast_message_root,
        v -> getLaunchHelper().startActivity(MessageDetailActivity.getIntent(getContext(), 1)));
  }

  @Override public void getEarningsMessage(List<SystemMessageBean.CdoListBean> cdoList) {
    if (cdoList != null && cdoList.size() > 0) {
      bindText(R.id.earnings_message, cdoList.get(0).sContent);
    } else {
      bindText(R.id.earnings_message, "暂无");
    }
    bindView(R.id.earnings_message_root,
        v -> getLaunchHelper().startActivity(MessageDetailActivity.getIntent(getContext(), 2)));
  }

  @Override public void getEvaluateMessage(List<SystemMessageBean.CdoListBean> cdoList) {
    if (cdoList != null && cdoList.size() > 0) {
      bindText(R.id.evaluate_message, cdoList.get(0).sContent);
    } else {
      bindText(R.id.evaluate_message, "暂无");
    }
    bindView(R.id.evaluate_message_root,
        v -> getLaunchHelper().startActivity(MessageDetailActivity.getIntent(getContext(), 4)));
  }

  @Override public void getSystemMessageCount(SystemCountBean systemCountBean) {
    bindText(R.id.system_message_count,
        systemCountBean.nCount < 99 ? (systemCountBean.nCount + "") : "99+");
    bindView(R.id.system_message_count, systemCountBean.nCount > 0);

    bindText(R.id.broadcast_message_count,
        systemCountBean.nCount1 < 99 ? (systemCountBean.nCount1 + "") : "99+");
    bindView(R.id.broadcast_message_count, systemCountBean.nCount1 > 0);

    bindText(R.id.earnings_message_count,
        systemCountBean.nCount2 < 99 ? (systemCountBean.nCount2 + "") : "99+");
    bindView(R.id.earnings_message_count, systemCountBean.nCount2 > 0);

    bindText(R.id.evaluate_message_count,
        systemCountBean.nCount3 < 99 ? (systemCountBean.nCount3 + "") : "99+");
    bindView(R.id.evaluate_message_count, systemCountBean.nCount3 > 0);
  }
}
