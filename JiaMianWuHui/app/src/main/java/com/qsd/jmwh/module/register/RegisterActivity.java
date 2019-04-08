package com.qsd.jmwh.module.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

public class RegisterActivity extends BaseBarActivity implements View.OnClickListener {

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.register_activity_layout);
    }

    @Override
    protected void loadData() {
        setTitle("手机号码注册");
        bindView(R.id.next, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                getLaunchHelper().startActivity(SelectGenderActivity.class);
                break;
        }
    }
}
