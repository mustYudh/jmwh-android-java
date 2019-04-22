package com.qsd.jmwh.module.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.module.register.presenter.EditRegisterCodePresenter;
import com.qsd.jmwh.module.register.presenter.EditRegisterCodeViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

public class EditRegisterCodeActivity extends BaseBarActivity implements View.OnClickListener, EditRegisterCodeViewer {
    @PresenterLifeCycle
    EditRegisterCodePresenter mPresenter = new EditRegisterCodePresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.edit_register_code_activity_layout);
    }

    private static final String TOKEN = "token";
    private static final String USER_ID = "user_id";
    private static final String SEX = "sex";

    public static Intent getIntent(Context context, String token, int lUserId,int sex) {
        Intent starter = new Intent(context, EditRegisterCodeActivity.class);
        starter.putExtra(TOKEN, token);
        starter.putExtra(USER_ID, lUserId);
        starter.putExtra(SEX, sex);
        return starter;
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
                getLaunchHelper().startActivity(
                        EditUserDataActivity.getIntent(getActivity(), getIntent().getStringExtra(TOKEN),
                                getIntent().getIntExtra(USER_ID, -1),getIntent().getIntExtra(SEX,-1)));
                break;
            case R.id.to_by:
                getLaunchHelper().startActivity(ToByVipActivity.getIntent(getActivity(), getIntent().getIntExtra(USER_ID, -1), getIntent().getStringExtra(TOKEN)));
                break;
            case R.id.login:
                EditText editText = bindView(R.id.code);
                String code = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(code)) {
                    SelectHintPop selectHintPop = new SelectHintPop(getActivity());
                    selectHintPop.setTitle("验证码验证通过")
                            .setMessage("欢迎加入假面舞会！请勿把您的的账户泄露给他人，一经发现登录异常，账户会被自动冻结。")
                            .setSingleButton("好的", v1 -> {
                                mPresenter.commitCode(getIntent().getIntExtra(USER_ID, -1),getIntent().getStringExtra(TOKEN),code);
                                selectHintPop.dismiss();
                            })
                            .showPopupWindow();
                } else {
                    ToastUtils.show("邀请码输入不能为空");
                }
                break;
            default:
        }
    }

    @Override
    public void registerSuccess() {
        ToastUtils.show("认证通过，请登录");
        finish();
    }
}
