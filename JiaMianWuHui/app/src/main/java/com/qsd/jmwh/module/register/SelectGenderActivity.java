package com.qsd.jmwh.module.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.dialog.SelectGenderHintPop;
import com.qsd.jmwh.module.home.HomeActivity;

public class SelectGenderActivity extends BaseBarActivity implements View.OnClickListener {
    private ImageView boy;
    private ImageView girle;
    private int currentType = 0;
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.select_gender_activity_layout);
    }

    @Override
    protected void loadData() {
        setTitle("选择性别");
        boy = bindView(R.id.boy,this);
        girle = bindView(R.id.girle,this);
        bindView(R.id.next,this);
        boy.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boy:
                boy.setSelected(true);
                girle.setSelected(false);
                currentType = 0;
                break;
            case R.id.girle:
                boy.setSelected(false);
                girle.setSelected(true);
                currentType = 1;
                break;
            case R.id.next:
                doNext();
                break;
        }
    }

    private void doNext() {
        if (currentType == 1) {
            getLaunchHelper().startActivity(HomeActivity.class);
        } else {
            SelectGenderHintPop selectGenderHintPop  = new SelectGenderHintPop(this);
            selectGenderHintPop.showPopupWindow();
            selectGenderHintPop.setOnItemClickListener(new SelectGenderHintPop.ItemClickListener() {
                @Override
                public void onNext() {
                    getLaunchHelper().startActivity(EditRegisterCodeActivity.class);
                }

                @Override
                public void onCancel() {
                    selectGenderHintPop.dismiss();
                }
            });
        }
    }
}
