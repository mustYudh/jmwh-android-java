package com.qsd.jmwh.module.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

public class EditRegisterCodeActivity extends BaseBarActivity implements View.OnClickListener {
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
            setContentView(R.layout.edit_register_code_activity_layout);
    }

    @Override
    protected void loadData() {
        setTitle("欢迎");
        initListener();
    }

    private void initListener() {
        bindView(R.id.to_register,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_register:
                getLaunchHelper().startActivity(EditUserDataActivity.class);
                break;
        }
    }
}
