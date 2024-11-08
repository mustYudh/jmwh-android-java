package com.qsd.jmwh.module.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.user.activity.WebViewActivity;
import com.qsd.jmwh.module.register.bean.RangeData;
import com.qsd.jmwh.module.register.bean.SelectData;
import com.qsd.jmwh.module.register.bean.UploadUserInfoParams;
import com.qsd.jmwh.module.register.dialog.RangeItemPop;
import com.qsd.jmwh.module.register.dialog.SelectInfoPop;
import com.qsd.jmwh.module.register.presenter.EditUserInfoPresenter;
import com.qsd.jmwh.module.register.presenter.EditUserInfoViewer;
import com.qsd.jmwh.view.NormaFormItemVIew;
import com.qsd.jmwh.view.UserItemView;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;

public class EditUserDataActivity extends BaseBarActivity
        implements EditUserInfoViewer, View.OnClickListener {
    @PresenterLifeCycle
    EditUserInfoPresenter mPresenter = new EditUserInfoPresenter(this);


    private static final String TOKEN = "token";
    private static final String USER_ID = "user_id";
    private static final String SEX = "sex";
    public static final int DATE_RANGE_REQUEST_CODE = 0X123;
    public static final int PROJECT_REQUEST_CODE = 0X124;
    private List<String> ranges = new ArrayList<>();

    private TextView headerHint;
    private ImageView selectHeader;
    private NormaFormItemVIew location;
    private NormaFormItemVIew professional;
    private NormaFormItemVIew project;
    private NormaFormItemVIew height;
    private NormaFormItemVIew weight;
    private NormaFormItemVIew age;
    private NormaFormItemVIew sNickName;
    private NormaFormItemVIew bust;
    private NormaFormItemVIew wechat;
    private NormaFormItemVIew qq;
    private UserItemView switchSocial;
    private String headerUrl;
    private String bugsSize = "";
    private String selectPhotoUrl;

    public static Intent getIntent(Context context, String token, int lUserId, int sex) {
        Intent starter = new Intent(context, EditUserDataActivity.class);
        starter.putExtra(TOKEN, token);
        starter.putExtra(USER_ID, lUserId);
        starter.putExtra(SEX, sex);
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
        bust = bindView(R.id.bust);
        wechat = bindView(R.id.we_chat);
        qq = bindView(R.id.qq);
        sNickName = bindView(R.id.sNickName);
        switchSocial = bindView(R.id.switch_social);
        boolean isGirl = getIntent().getIntExtra(SEX, -1) == 0;
        bindView(R.id.bust, isGirl);
        bindView(R.id.social, isGirl);
    }

    private void initListener() {
        selectHeader.setOnClickListener(this);
        bindView(R.id.login, this);
        bindView(R.id.agreement, this);
        initItemClickListener();
    }

    private void initItemClickListener() {
        location.setOnClickSelectedItem(v ->
                getLaunchHelper().startActivityForResult(
                        DateRangeActivity.getIntent(getActivity(), 1, getIntent().getStringExtra(TOKEN),
                                getIntent().getIntExtra(USER_ID, -1), "约会范围"), DATE_RANGE_REQUEST_CODE));
        professional.setOnClickSelectedItem(v ->
                getLaunchHelper().startActivityForResult(
                        DateRangeActivity.getIntent(getActivity(), 0, getIntent().getStringExtra(TOKEN),
                                getIntent().getIntExtra(USER_ID, -1), "职业"), PROJECT_REQUEST_CODE));
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
        bust.setOnClickSelectedItem(v -> {
            List<SelectData> datas = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                SelectData data = new SelectData();
                data.key = 30 + i;
                data.value = (30 + i) + "";
                datas.add(data);
            }
            String[] NO = {"A", "B", "C", "D", "E", "F", "G"};
            List<SelectData> bustSize = new ArrayList<>();
            for (String s : NO) {
                SelectData data = new SelectData();
                data.value = s;
                bustSize.add(data);
            }
            SelectInfoPop infoPop = new SelectInfoPop(getActivity());
            infoPop.setTitle("胸围大小").setData(datas).setoNDataSelectedListener(selectData -> {
                bugsSize = "";
                bugsSize += selectData.value;
                SelectInfoPop bustPop = new SelectInfoPop(getActivity());
                bustPop.setTitle("胸围类型").setData(bustSize).setoNDataSelectedListener(data -> {
                            bugsSize += data.value;
                            bust.setContentText(bugsSize);
                        }
                ).showPopupWindow();
            }).showPopupWindow();
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
                            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                                selectPhotoUrl = imageRadioResultEvent.getResult().getThumbnailSmallPath();
                            }
                        }).onCropImageResult(new IRadioImageCheckedListener() {
                    @Override
                    public void cropAfter(Object t) {
                        if (!TextUtils.isEmpty(selectPhotoUrl)) {
                            mPresenter.setHeader(selectPhotoUrl,
                                    +getIntent().getIntExtra(USER_ID, -1) + "/head_" + UUID.randomUUID().toString() + ".jpg",
                                    getIntent().getIntExtra(USER_ID, -1) + "", getIntent().getStringExtra(TOKEN));
                        }
                    }

                    @Override
                    public boolean isActivityFinish() {
                        return true;
                    }
                });
                ;
                break;
            case R.id.login:
                UploadUserInfoParams params = new UploadUserInfoParams();
                params.sNickName = sNickName.getText();
                params.sDateRange = location.getText();
                params.sAge = age.getText();
                params.sJob = professional.getText();
                params.sDatePro = project.getText();
                params.sHeight = height.getText();
                params.sWeight = weight.getText();
                params.QQ = qq.getText();
                params.WX = wechat.getText();
                params.sBust = bust.getText();
                params.bHiddenQQandWX = switchSocial.getSwitched();
                params.sUserHeadPic = headerUrl;
                params.lUserId = getIntent().getIntExtra(USER_ID, -1) + "";
                EditText edit = bindView(R.id.edit_sIntroduce);
                params.sIntroduce = edit.getText().toString().trim();
                params.token = getIntent().getStringExtra(TOKEN);
                mPresenter.uploadUserInfo(params);

                break;
            case R.id.agreement:
                getLaunchHelper().startActivity(WebViewActivity.class);
                break;
            default:
        }
    }

    @Override
    public void setUserHeaderSuccess(String url) {
        headerHint.setVisibility(View.GONE);
        selectHeader.setVisibility(View.VISIBLE);
        headerUrl = url;
        ImageLoader.loadHader(EditUserDataActivity.this, url, selectHeader);
    }

    @Override
    public void showDateProjectList(List<String> list) {
        RangeItemPop rangeItemPop = new RangeItemPop(getActivity());
        rangeItemPop.setData(list).setData(list).setOnSelectedProjectListener(selected -> {
            if (selected.size() > 4) {
                ToastUtils.show("最多只能选择4个");
                rangeItemPop.dismiss();
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
        mPresenter.getCode(getIntent().getIntExtra(USER_ID, -1), getIntent().getStringExtra(TOKEN), getIntent().getIntExtra(SEX, -1));
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
