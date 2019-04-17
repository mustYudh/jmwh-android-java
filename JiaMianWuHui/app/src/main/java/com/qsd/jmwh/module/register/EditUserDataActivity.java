package com.qsd.jmwh.module.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.HomeActivity;
import com.qsd.jmwh.module.register.bean.EditUserInfo;
import com.qsd.jmwh.module.register.bean.SelectData;
import com.qsd.jmwh.module.register.dialog.RangeItemPop;
import com.qsd.jmwh.module.register.dialog.SelectInfoPop;
import com.qsd.jmwh.module.register.presenter.EditUserInfoPresenter;
import com.qsd.jmwh.module.register.presenter.EditUserInfoViewer;
import com.qsd.jmwh.view.NormaFormItemVIew;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

public class EditUserDataActivity extends BaseBarActivity
        implements EditUserInfoViewer, View.OnClickListener {
    @PresenterLifeCycle
    EditUserInfoPresenter mPresenter = new EditUserInfoPresenter(this);

    private static final String TOKEN = "token";
    private static final String USER_ID = "user_id";

    private TextView headerHint;
    private ImageView selectHeader;
    private String userHeaderUrl;
    private NormaFormItemVIew location;
    private NormaFormItemVIew professional;
    private NormaFormItemVIew project;
    private NormaFormItemVIew height;
    private NormaFormItemVIew weight;
    private NormaFormItemVIew age;

    public static Intent getIntent(Context context, String token, int lUserId) {
        Intent starter = new Intent(context, EditUserDataActivity.class);
        starter.putExtra(TOKEN, token);
        starter.putExtra(USER_ID, lUserId);
        return starter;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.edit_user_data_activity);
    }

    @Override
    protected void loadData() {
        setTitle("完善资料");
        initView();
        initListener();
    }

    private void initView() {
        headerHint = bindView(R.id.select_hint);
        selectHeader = bindView(R.id.header);
        location = bindView(R.id.location);
        professional = bindView(R.id.professional);
        project = bindView(R.id.project);
        height = bindView(R.id.height);
        weight = bindView(R.id.weight);
        age = bindView(R.id.age);
    }

    private void initListener() {
        selectHeader.setOnClickListener(this);
        bindView(R.id.login, this);
        bindView(R.id.agreement, this);
        initItemClickListener();
    }

    private void initItemClickListener() {
        location.setOnClickSelectedItem(v -> {
            getLaunchHelper().startActivity(
                    DateRangeActivity.getIntent(getActivity(), 1, getIntent().getStringExtra(TOKEN),
                            getIntent().getIntExtra(USER_ID, -1), "约会范围"));
        });
        professional.setOnClickSelectedItem(v -> {
            getLaunchHelper().startActivity(
                    DateRangeActivity.getIntent(getActivity(), 0, getIntent().getStringExtra(TOKEN),
                            getIntent().getIntExtra(USER_ID, -1), "职业"));
        });
        project.setOnClickSelectedItem(v -> {
            mPresenter.getDateProject();
        });
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header:
                RxGalleryFinalApi.getInstance(EditUserDataActivity.this)
                        .openGalleryRadioImgDefault(new RxBusResultDisposable<ImageRadioResultEvent>() {
                            @Override
                            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent)
                                    throws Exception {
                                if (!TextUtils.isEmpty(imageRadioResultEvent.getResult().getThumbnailSmallPath())) {
                                    mPresenter.setHeader(imageRadioResultEvent.getResult().getThumbnailSmallPath());
                                }
                            }
                        });
                break;
            case R.id.login:
                EditUserInfo editUserInfo = new EditUserInfo();
                mPresenter.editUserInfo(editUserInfo);
                break;
            case R.id.agreement:
                ToastUtils.show("用户协议");
                break;
            default:
        }
    }

    @Override
    public void setUserHeaderSuccess(String url) {
        userHeaderUrl = url;
        headerHint.setVisibility(View.GONE);
        selectHeader.setVisibility(View.VISIBLE);
        ImageLoader.loadCenterCrop(EditUserDataActivity.this, url, selectHeader, R.mipmap.ic_launcher);
    }

    @Override
    public void showDateProjectList(List<String> list) {
        RangeItemPop rangeItemPop = new RangeItemPop(getActivity());
        rangeItemPop.setData(list).setData(list).setOnSelectedProjectListener(selected -> {

        }).showPopupWindow();
    }

    @Override
    public void commitUserInfo() {
        getLaunchHelper().startActivity(HomeActivity.class);
        finish();
    }
}
