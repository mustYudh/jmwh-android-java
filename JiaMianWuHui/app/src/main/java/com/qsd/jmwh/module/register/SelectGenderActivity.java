package com.qsd.jmwh.module.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.module.register.presenter.SelectGenderPresenter;
import com.qsd.jmwh.module.register.presenter.SelectGenderViewer;
import com.yu.common.mvp.PresenterLifeCycle;

public class SelectGenderActivity extends BaseBarActivity implements SelectGenderViewer, View.OnClickListener {
    @PresenterLifeCycle
    SelectGenderPresenter mPresenter = new SelectGenderPresenter(this);
    private ImageView boy;
    private ImageView girle;
    private int currentType = 1;
    public static String APP_ACCOUNT = "APP_ACCOUNT";


    public static Intent getIntent(Context context, int account) {
        Intent intent = new Intent(context, SelectGenderActivity.class);
        intent.putExtra(APP_ACCOUNT, account);
        return intent;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.select_gender_activity_layout);
    }

    @Override
    protected void loadData() {
        setTitle("选择性别");
        boy = bindView(R.id.boy, this);
        girle = bindView(R.id.girle, this);
        bindView(R.id.next, this);
        boy.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boy:
                boy.setSelected(true);
                girle.setSelected(false);
                currentType = 1;
                break;
            case R.id.girle:
                boy.setSelected(false);
                girle.setSelected(true);
                currentType = 0;
                break;
            case R.id.next:
                SelectHintPop selectGenderHintPop = new SelectHintPop(this);
                selectGenderHintPop.setMessage("注册之后不能修改性别，并且，你不能与相同性别的用户交流。")
                        .setPositiveButton("确定", v1 -> {
                                    mPresenter.selectGender(currentType, getIntent().getIntExtra(APP_ACCOUNT, -1));
                                    selectGenderHintPop.dismiss();
                                }
                        )
                        .setNegativeButton("取消", v12 -> selectGenderHintPop.dismiss())
                        .showPopupWindow();

                break;
        }
    }


    @Override
    public void selectedSuccess(int type) {
        if (type == 0) {
            finish();
        } else {
            getLaunchHelper().startActivity(EditRegisterCodeActivity.class);
            finish();
        }
    }
}
