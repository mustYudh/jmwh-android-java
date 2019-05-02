package com.qsd.jmwh.module.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.module.home.HomeActivity;
import com.qsd.jmwh.module.login.bean.LoginInfo;
import com.qsd.jmwh.module.register.presenter.EditRegisterCodePresenter;
import com.qsd.jmwh.module.register.presenter.EditRegisterCodeViewer;
import com.qsd.jmwh.module.splash.bean.RegisterSuccess;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import org.greenrobot.eventbus.EventBus;

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
    private static final String TYPE = "sex";

    public static int GET_AUTH_CODE_REQUEST = 124;
    public static String GET_AUTH_CODE_RESULT = "get_auth_code_result";

    public static Intent getIntent(Context context, String token, int lUserId, int sex,int type) {
        Intent starter = new Intent(context, EditRegisterCodeActivity.class);
        starter.putExtra(TOKEN, token);
        starter.putExtra(USER_ID, lUserId);
        starter.putExtra(SEX, sex);
        starter.putExtra(TYPE, type);
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
                getLaunchHelper().startActivityForResult(
                        EditUserDataActivity.getIntent(getActivity(), getIntent().getStringExtra(TOKEN),
                                getIntent().getIntExtra(USER_ID, -1), getIntent().getIntExtra(SEX, -1)), GET_AUTH_CODE_REQUEST);
                break;
            case R.id.to_by:
                getLaunchHelper().startActivityForResult(ToByVipActivity.getIntent(getActivity(), getIntent().getIntExtra(USER_ID, -1), getIntent().getStringExtra(TOKEN),true),GET_AUTH_CODE_REQUEST);
                break;
            case R.id.login:
                EditText editText = bindView(R.id.code);
                String code = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(code)) {
                    SelectHintPop selectHintPop = new SelectHintPop(getActivity());
                    selectHintPop.setTitle("验证码验证通过")
                            .setMessage("欢迎加入假面舞会！请勿把您的的账户泄露给他人，一经发现登录异常，账户会被自动冻结。")
                            .setSingleButton("好的", v1 -> {
                                mPresenter.commitCode(getIntent().getIntExtra(USER_ID, -1), getIntent().getStringExtra(TOKEN), code);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            String result = data.getStringExtra(GET_AUTH_CODE_RESULT);
            Log.e("=========>获取成功",result);
            EditText editText = bindView(R.id.code);
            if (!TextUtils.isEmpty(result)) {
                editText.setText(result);
            }

        }
    }

    @Override
    public void registerSuccess() {
        if (getIntent().getIntExtra(TYPE,-1) == 0) {
            getLaunchHelper().startActivity(HomeActivity.class);
        } else {
            EventBus.getDefault().post(new RegisterSuccess(true));
            LoginInfo info = new LoginInfo();
            info.lUserId = getIntent().getIntExtra(USER_ID,-1);
            info.token = getIntent().getStringExtra(TOKEN);
            UserProfile.getInstance().appLogin(info);
            getLaunchHelper().startActivity(HomeActivity.class);
            finish();
        }

    }

    @Override
    public void getUserCode(String code) {
        EditText editText = bindView(R.id.code);
        editText.setText(code);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserCode(getIntent().getIntExtra(USER_ID,-1),getIntent().getStringExtra(TOKEN));
    }
}
