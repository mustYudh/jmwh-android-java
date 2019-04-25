package com.qsd.jmwh.module.home.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.yu.common.toast.ToastUtils;
import java.util.ArrayList;
import java.util.List;

import static com.qsd.jmwh.module.register.EditUserDataActivity.DATE_RANGE_REQUEST_CODE;
import static com.qsd.jmwh.module.register.EditUserDataActivity.PROJECT_REQUEST_CODE;

public class EditUserInfoActivity extends BaseBarActivity implements View.OnClickListener, EditUserInfoViewer {
    @PresenterLifeCycle
    private EditUserInfoPresenter mPresenter = new EditUserInfoPresenter(this);
    private List<String> ranges = new ArrayList<>();

    private NormaFormItemVIew location;
    private NormaFormItemVIew professional;
    private NormaFormItemVIew project;
    private NormaFormItemVIew height;
    private NormaFormItemVIew weight;
    private NormaFormItemVIew age;
    private NormaFormItemVIew qq;
    private NormaFormItemVIew weChat;
    private NormaFormItemVIew measure;


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
        qq = bindView(R.id.qq);
        weChat = bindView(R.id.we_chat);
        weChat = bindView(R.id.measure);
        bindView(R.id.measure,UserProfile.getInstance().getSex() == 0);
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
            if (selected.size() > 4) {
                ToastUtils.show("最多只能选择4个");
            } else {
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < selected.size(); i++) {
                    result.append(selected.get(i))
                        .append((i != selected.size() - 1) ? ";" : "");
                }
                project.setContentText(result.toString());
                rangeItemPop.dismiss();
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
                    if (ranges.size() < 4) {
                        if (range.selected && !TextUtils.isEmpty(range.sName)) {
                            if (!ranges.contains(range.sName)) {
                                ranges.add(range.sName);
                            } else {
                                ToastUtils.show("您已经选择过了");
                            }
                        }
                        StringBuilder result = new StringBuilder();
                        for (int i = 0; i < ranges.size(); i++) {
                            result.append(ranges.get(i))
                                .append((i != ranges.size() - 1) ? ";" : "");
                        }
                        location.setContentText(result.toString());
                    } else {
                        ToastUtils.show("最多只能选择4个,即将重新选择");
                        ranges.clear();
                        location.setContentText("");
                    }

                } else if (requestCode == PROJECT_REQUEST_CODE) {
                    professional.setContentText(range.sName);

                }
            }
        }
    }
}
