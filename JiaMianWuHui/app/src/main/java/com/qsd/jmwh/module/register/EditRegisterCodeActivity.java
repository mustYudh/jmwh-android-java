package com.qsd.jmwh.module.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.module.ToByVipActivity;
import com.qsd.jmwh.module.home.HomeActivity;

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
        bindView(R.id.to_register, this);
        bindView(R.id.to_by, this);
        bindView(R.id.login, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_register:
                getLaunchHelper().startActivity(EditUserDataActivity.class);
                break;
            case R.id.to_by:
                getLaunchHelper().startActivity(ToByVipActivity.class);
                break;
            case R.id.login:
                SelectHintPop selectHintPop = new SelectHintPop(getActivity());
                selectHintPop.setTitle("验证码验证通过")
                        .setMessage("欢迎加入假面舞会！请勿把您的的账户泄露给他人，一经发现登录异常，账户会被自动冻结。")
                        .setSingleButton("好的", v1 -> {
                            selectHintPop.dismiss();
                            getLaunchHelper().startActivity(HomeActivity.class);
                            finish();
                        }).showPopupWindow();
                break;
            default:
        }
    }
}
