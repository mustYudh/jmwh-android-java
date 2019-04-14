package com.qsd.jmwh.module.userinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

public class EditUserDataActivity extends BaseBarActivity {


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.edit_user_data_activity);

    }

    @Override
    protected void loadData() {
        setTitle("完善资料");
        initListener();

    }

    private void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
