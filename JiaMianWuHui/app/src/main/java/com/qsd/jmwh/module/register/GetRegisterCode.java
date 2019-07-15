package com.qsd.jmwh.module.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.register.presenter.GetRegisterPresenter;
import com.qsd.jmwh.module.register.presenter.GetRegisterViewer;
import com.yu.common.mvp.PresenterLifeCycle;

public class GetRegisterCode extends BaseBarActivity implements GetRegisterViewer {
    @PresenterLifeCycle
    private GetRegisterPresenter mPresenter = new GetRegisterPresenter(this);

    private EditText city;
    private EditText know;
    private EditText weChat;
    private EditText referees;

    private int userId = UserProfile.getInstance().getUserId();
    private String userToken = UserProfile.getInstance().getAppToken();
    private int userSex = UserProfile.getInstance().getSex();


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.get_register_code_activity);
    }



    @Override
    protected void loadData() {
        setTitle("申请邀请码");
        initView();
        bindView(R.id.next, v -> mPresenter.getRegisterCode(userId,
                userToken, getEditSting(know), getEditSting(referees), getEditSting(weChat), getEditSting(city)));
    }

    private void initView() {
        city = bindView(R.id.edit_city);
        know = bindView(R.id.where_know);
        weChat = bindView(R.id.we_chat);
        referees = bindView(R.id.referees);
    }


    private String getEditSting(EditText editText) {
        String text = editText.getText().toString().trim();
        return TextUtils.isEmpty(text) ? "" : text;
    }
}
