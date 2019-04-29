package com.qsd.jmwh.module.home.radio.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

public class ReleaseAppointmentActivity extends BaseBarActivity {

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_release_appointment);
    }

    @Override
    protected void loadData() {
        setTitle("发布约会");
    }
}
