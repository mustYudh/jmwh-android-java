package com.qsd.jmwh.module.register;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

public class EditRegisterCodeActivity extends BaseBarActivity {
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
            setContentView(R.layout.edit_register_code_activity_layout);
    }

    @Override
    protected void loadData() {
        setTitle("欢迎");
    }
}
