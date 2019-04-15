package com.qsd.jmwh.module;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

public class ToByVipActivity extends BaseBarActivity {
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
            setContentView(R.layout.to_by_vip_activity);
    }

    @Override
    protected void loadData() {
            setTitle("会员中心");
    }
}
