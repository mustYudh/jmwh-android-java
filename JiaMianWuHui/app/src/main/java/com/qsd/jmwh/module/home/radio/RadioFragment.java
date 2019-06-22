package com.qsd.jmwh.module.home.radio;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarFragment;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.activity.LookUserInfoActivity;
import com.qsd.jmwh.module.home.radio.activity.ReleaseAppointmentActivity;
import com.qsd.jmwh.module.home.radio.adapter.DateRadioListDialogAdapter;
import com.qsd.jmwh.module.home.radio.adapter.HomeRadioRvAdapter;
import com.qsd.jmwh.module.home.radio.bean.DataRefreshRadioDataEvent;
import com.qsd.jmwh.module.home.radio.bean.GetDatingUserVipBean;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.qsd.jmwh.module.home.radio.bean.HomeRadioListBean;
import com.qsd.jmwh.module.home.radio.bean.LocalHomeRadioListBean;
import com.qsd.jmwh.module.home.radio.presenter.RadioPresenter;
import com.qsd.jmwh.module.home.radio.presenter.RadioViewer;
import com.qsd.jmwh.module.home.user.activity.AuthCenterActivity;
import com.qsd.jmwh.module.register.DateRangeActivity;
import com.qsd.jmwh.module.register.ToByVipActivity;
import com.qsd.jmwh.module.register.bean.PayInfo;
import com.qsd.jmwh.module.register.bean.RangeData;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.qsd.jmwh.utils.DialogUtils;
import com.qsd.jmwh.utils.PayUtils;
import com.qsd.jmwh.view.CircleImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;
import com.yu.common.utils.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

