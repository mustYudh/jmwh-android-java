package com.qsd.jmwh.module.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.register.adapter.RangeProvinceAdapter;
import com.qsd.jmwh.module.register.bean.RangeData;
import com.qsd.jmwh.module.register.presenter.DateRangePresenter;
import com.qsd.jmwh.module.register.presenter.DateRangeViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import java.util.List;

public class DateRangeActivity extends BaseBarActivity implements DateRangeViewer {

  private static final String TOKEN = "token";
  private static final String USER_ID = "user_id";
  public static final int PROVINCE = 0;
  public static final int CITY = 1;


  @PresenterLifeCycle DateRangePresenter mPresenter = new DateRangePresenter(this);
  private RecyclerView mProvince;
  private RangeProvinceAdapter mProvinceAdapter;

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.data_range_activity);
  }


  public static Intent getIntent(Context context, String token, int lUserId) {
    Intent starter = new Intent(context, DateRangeActivity.class);
    starter.putExtra(TOKEN, token);
    starter.putExtra(USER_ID, lUserId);
    return starter;
  }

  @Override protected void loadData() {
    setTitle("约会范围");
    initView();
    Intent intent = getIntent();
    mPresenter.getRangeData(1, 0, intent.getIntExtra(USER_ID, -1), intent.getStringExtra(TOKEN),PROVINCE);
    initListener(intent);
  }

  private void initListener(Intent intent) {
    if (mProvinceAdapter != null) {
      mProvinceAdapter.setOnItemClickListener((adapter, view, position) -> {
        RangeData.Range range = (RangeData.Range) adapter.getData();
        mPresenter.getRangeData(1, range.lId, intent.getIntExtra(USER_ID, -1), intent.getStringExtra(TOKEN),PROVINCE);
      });
    }
    setRightMenu("确定", v -> finish());
  }

  private void initView() {
    mProvince = bindView(R.id.province);
    mProvince.setLayoutManager(new LinearLayoutManager(getActivity()));
  }

  @Override public void setProvince(List<RangeData.Range> provinces) {
    mProvinceAdapter = new RangeProvinceAdapter(R.layout.item_range_province, provinces);
    mProvince.setAdapter(mProvinceAdapter);
  }

  @Override public void setCity(List<RangeData.Range> provinces) {

  }
}
