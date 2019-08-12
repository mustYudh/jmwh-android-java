package com.qsd.jmwh.module.home.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseFragment;
import com.qsd.jmwh.module.home.message.bean.SystemCountBean;
import com.qsd.jmwh.module.home.message.presenter.SystemMessagePresenter;
import com.qsd.jmwh.module.home.message.presenter.SystemMessageViewer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yu.common.mvp.PresenterLifeCycle;

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
  }

  @Override protected void loadData() {
    initListener();
    SmartRefreshLayout refreshLayout = bindView(R.id.refresh);
    refreshLayout.setOnRefreshListener(refreshLayout1 -> {
      mPresenter.getMessageCount();
          refreshLayout1.finishRefresh();
        }

    );
    refreshLayout.setEnableLoadMore(false);
  }

  @Override public void onResume() {
    super.onResume();
    mPresenter.getMessageCount();
  }

  private void initListener() {
    bindView(R.id.system_message_root,
        v -> getLaunchHelper().startActivity(MessageDetailActivity.getIntent(getContext(), 0)));
    bindView(R.id.broadcast_message_root,
        v -> getLaunchHelper().startActivity(MessageDetailActivity.getIntent(getContext(), 1)));
    bindView(R.id.earnings_message_root,
        v -> getLaunchHelper().startActivity(MessageDetailActivity.getIntent(getContext(), 2)));
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

    bindText(R.id.system_message, systemCountBean.cdoList);
    bindText(R.id.broadcast_message, systemCountBean.cdoList1);
    bindText(R.id.earnings_message, systemCountBean.cdoList2);
    bindText(R.id.evaluate_message, systemCountBean.cdoList3);
  }

  public void updateMessage() {
    mPresenter.getMessageCount();
  }
}
