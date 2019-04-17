package com.qsd.jmwh.module.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.register.presenter.DateRangePresenter;
import com.qsd.jmwh.module.register.presenter.DateRangeViewer;
import com.yu.common.mvp.PresenterLifeCycle;

public class DateRangeActivity extends BaseBarActivity implements DateRangeViewer {

  private static final String TOKEN = "token";
  private static final String USER_ID = "user_id";

  @PresenterLifeCycle DateRangePresenter mPresenter = new DateRangePresenter(this);

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.data_range_activity);
  }

  public static void getIntent(Context context, String token, String lUserId) {
    Intent starter = new Intent(context, DateRangeActivity.class);
    starter.putExtra(TOKEN, token);
    starter.putExtra(USER_ID, lUserId);
    context.startActivity(starter);
  }

  @Override protected void loadData() {
    setTitle("约会范围");
    Intent intent = getIntent();
    mPresenter.getRangeData(1, 0, intent.getIntExtra(USER_ID, -1), intent.getStringExtra(TOKEN));
    setRightMenu("确定", v -> finish());
  }
}
