package com.qsd.jmwh.module.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.register.adapter.RangCityAdapter;
import com.qsd.jmwh.module.register.adapter.RangeDataAdapter;
import com.qsd.jmwh.module.register.bean.RangeData;
import com.qsd.jmwh.module.register.presenter.DateRangePresenter;
import com.qsd.jmwh.module.register.presenter.DateRangeViewer;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;

public class DateRangeActivity extends BaseBarActivity implements DateRangeViewer {

    private static final String TOKEN = "token";
    private static final String USER_ID = "user_id";
    private static final String TITLE = "title";
    private static final String LEVEL = "level";
    public static final String RANGE_RESULT = "range_result";
    public static final int PROVINCE = 0;
    public static final int CITY = 1;

    @PresenterLifeCycle
    DateRangePresenter mPresenter = new DateRangePresenter(this);
    private RecyclerView mProvince;
    private RecyclerView mCity;
    private RangeDataAdapter mProvinceAdapter;
    private RangCityAdapter mRangCityAdapter;
    private RangeData.Range mRange;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.data_range_activity);
    }

    public static Intent getIntent(Context context, int level, String token, int lUserId, String title) {
        Intent starter = new Intent(context, DateRangeActivity.class);
        starter.putExtra(TOKEN, token);
        starter.putExtra(USER_ID, lUserId);
        starter.putExtra(LEVEL, level);
        starter.putExtra(TITLE, title);
        return starter;
    }

    @Override
    protected void loadData() {
        setTitle(getIntent().getStringExtra(TITLE));
        initView();
        Intent intent = getIntent();
        mPresenter.getRangeData(intent.getIntExtra(LEVEL, -1), 0, intent.getIntExtra(USER_ID, -1), intent.getStringExtra(TOKEN), PROVINCE);
    }

    private void initListener(Intent intent) {
        if (mProvinceAdapter != null) {
            mProvinceAdapter.setOnItemClickListener((adapter, view, position) -> {
                notifyDataSetChanged(intent, adapter, position, PROVINCE);
            });
        }
        if (mRangCityAdapter != null) {
            mRangCityAdapter.setOnItemClickListener((adapter, view, position) -> {
                notifyDataSetChanged(intent, adapter, position, CITY);
                mRange = (RangeData.Range) adapter.getData().get(position);
            });
        }
        setRightMenu("确定", v -> {
            if (mRange != null) {
                Intent result = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(RANGE_RESULT, mRange);
                result.putExtra(RANGE_RESULT, bundle);
                setResult(Activity.RESULT_OK, result);
            }
            finish();
        });
    }

    private void notifyDataSetChanged(Intent intent, BaseQuickAdapter adapter, int position,
                                      int type) {
        ArrayList<RangeData.Range> range = (ArrayList<RangeData.Range>) adapter.getData();
        for (Object datum : adapter.getData()) {
            RangeData.Range data = (RangeData.Range) datum;
            data.selected = false;
        }
        RangeData.Range selected = (RangeData.Range) adapter.getData().get(position);
        selected.selected = true;
        adapter.notifyDataSetChanged();
        if (type == PROVINCE) {
            mPresenter.getRangeData(intent.getIntExtra(LEVEL, -1), range.get(position).lId, intent.getIntExtra(USER_ID, -1),
                    intent.getStringExtra(TOKEN), CITY);
        }
    }

    private void initView() {
        mProvince = bindView(R.id.province);
        mProvince.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCity = bindView(R.id.city);
        mCity.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void setProvince(List<RangeData.Range> provinces) {
        if (provinces.size() > 0) {
            provinces.get(0).selected = true;
            mProvinceAdapter = new RangeDataAdapter(R.layout.item_range_province, provinces);
            mProvince.setAdapter(mProvinceAdapter);
            RangeData.Range range = provinces.get(0);
            mPresenter.getRangeData(getIntent().getIntExtra(LEVEL, -1), range.lId, getIntent().getIntExtra(USER_ID, -1), getIntent().getStringExtra(TOKEN), CITY);
            initListener(getIntent());
        }
    }

    @Override
    public void setCity(List<RangeData.Range> provinces) {
        mRangCityAdapter = new RangCityAdapter(R.layout.item_range_city_layout, provinces);
        mCity.setAdapter(mRangCityAdapter);
        initListener(getIntent());
    }
}