import static com.qsd.jmwh.module.register.EditUserDataActivity.DATE_RANGE_REQUEST_CODE;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class RadioFragment extends BaseBarFragment implements RadioViewer {
    private List<LocalHomeRadioListBean> dataList = new ArrayList<>();
    @PresenterLifeCycle
    RadioPresenter mPresenter = new RadioPresenter(this);
    private RecyclerView rv_radio;
    private LinearLayout ll_empty;
    private DialogUtils typeDialog, enrollDialog, releaseDialog, upVipDialog, payDialog;
    private int sexType = 0;
    private int disType = 0;
    private DelayClickTextView right_menu;
    private DelayClickTextView left_menu;
    private TextView tv_left;
    private HomeRadioRvAdapter adapter;
    private Dialog commentDialog;
    private String select_city = "";
    private SmartRefreshLayout refresh;
    public int pageIndex = 0;

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.rang_bar_layout;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.radio_fragment;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected void loadData() {
        mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", pageIndex + "");
    }

    private void initView() {
        refresh = bindView(R.id.refresh);
        tv_left = bindView(R.id.tv_left);
        TextView tv_right = bindView(R.id.tv_right);
        right_menu = bindView(R.id.right_menu);
        left_menu = bindView(R.id.left_menu);
        rv_radio = bindView(R.id.rv_radio);
        ll_empty = bindView(R.id.ll_empty);
        rv_radio.setLayoutManager(new LinearLayoutManager(getActivity()));
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLaunchHelper().startActivityForResult(DateRangeActivity.getIntent(getActivity(), 1, UserProfile.getInstance().getAppToken(), UserProfile.getInstance().getAppAccount(), "约会范围"), DATE_RANGE_REQUEST_CODE);
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.initRadioConfigData("0");
            }
        });

        left_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTypeDialog(1);
            }
        });

        right_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTypeDialog(2);
            }
        });


        refresh.setOnRefreshListener(refreshLayout -> {
            pageIndex = 0;
            switch (sexType) {
                case 0:
                    mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), select_city + "", (disType == 0 ? 1 : 2) + "", pageIndex + "");
                    break;
                case 1:
                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), select_city + "", (disType == 0 ? 1 : 2) + "", pageIndex + "", "0");
                    break;
                case 2:
                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), select_city + "", (disType == 0 ? 1 : 2) + "", pageIndex + "", "1");
                    break;
            }
        });
        refresh.setOnLoadMoreListener(refreshLayout -> {
            pageIndex++;
            switch (sexType) {
                case 0:
                    mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), select_city + "", (disType == 0 ? 1 : 2) + "", pageIndex + "");
                    break;
                case 1:
                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), select_city + "", (disType == 0 ? 1 : 2) + "", pageIndex + "", "0");
                    break;
                case 2:
                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), select_city + "", (disType == 0 ? 1 : 2) + "", pageIndex + "", "1");
                    break;
            }
        });
    }


    @Override
    public void getDataSuccess(HomeRadioListBean homeRadioListBean) {
        refresh.finishRefresh();
        refresh.finishLoadMore();
        if (homeRadioListBean != null) {
            if (homeRadioListBean.cdoList != null && homeRadioListBean.cdoList.size() != 0) {
                if (pageIndex > 0) {

                } else {
                    dataList.clear();
                }
                for (int i = 0; i < homeRadioListBean.cdoList.size(); i++) {
                    HomeRadioListBean.CdoListBean cdoListBean = homeRadioListBean.cdoList.get(i);
                    LocalHomeRadioListBean localHomeRadioListTitleBean = new LocalHomeRadioListBean();
                    if (cdoListBean.cdoUserData != null) {
                        localHomeRadioListTitleBean.headImg = cdoListBean.cdoUserData.sUserHeadPic;
                        localHomeRadioListTitleBean.userName = cdoListBean.cdoUserData.sNickName;
                        localHomeRadioListTitleBean.lUserId = cdoListBean.cdoUserData.lUserId;
                    }
                    localHomeRadioListTitleBean.sDatingRange = cdoListBean.sDatingRange;
                    localHomeRadioListTitleBean.sDatingTime = cdoListBean.sDatingTime;
                    localHomeRadioListTitleBean.sContent = cdoListBean.sContent;
                    localHomeRadioListTitleBean.sDatingTitle = cdoListBean.sDatingTitle;
                    localHomeRadioListTitleBean.sDatingHope = cdoListBean.sDatingHope;
                    localHomeRadioListTitleBean.sex = cdoListBean.nSex;
                    localHomeRadioListTitleBean.cTime = cdoListBean.dCreateTime;
                    localHomeRadioListTitleBean.itemType = 0;
                    dataList.add(localHomeRadioListTitleBean);

                    LocalHomeRadioListBean localHomeRadioListPicBean = new LocalHomeRadioListBean();
                    localHomeRadioListPicBean.picList = cdoListBean.sImg;
                    localHomeRadioListPicBean.itemType = 1;
                    dataList.add(localHomeRadioListPicBean);

                    LocalHomeRadioListBean localHomeRadioListBottomBean = new LocalHomeRadioListBean();
                    localHomeRadioListBottomBean.is_like = cdoListBean.bLove;
                    localHomeRadioListBottomBean.like_count = cdoListBean.nLikeCount;
                    localHomeRadioListBottomBean.count_num = cdoListBean.nCommentCount;
                    localHomeRadioListBottomBean.is_apply = cdoListBean.bApply;
                    localHomeRadioListBottomBean.lDatingId = cdoListBean.lDatingId;
                    localHomeRadioListBottomBean.lUserId = cdoListBean.lUserId;
                    localHomeRadioListBottomBean.nApplyCount = cdoListBean.nApplyCount;
                    localHomeRadioListBottomBean.bCommentType = cdoListBean.bCommentType;
                    localHomeRadioListBottomBean.sex = cdoListBean.nSex;
                    if (cdoListBean.cdoUserData != null) {
                        localHomeRadioListTitleBean.bVIP = cdoListBean.cdoUserData.bVIP;
                    }
                    localHomeRadioListBottomBean.itemType = 2;
                    dataList.add(localHomeRadioListBottomBean);
                }

                if (adapter == null) {
                    adapter = new HomeRadioRvAdapter(dataList, getActivity());
                    rv_radio.setAdapter(adapter);
                } else {
                    adapter.setNewData(dataList);
                }
                adapter.setOnPersonItemClickListener(new HomeRadioRvAdapter.OnRadioItemClickListener() {
                    @Override
                    public void setOnRadioItemClick(DelayClickImageView iv_like, DelayClickTextView tv_like, int position, int is_like, String lDatingId, String lJoinerId, String lInitiatorId) {
                        mPresenter.addDatingLikeCount(lDatingId, lJoinerId, lInitiatorId, iv_like, tv_like, position, is_like);
                    }

                    @Override
                    public void setOnPersonInfoItemClick(int lLoveUserId) {
                        getLaunchHelper().startActivity(LookUserInfoActivity.getIntent(getActivity(), lLoveUserId,lLoveUserId,2));
                    }

                    @Override
                    public void setOnAddContentItemClick(LocalHomeRadioListBean item) {
                        mPresenter.getDatingUserVIP(0, item);
                    }

                    @Override
                    public void setOnAddDatingEnrollItemClick(LocalHomeRadioListBean item) {
                        mPresenter.getDatingUserVIP(1, item);
                    }
                });
                ll_empty.setVisibility(View.GONE);
            } else {
                if (pageIndex > 0) {
                    ToastUtils.show("没有更多了");
                } else {
                    ll_empty.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    @Override
    public void getConfigDataSuccess(GetRadioConfigListBean configListBean) {
        if (configListBean != null && configListBean.cdoList != null && configListBean.cdoList.size() != 0) {
            showReleaseDialog(configListBean);
        }
    }

    @Override
    public void addDatingLikeCountSuccess(DelayClickImageView iv_like, DelayClickTextView tv_like, int position, int is_like) {
        if (is_like == 1) {
            dataList.get(position).is_like = 0;
            dataList.get(position).like_count = dataList.get(position).like_count - 1;
            tv_like.setText("赞(" + dataList.get(position).like_count + ")");
            iv_like.setImageResource(R.drawable.zan);
            ToastUtils.show("取消点赞成功");
        } else {
            dataList.get(position).is_like = 1;
            dataList.get(position).like_count = dataList.get(position).like_count + 1;
            tv_like.setText("赞(" + dataList.get(position).like_count + ")");
            iv_like.setImageResource(R.drawable.zan_select);
            ToastUtils.show("点赞成功");
        }
    }

    @Override
    public void getDatingUserVIPSuccess(int type, GetDatingUserVipBean getDatingUserVipBean, LocalHomeRadioListBean item) {
        if (getDatingUserVipBean != null) {
            if (type == 0) {
                //评论
                if (item.sex == 1 && !getDatingUserVipBean.bVIP) {
                    //非会员,男士
                    ToastUtils.show("非会员不能评论");
                    return;
                }
                showCommentDialog(item);
            } else {
                //报名
                if (item.sex == 1 && !getDatingUserVipBean.bVIP) {
                    //非会员,男士
                    ToastUtils.show("非会员不能报名");
                    return;
                }
                showEnrollDialog(item);
            }

        }
    }

    @Override
    public void addDatingCommentCountSuccess(LocalHomeRadioListBean item) {
        item.count_num = item.count_num + 1;
        adapter.notifyDataSetChanged();
        ToastUtils.show("评论成功");
    }

    @Override
    public void addDatingEnrollSuccess(LocalHomeRadioListBean item) {
        item.is_apply = 1;
        item.nApplyCount = item.nApplyCount + 1;
        adapter.notifyDataSetChanged();
        ToastUtils.show("报名成功,如果对方觉得合适将会联系您!");
    }

    @Override
    public void getDatingUserVIPPaySuccess(GetDatingUserVipBean getDatingUserVipBean, String name) {
        if (getDatingUserVipBean != null) {
            if (getDatingUserVipBean.nSex == 0) {
                if (getDatingUserVipBean.nAuthType == 3 || getDatingUserVipBean.nAuthType == 4) {
                    getContext().startActivity(new Intent(getContext(), ReleaseAppointmentActivity.class).putExtra("activity_title", name));
                } else {
                    //为认证
                    getLaunchHelper().startActivity(AuthCenterActivity.class);
                }
            } else {
                if (getDatingUserVipBean.bVIP) {
                    getContext().startActivity(new Intent(getContext(), ReleaseAppointmentActivity.class).putExtra("activity_title", name));
                } else {
                    //非会员
                    showUpVipDialog(getDatingUserVipBean, name);
                }
            }
        }
    }

    @Override
    public void getBuyDatingPaySignSuccess(PayInfo payInfo, String name) {
        PayUtils.getInstance().pay(getActivity(), payType, payInfo)
                .getPayResult(new PayUtils.PayCallBack() {
                    @Override
                    public void onPaySuccess(int type) {
                        getContext().startActivity(new Intent(getContext(), ReleaseAppointmentActivity.class).putExtra("activity_title", name));
                    }

                    @Override
                    public void onFailed(int type) {
                        ToastUtils.show("支付失败，请重试");
                    }
                });
        ;
    }

    /**
     * 评论弹窗
     */
    private void showCommentDialog(LocalHomeRadioListBean item) {
        commentDialog = new Dialog(getActivity(), R.style.DialogComment);
        View view = View.inflate(getActivity(), R.layout.dialog_comment_layout, null);
        commentDialog.setContentView(view);
        WindowManager.LayoutParams attributes = commentDialog.getWindow().getAttributes();
        attributes.width = ViewPager.LayoutParams.MATCH_PARENT;
        attributes.height = ViewGroup.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.CENTER;
        commentDialog.setCanceledOnTouchOutside(true);
        commentDialog.getWindow().setAttributes(attributes);
        commentDialog.show();

        final EditText et_talk = commentDialog.findViewById(R.id.et_talk);
        TextView tv_send = commentDialog.findViewById(R.id.tv_send);

        CircleImageView iv_headimg = commentDialog.findViewById(R.id.iv_headimg_other);
        ImageLoader.loadCenterCrop(getActivity(), UserProfile.getInstance().getUserPic(), iv_headimg, R.drawable.zhanwei);

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_talk.getText().toString().trim())) {
                    ToastUtils.show("内容不能为空");
                } else {
                    if (commentDialog.isShowing()) {
                        commentDialog.dismiss();
                    }
                    mPresenter.addDatingCommentCount(item.lDatingId + "", UserProfile.getInstance().getAppAccount() + "", item.lUserId + "", et_talk.getText().toString().trim(), item);
                }
            }
        });

        LinearLayout ll_root = commentDialog.findViewById(R.id.ll_root);

        ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputMgr = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);

                if (commentDialog.isShowing()) {
                    commentDialog.dismiss();
                }
            }
        });
    }


    /**
     * 弹窗
     */
    private void showTypeDialog(int type) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageIndex = 0;
                switch (v.getId()) {
                    case R.id.ll_top:
                        if (typeDialog.isShowing()) {
                            typeDialog.dismiss();
                        }
                        if (type == 1) {
                            disType = 0;
                            left_menu.setText("最新发布");
                            switch (sexType) {
                                case 0:
                                    mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", pageIndex + "");
                                    break;
                                case 1:
                                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", pageIndex + "", "0");
                                    break;
                                case 2:
                                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", pageIndex + "", "1");
                                    break;
                            }
                        } else {
                            sexType = 0;
                            right_menu.setText("全部");
                            switch (disType) {
                                case 0:
                                    mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", pageIndex + "");
                                    break;
                                case 1:
                                    mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "2", pageIndex + "");
                                    break;
                            }

                        }
                        break;
                    case R.id.ll_bottom:
                        if (typeDialog.isShowing()) {
                            typeDialog.dismiss();
                        }
                        disType = 1;
                        if (type == 1) {
                            switch (sexType) {
                                case 0:
                                    mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "2", "0");
                                    break;
                                case 1:
                                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "2", "0", "0");
                                    break;
                                case 2:
                                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "2", "0", "1");
                                    break;
                            }
                            left_menu.setText("最近约会");
                        } else {
                            sexType = 1;
                            right_menu.setText("女士");
                            switch (disType) {
                                case 0:
                                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", "0", "0");
                                    break;
                                case 1:
                                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "2", "0", "0");
                                    break;
                            }

                        }

                        break;

                    case R.id.ll_other:
                        if (typeDialog.isShowing()) {
                            typeDialog.dismiss();
                        }
                        sexType = 2;
                        right_menu.setText("男士");
                        switch (disType) {
                            case 0:
                                mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", "0", "1");
                                break;
                            case 1:
                                mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "2", "0", "1");
                                break;
                        }

                        break;
                }
            }
        };

        typeDialog = new DialogUtils.Builder(getActivity())
                .view(R.layout.dialog_radio_type)
                .settext(type == 1 ? "选择排序条件" : "选择过滤条件", R.id.tv_title)
                .setLeftButton(type == 1 ? "最新发布" : "全部", R.id.tv_top)
                .setRightButton(type == 1 ? "最近约会" : "女士", R.id.tv_bottom)
                .gravity(Gravity.BOTTOM)
                .style(R.style.Dialog)
                .cancelTouchout(true)
                .addViewOnclick(R.id.ll_bottom, listener)
                .addViewOnclick(R.id.ll_top, listener)
                .addViewOnclick(R.id.ll_other, listener)
                .build();
        typeDialog.show();
        if (type == 1) {
            typeDialog.findViewById(R.id.ll_other).setVisibility(View.GONE);
        } else {
            typeDialog.findViewById(R.id.ll_other).setVisibility(View.VISIBLE);
        }
    }


    /**
     * 报名弹窗
     */
    private void showEnrollDialog(LocalHomeRadioListBean item) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enrollDialog.isShowing()) {
                    enrollDialog.dismiss();
                }
                switch (v.getId()) {
                    case R.id.ll_top:
                        addPhoto(item);
                        break;
                    case R.id.ll_bottom:

                        break;

                }
            }
        };

        enrollDialog = new DialogUtils.Builder(getActivity())
                .view(R.layout.dialog_radio_enroll)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(true)
                .addViewOnclick(R.id.ll_bottom, listener)
                .addViewOnclick(R.id.ll_top, listener)
                .build();
        enrollDialog.show();

    }


    /**
     * 发布约会弹窗
     */
    private void showReleaseDialog(GetRadioConfigListBean configListBean) {
        releaseDialog = new DialogUtils.Builder(getActivity())
                .view(R.layout.radio_item_layout)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(true)
                .settext("发布约会广播", R.id.title)
                .build();
        releaseDialog.show();


        RecyclerView recyclerView = releaseDialog.findViewById(R.id.list_data);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        DateRadioListDialogAdapter adapter = new DateRadioListDialogAdapter(R.layout.item_date_radio_layout, configListBean.cdoList, getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (releaseDialog.isShowing()) {
                releaseDialog.dismiss();
            }
            mPresenter.getDatingUserPayVIP(configListBean.cdoList.get(position).sDatingConfigName);
        });

    }


    /**
     * 提醒升级弹窗
     */
    private void showUpVipDialog(GetDatingUserVipBean getDatingUserVipBean, String name) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upVipDialog.isShowing()) {
                    upVipDialog.dismiss();
                }
                switch (v.getId()) {
                    case R.id.tv_vip:
                        LauncherHelper.from(getActivity()).startActivity(ToByVipActivity
                                .getIntent(getActivity(), UserProfile.getInstance().getAppAccount(),
                                        UserProfile.getInstance().getAppToken()));
                        break;
                    case R.id.tv_pay:
                        payType = 0;
                        showPayDialog(getDatingUserVipBean, name);
                        break;
                    case R.id.tv_cancle:
                        break;
                }
            }
        };

        upVipDialog = new DialogUtils.Builder(getActivity())
                .view(R.layout.radio_up_vip_layout)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(true)
                .settext("会员免费,非会员需支付" + getDatingUserVipBean.cdoList.get(0).nGoodsRealFee + "元", R.id.title)
                .addViewOnclick(R.id.tv_vip, listener)
                .addViewOnclick(R.id.tv_pay, listener)
                .addViewOnclick(R.id.tv_cancle, listener)
                .build();
        upVipDialog.show();


    }

    ImageView iv1;
    ImageView iv2;
    ImageView iv3;
    private int payType = 0;

    /**
     * 选择付款弹窗
     */
    private void showPayDialog(GetDatingUserVipBean getDatingUserVipBean, String name) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.rl_alipay:
                        iv1.setImageResource(R.drawable.ic_button_selected);
                        iv2.setImageResource(R.drawable.ic_button_unselected);
                        iv3.setImageResource(R.drawable.ic_button_unselected);
                        payType = 1;
                        break;
                    case R.id.rl_wechat:
                        iv1.setImageResource(R.drawable.ic_button_unselected);
                        iv2.setImageResource(R.drawable.ic_button_selected);
                        iv3.setImageResource(R.drawable.ic_button_unselected);
                        payType = 2;
                        break;
                    case R.id.rl_wallet:
                        iv1.setImageResource(R.drawable.ic_button_unselected);
                        iv2.setImageResource(R.drawable.ic_button_unselected);
                        iv3.setImageResource(R.drawable.ic_button_selected);
                        payType = 3;
                        break;
                    case R.id.tv_pay:
                        if (payType == 0) {
                            ToastUtils.show("请选择支付方式");
                            return;
                        }
                        if (payDialog.isShowing()) {
                            payDialog.dismiss();
                        }
                        mPresenter.getBuyDatingPaySign(getDatingUserVipBean.cdoList.get(0).lGoodsId + "", payType + "", name);
                        break;
                    case R.id.iv_close:
                        if (payDialog.isShowing()) {
                            payDialog.dismiss();
                        }
                        break;
                }
            }
        };

        payDialog = new DialogUtils.Builder(getActivity())
                .view(R.layout.radio_pay_layout)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(true)
                .settext(getDatingUserVipBean.cdoList.get(0).nGoodsRealFee + "", R.id.tv_money)
                .addViewOnclick(R.id.rl_wallet, listener)
                .addViewOnclick(R.id.rl_alipay, listener)
                .addViewOnclick(R.id.rl_wechat, listener)
                .addViewOnclick(R.id.iv_close, listener)
                .addViewOnclick(R.id.tv_pay, listener)
                .build();
        payDialog.show();

        iv1 = payDialog.findViewById(R.id.iv1);
        iv2 = payDialog.findViewById(R.id.iv2);
        iv3 = payDialog.findViewById(R.id.iv3);
    }

    /**
     * 选择图片
     */
    private void addPhoto(LocalHomeRadioListBean item) {
        RxGalleryFinalApi instance = RxGalleryFinalApi.getInstance(getActivity());
        instance.openSingGalleryRadioImgDefault(
                new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                        String imgUrl = imageRadioResultEvent.getResult().getOriginalPath();
                        if (!TextUtils.isEmpty(imgUrl)) {
                            PersistenceResponse response = UploadImage.uploadImage(getActivity(), UserProfile.getInstance().getObjectName(), imgUrl);

                            mPresenter.addDatingEnroll(item.lDatingId + "", UserProfile.getInstance().getAppAccount() + "", item.lUserId + "", response.cloudUrl, item);
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
                    pageIndex = 0;
                    tv_left.setText(range.sName);
                    select_city = range.sName;
                    switch (sexType) {
                        case 0:
                            mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), range.sName + "", (disType == 0 ? 1 : 2) + "", pageIndex + "");
                            break;
                        case 1:
                            mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), range.sName + "", (disType == 0 ? 1 : 2) + "", pageIndex + "", "0");
                            break;
                        case 2:
                            mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), range.sName + "", (disType == 0 ? 1 : 2) + "", pageIndex + "", "1");
                            break;
                    }

                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //刷新数据
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEvent(DataRefreshRadioDataEvent event) {
        pageIndex = 0;
        switch (sexType) {
            case 0:
                mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), select_city + "", (disType == 0 ? 1 : 2) + "", pageIndex + "");
                break;
            case 1:
                mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), select_city + "", (disType == 0 ? 1 : 2) + "", pageIndex + "", "0");
                break;
            case 2:
                mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), select_city + "", (disType == 0 ? 1 : 2) + "", pageIndex + "", "1");
                break;
        }
    }
}
