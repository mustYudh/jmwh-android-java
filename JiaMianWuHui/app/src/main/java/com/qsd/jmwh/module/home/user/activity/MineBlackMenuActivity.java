package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

public class MineBlackMenuActivity extends BaseBarActivity {

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_black_menu);
    }

    @Override
    protected void loadData() {
        setTitle("黑名单");
    }
}
