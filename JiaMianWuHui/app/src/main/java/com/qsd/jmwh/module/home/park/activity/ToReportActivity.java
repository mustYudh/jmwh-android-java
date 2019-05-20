package com.qsd.jmwh.module.home.park.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.view.UserItemView;

public class ToReportActivity extends BaseBarActivity {
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.to_report_layout);
    }

    @Override
    protected void loadData() {
        UserItemView user_item_one = bindView(R.id.user_item_one);
        UserItemView user_item_two = bindView(R.id.user_item_two);
        UserItemView user_item_three = bindView(R.id.user_item_three);
        UserItemView user_item_four = bindView(R.id.user_item_four);
        UserItemView user_item_five = bindView(R.id.user_item_five);


        user_item_one.setSwichlistener(new UserItemView.SwitchListener() {
            @Override
            public void onSwitch(boolean switchStatus) {

            }
        });
        bindView(R.id.commit, v -> finish());
    }
}
