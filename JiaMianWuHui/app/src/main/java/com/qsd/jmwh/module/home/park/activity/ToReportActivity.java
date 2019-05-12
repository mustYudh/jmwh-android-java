package com.qsd.jmwh.module.home.park.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

public class ToReportActivity extends BaseBarActivity {
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.to_report_layout);
    }

    @Override
    protected void loadData() {
        bindView(R.id.commit, v -> finish());
    }
}
