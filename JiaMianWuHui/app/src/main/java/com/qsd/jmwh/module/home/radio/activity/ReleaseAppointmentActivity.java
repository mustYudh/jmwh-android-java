package com.qsd.jmwh.module.home.radio.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.radio.adapter.RadioLoveRvAdapter;
import com.qsd.jmwh.module.home.radio.bean.DataRefreshRadioDataEvent;
import com.qsd.jmwh.module.home.radio.bean.GetDatingUserVipBean;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.qsd.jmwh.module.home.radio.presenter.ReleaseAppointmentPresenter;
import com.qsd.jmwh.module.home.radio.presenter.ReleaseAppointmentViewer;
import com.qsd.jmwh.module.register.DateRangeActivity;
import com.qsd.jmwh.module.register.bean.PayInfo;
import com.qsd.jmwh.module.register.bean.RangeData;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.qsd.jmwh.utils.DateUtils;
import com.qsd.jmwh.utils.DialogUtils;
import com.qsd.jmwh.view.CircleImageView;
import com.qsd.jmwh.view.NoSlidingGridView;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.SwitchView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

import static com.qsd.jmwh.module.register.EditUserDataActivity.DATE_RANGE_REQUEST_CODE;

public class ReleaseAppointmentActivity extends BaseBarActivity implements View.OnClickListener, ReleaseAppointmentViewer {

    private NoSlidingGridView gv_photo;
    private List<String> allLocationSelectedPicture = new ArrayList<>();
    private GridAdapter adapter;
    private DialogUtils loveDialog, timeDialog, dataTime;
    private TextView tv_city;
    private TextView tv_time;
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    ReleaseAppointmentPresenter mPresenter = new ReleaseAppointmentPresenter(this);
    private RadioLoveRvAdapter radioLoveRvAdapter;
    private List<GetRadioConfigListBean.CdoListBean> cdoList;
    private TextView tv_qiwang;
    private String sDatingHope = "";
    private String sDatingRange = "";
    private String sDatingTime = "";
    private String sDatingTimeExt = "";

