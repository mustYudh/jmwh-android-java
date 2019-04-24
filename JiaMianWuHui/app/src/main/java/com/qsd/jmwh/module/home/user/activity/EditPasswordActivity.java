package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.user.presenter.EditPasswordPresenter;
import com.qsd.jmwh.module.home.user.presenter.EditPasswordViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

public class EditPasswordActivity extends BaseBarActivity implements EditPasswordViewer {
    @PresenterLifeCycle
    EditPasswordPresenter mPresenter = new EditPasswordPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.edit_password_layout);
    }

    @Override
    protected void loadData() {
        EditText oldPwdEdit = bindView(R.id.old_pwd);
        EditText newPwdEdit = bindView(R.id.new_pwd);
        EditText againPwdEdit = bindView(R.id.again_pwd);
        bindView(R.id.next, v -> mPresenter.modifyPassword(oldPwdEdit.getText().toString().trim(),
                newPwdEdit.getText().toString().trim(), againPwdEdit.getText().toString().trim()));
    }

    @Override
    public void onSuccess() {
        ToastUtils.show("修改成功");
        finish();
    }
}
