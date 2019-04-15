package com.qsd.jmwh.module.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

public class DateRangeActivity extends BaseBarActivity {
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
            setContentView(R.layout.data_range_activity);
    }

    @Override
    protected void loadData() {
        setTitle("约会范围");
        setRightMenu("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