    private TextView tv_date;
    private int bCommentType = 1;
    private int bHiddenType = 0;
    private String activity_title;
    private EditText et_buchong;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_release_appointment);
    }

    @Override
    protected void loadData() {
        activity_title = getIntent().getStringExtra("activity_title");
        setTitle(activity_title);
        LinearLayout ll_qiwang = bindView(R.id.ll_qiwang);
        LinearLayout ll_city = bindView(R.id.ll_city);
        LinearLayout ll_time = bindView(R.id.ll_time);
        LinearLayout ll_date = bindView(R.id.ll_date);
        TextView tv_sure = bindView(R.id.tv_sure);
        tv_date = bindView(R.id.tv_date);
        tv_qiwang = bindView(R.id.tv_qiwang);
        tv_city = bindView(R.id.tv_city);
        tv_time = bindView(R.id.tv_time);
        et_buchong = bindView(R.id.et_buchong);
        SwitchView sw_one = bindView(R.id.sw_one);
        SwitchView sw_two = bindView(R.id.sw_two);


        gv_photo = bindView(R.id.gv_photo);
        adapter = new GridAdapter();
        gv_photo.setAdapter(adapter);

        //期望
        ll_qiwang.setOnClickListener(this);
        //城市
        ll_city.setOnClickListener(this);
        //时间
        ll_time.setOnClickListener(this);
        //日期
        ll_date.setOnClickListener(this);
        //发布
        tv_sure.setOnClickListener(this);
        //是否禁止评论
        sw_one.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true);
                bCommentType = 1;
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false);
                bCommentType = 0;
            }
        });
        //是否对同性屏蔽
        sw_two.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true);
                bHiddenType = 1;
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false);
                bHiddenType = 0;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_qiwang:
                mPresenter.initRadioConfigData("1");
                break;
            case R.id.ll_city:
                getLaunchHelper().startActivityForResult(DateRangeActivity.getIntent(getActivity(), 1, UserProfile.getInstance().getAppToken(), UserProfile.getInstance().getAppAccount(), "约会范围"), DATE_RANGE_REQUEST_CODE);
                break;
            case R.id.ll_time:
                showTimeDialog();
                break;
            case R.id.ll_date:
                showDataDialog();
                break;
            case R.id.tv_sure:
                if (TextUtils.isEmpty(sDatingRange)) {
                    ToastUtils.show("请选择约会城市");
                    return;
                }
                if (TextUtils.isEmpty(sDatingTime)) {
                    ToastUtils.show("请选择日期");
                    return;
                }
                if (TextUtils.isEmpty(sDatingTimeExt)) {
                    ToastUtils.show("请选择时间");
                    return;
                }
                if (allLocationSelectedPicture.size() != 0) {
                    StringBuffer url = new StringBuffer();
                    for (int i = 0; i < allLocationSelectedPicture.size(); i++) {
                        PersistenceResponse response = UploadImage.uploadImage(getActivity(), UserProfile.getInstance().getObjectName(), allLocationSelectedPicture.get(i));
                        url.append(response.cloudUrl);
                        if (i < allLocationSelectedPicture.size() - 1) {
                            url.append(",");
                        }
                    }
                    mPresenter.addDatingData(activity_title, sDatingRange, sDatingTime, sDatingTimeExt, et_buchong.getText().toString(), UserProfile.getInstance().getSex() + "", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), bCommentType + "", bHiddenType + "", url.toString(), sDatingHope);

                } else {
                    mPresenter.addDatingData(activity_title, sDatingRange, sDatingTime, sDatingTimeExt, et_buchong.getText().toString(), UserProfile.getInstance().getSex() + "", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), bCommentType + "", bHiddenType + "", "", sDatingHope);

                }

                break;
        }
    }


    @Override
    public void addDatingSuccess() {
        ToastUtils.show("发布成功");
        EventBus.getDefault().post(new DataRefreshRadioDataEvent());
        finish();
    }

    @Override
    public void getConfigDataSuccess(GetRadioConfigListBean configListBean) {
        if (configListBean != null && configListBean.cdoList != null && configListBean.cdoList.size() != 0) {
            cdoList = configListBean.cdoList;
            showLoveDialog(configListBean.cdoList);
        }
    }


    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (allLocationSelectedPicture.size() == 3) {
                return allLocationSelectedPicture.size();
            }
            return allLocationSelectedPicture.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.gv_item, null);
                holder = new ViewHolder();
                holder.ivDemo = (ImageView) convertView.findViewById(R.id.iv_picture_item);
                holder.ivDel = (CircleImageView) convertView.findViewById(R.id.iv_del);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == allLocationSelectedPicture.size()) {
                if (position == 3) {
                    holder.ivDemo.setVisibility(View.GONE);
                }

                holder.ivDemo.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pic_add));
                holder.ivDel.setVisibility(View.GONE);
                holder.ivDemo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPhoto();
                    }
                });
            } else {
                Glide.with(getActivity()).load(allLocationSelectedPicture.get(position) + "").into(holder.ivDemo);
                holder.ivDel.setVisibility(View.VISIBLE);
            }
            holder.ivDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    allLocationSelectedPicture.remove(position);
                    if (adapter != null) {
                        gv_photo.setAdapter(adapter);
                    }
                }
            });
            return convertView;
        }
    }


    public class ViewHolder {
        public ImageView ivDemo;
        public CircleImageView ivDel;
    }


    private void addPhoto() {
        RxGalleryFinalApi instance = RxGalleryFinalApi.getInstance(getActivity());
        instance.openSingGalleryRadioImgDefault(
                new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                        String imgUrl = imageRadioResultEvent.getResult().getOriginalPath();
                        if (!TextUtils.isEmpty(imgUrl)) {
                            allLocationSelectedPicture.add(imgUrl);
                            if (adapter != null) {
                                gv_photo.setAdapter(adapter);
                            }
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getBundleExtra(DateRangeActivity.RANGE_RESULT);
            RangeData.Range range = (RangeData.Range) bundle.getSerializable(DateRangeActivity.RANGE_RESULT);
            if (range != null) {
                if (requestCode == DATE_RANGE_REQUEST_CODE) {
                    sDatingRange = range.sName;
                    tv_city.setText(range.sName);
                    tv_city.setTextColor(getResources().getColor(R.color.app_main_bg_color));
                }
            }
        }
    }


    /**
     * 选择日期弹窗
     */
    private void showDataDialog() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataTime.isShowing()) {
                    dataTime.dismiss();
                }
                switch (v.getId()) {
                    case R.id.cancle:

                        break;
                    case R.id.down:
                        tv_date.setText(sDatingTime);
                        tv_date.setTextColor(getResources().getColor(R.color.app_main_bg_color));
                        break;
                }
            }
        };

        dataTime = new DialogUtils.Builder(getActivity())
                .view(R.layout.dialog_data_layout)
                .setLeftButton("取消", R.id.cancle)
                .setRightButton("确定", R.id.down)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.cancle, listener)
                .addViewOnclick(R.id.down, listener)
                .build();
        dataTime.show();

        CalendarView caledar_view = dataTime.findViewById(R.id.caledar_view);
        String strTime = DateUtils.getStrTime(caledar_view.getDate() + "");
        sDatingTime = strTime;
        caledar_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                sDatingTime = year + "-" + (month + 1) + "-" + dayOfMonth;
            }
        });

    }


    /**
     * 约会期望弹窗
     */
    private void showLoveDialog(List<GetRadioConfigListBean.CdoListBean> cdoList) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loveDialog.isShowing()) {
                    loveDialog.dismiss();
                }
                switch (v.getId()) {
                    case R.id.cancle:

                        break;
                    case R.id.down:
                        list.clear();
                        if (radioLoveRvAdapter != null) {
                            if (radioLoveRvAdapter.isSelected != null && radioLoveRvAdapter.isSelected.size() != 0) {
                                for (int i = 0; i < radioLoveRvAdapter.isSelected.size(); i++) {
                                    if (radioLoveRvAdapter.isSelected.get(i)) {
                                        list.add(cdoList.get(i).sDatingConfigName);
                                    }
                                }
                                StringBuffer sb = new StringBuffer();
                                for (int i = 0; i < list.size(); i++) {
                                    sb.append(list.get(i));
                                    if (i < list.size() - 1) {
                                        sb.append("/");
                                    }
                                }
                                sDatingHope = sb.toString();
                                tv_qiwang.setText(sb.toString());
                                tv_qiwang.setTextColor(getResources().getColor(R.color.app_main_bg_color));
                            } else {
                                ToastUtils.show("请选择约会期望");
                                return;
                            }
                        }
                        break;
                }
            }
        };

        loveDialog = new DialogUtils.Builder(getActivity())
                .view(R.layout.dialog_love_layout)
                .setLeftButton("取消", R.id.cancle)
                .setRightButton("确定", R.id.down)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.cancle, listener)
                .addViewOnclick(R.id.down, listener)
                .build();
        loveDialog.show();

        RecyclerView rv_love = loveDialog.findViewById(R.id.rv_love);
        rv_love.setLayoutManager(new LinearLayoutManager(getActivity()));
        radioLoveRvAdapter = new RadioLoveRvAdapter(R.layout.item_radio_love, cdoList, getActivity());
        rv_love.setAdapter(radioLoveRvAdapter);
    }

    /**
     * 时间弹窗
     */
    private void showTimeDialog() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeDialog.isShowing()) {
                    timeDialog.dismiss();
                }
                tv_time.setTextColor(getResources().getColor(R.color.app_main_bg_color));
                switch (v.getId()) {
                    case R.id.ll_1:
                        sDatingTimeExt = "上午";
                        tv_time.setText("上午");
                        break;
                    case R.id.ll_2:
                        sDatingTimeExt = "中午";
                        tv_time.setText("中午");
                        break;
                    case R.id.ll_3:
                        sDatingTimeExt = "下午";
                        tv_time.setText("下午");
                        break;
                    case R.id.ll_4:
                        sDatingTimeExt = "晚上";
                        tv_time.setText("晚上");
                        break;
                    case R.id.ll_5:
                        sDatingTimeExt = "一整天";
                        tv_time.setText("一整天");
                        break;
                }
            }
        };

        timeDialog = new DialogUtils.Builder(getActivity())
                .view(R.layout.dialog_layout_time)
                .gravity(Gravity.BOTTOM)
                .style(R.style.Dialog)
                .cancelTouchout(false)
                .addViewOnclick(R.id.ll_1, listener)
                .addViewOnclick(R.id.ll_2, listener)
                .addViewOnclick(R.id.ll_3, listener)
                .addViewOnclick(R.id.ll_4, listener)
                .addViewOnclick(R.id.ll_5, listener)
                .build();
        timeDialog.show();
    }
}
