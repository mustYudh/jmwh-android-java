package com.qsd.jmwh.module.register;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
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
                Log.e("======>","选择新北");
                SelectHintPop selectGenderHintPop = new SelectHintPop(this);
                selectGenderHintPop.setMessage("注册之后不能修改性别，并且，你不能与相同性别的用户交流。")
                        .setPositiveButton("确定", v1 -> {
                            Log.e("======>","选择新北1");
                            UserProfile.getInstance().setSex(currentType);
                            UserProfile.getInstance().setHomeSexType(currentType);
                                    mPresenter.selectGender(currentType, UserProfile.getInstance().getUserId());
                                    selectGenderHintPop.dismiss();
                                }
                        )
                        .setNegativeButton("取消", v12 -> selectGenderHintPop.dismiss())
                        .showPopupWindow();

                break;
                default:
        }
    }



    @Override
    public void selectedSuccess(int type) {
        if (type == 0) {
            setResult(Activity.RESULT_OK);
            getLaunchHelper().startActivity(EditUserDataActivity.class);
            finish();
        } else {
            getLaunchHelper().startActivity(EditRegisterCodeActivity.class);
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}
