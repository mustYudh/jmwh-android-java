package com.qsd.jmwh.module.home.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.user.presenter.EditUserInfoPresenter;
import com.qsd.jmwh.module.home.user.presenter.EditUserInfoViewer;
import com.qsd.jmwh.module.register.DateRangeActivity;
import com.qsd.jmwh.module.register.bean.RangeData;
import com.qsd.jmwh.module.register.bean.SelectData;
import com.qsd.jmwh.module.register.dialog.RangeItemPop;
import com.qsd.jmwh.module.register.dialog.SelectInfoPop;
import com.qsd.jmwh.view.NormaFormItemVIew;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.qsd.jmwh.module.register.EditUserDataActivity.DATE_RANGE_REQUEST_CODE;
import static com.qsd.jmwh.module.register.EditUserDataActivity.PROJECT_REQUEST_CODE;

public class EditUserInfoActivity extends BaseBarActivity implements View.OnClickListener, EditUserInfoViewer {
    @PresenterLifeCycle
    private EditUserInfoPresenter mPresenter = new EditUserInfoPresenter(this);

    private NormaFormItemVIew location;
    private NormaFormItemVIew professional;
    private NormaFormItemVIew project;
    private NormaFormItemVIew height;
    private NormaFormItemVIew weight;
    private NormaFormItemVIew age;


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.edit_user_info_layout);
    }

    @Override
    protected void loadData() {
        setTitle("编辑资料");
        initView();
        initListener();
        setRightMenu("保存", v -> commitUserInfo());
    }

    private void initView() {
        location = bindView(R.id.location);
        professional = bindView(R.id.professional);
        project = bindView(R.id.project);
        height = bindView(R.id.height);
        weight = bindView(R.id.weight);
        age = bindView(R.id.age);
    }

    private void initListener() {
        bindView(R.id.login, this);
        bindView(R.id.agreement, this);
        initItemClickListener();
    }

    @Override
    public void onClick(View v) {

    }


    private void initItemClickListener() {
        location.setOnClickSelectedItem(v ->
                getLaunchHelper().startActivityForResult(
                        DateRangeActivity.getIntent(getActivity(), 1, UserProfile.getInstance().getAppToken(),
                                UserProfile.getInstance().getAppAccount(), "约会范围"), DATE_RANGE_REQUEST_CODE));
        professional.setOnClickSelectedItem(v ->
                getLaunchHelper().startActivityForResult(
                        DateRangeActivity.getIntent(getActivity(), 0, UserProfile.getInstance().getAppToken(),
                                UserProfile.getInstance().getAppAccount(), "职业"), PROJECT_REQUEST_CODE));
        project.setOnClickSelectedItem(v -> mPresenter.getDateProject());
        height.setOnClickSelectedItem(v -> {
            List<SelectData> datas = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                SelectData data = new SelectData();
                if (i != 0) {
                    data.key = 140 + i * 10;
                    data.value = 140 + i * 10 + "CM";
                } else {
                    data.key = -1;
                    data.value = "不显示";
                }
                datas.add(data);
            }
            SelectInfoPop infoPop = new SelectInfoPop(getActivity());
            infoPop.setTitle("请选择身高").setData(datas).setoNDataSelectedListener(selectData ->
                    height.setContentText(selectData.value)
            ).showPopupWindow();
        });
        weight.setOnClickSelectedItem(v -> {
            List<SelectData> datas = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                SelectData data = new SelectData();
                if (i != 0) {
                    data.key = 30 + i * 10;
                    data.value = 30 + i * 10 + "KG";
                } else {
                    data.key = -1;
                    data.value = "不显示";
                }
                datas.add(data);
            }
            SelectInfoPop infoPop = new SelectInfoPop(getActivity());
            infoPop.setTitle("请选择体重").setData(datas).setoNDataSelectedListener(selectData ->
                    weight.setContentText(selectData.value)).showPopupWindow();
        });
        age.setOnClickSelectedItem(v -> {
            List<SelectData> datas = new ArrayList<>();
            for (int i = 0; i < 46; i++) {
                SelectData data = new SelectData();
                data.key = 15 + i;
                data.value = 15 + i + "岁";
                datas.add(data);
            }
            SelectInfoPop infoPop = new SelectInfoPop(getActivity());
            infoPop.setTitle("请选择年龄").setData(datas).setoNDataSelectedListener(selectData ->
                    age.setContentText(selectData.value)
            ).showPopupWindow();
        });
    }

    @Override
    public void showDateProjectList(List<String> list) {
        RangeItemPop rangeItemPop = new RangeItemPop(getActivity());
        rangeItemPop.setData(list).setData(list).setOnSelectedProjectListener(selected -> {
            Set<Integer> keys = selected.keySet();
            for (int i = 0; i < keys.size(); i++) {
                if (i != keys.size() - 1) {

                }
            }
        }).showPopupWindow();
    }

    @Override
    public void commitUserInfo() {
        finish();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getBundleExtra(DateRangeActivity.RANGE_RESULT);
            RangeData.Range range = (RangeData.Range) bundle.getSerializable(DateRangeActivity.RANGE_RESULT);
            if (range != null) {
                if (requestCode == DATE_RANGE_REQUEST_CODE) {

                } else if (requestCode == PROJECT_REQUEST_CODE) {
                    professional.setContentText(range.sName);

                }
            }
        }
    }
}
