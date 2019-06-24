package com.qsd.jmwh.module.home.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.message.bean.SystemMessageBean;
import com.qsd.jmwh.module.home.message.presenter.MessageDetailPresenter;
import com.qsd.jmwh.module.home.message.presenter.MessageDetailVIewer;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * @author yudneghao
 * @date 2019-06-24
 */
public class MessageDetailActivity extends BaseBarActivity implements MessageDetailVIewer {

  @PresenterLifeCycle private MessageDetailPresenter mPresenter = new MessageDetailPresenter(this);

  private final static String TYPE = "type";

  public static Intent getIntent(Context context, int type) {
    Intent intent = new Intent(context, MessageDetailActivity.class);
    intent.putExtra(TYPE, type);
    return intent;
  }

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_message_detail_layout);
  }

  @Override protected void loadData() {
    int type = getIntent().getIntExtra(TYPE, -1);
    switch (type) {
      case 0:
        setTitle("系统消息");
        break;
      case 1:
        setTitle("电台广播");
        break;
      case 2:
        setTitle("收益提醒");
        break;
      case 4:
        setTitle("评价消息");
        break;
      default:
    }
    mPresenter.getMessageList(type);
  }

  @Override public void getMessageDetail(SystemMessageBean bean) {

  }
}
