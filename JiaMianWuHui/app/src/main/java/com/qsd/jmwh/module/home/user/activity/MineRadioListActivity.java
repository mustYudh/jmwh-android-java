package com.qsd.jmwh.module.home.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.activity.LookUserInfoActivity;
import com.qsd.jmwh.module.home.radio.activity.ReleaseAppointmentActivity;
import com.qsd.jmwh.module.home.radio.adapter.DateRadioListDialogAdapter;
import com.qsd.jmwh.module.home.radio.adapter.MineRadioRvAdapter;
import com.qsd.jmwh.module.home.radio.bean.GetDatingUserVipBean;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.qsd.jmwh.module.home.radio.bean.LocalHomeRadioListBean;
import com.qsd.jmwh.module.home.user.bean.MineRadioListBean;
import com.qsd.jmwh.module.home.user.presenter.MineRadioPresenter;
import com.qsd.jmwh.module.home.user.presenter.MineRadioViewer;
import com.qsd.jmwh.module.register.ToByVipActivity;
import com.qsd.jmwh.module.register.bean.PayInfo;
import com.qsd.jmwh.utils.DialogUtils;
import com.qsd.jmwh.utils.PayUtils;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;

import java.util.ArrayList;
import java.util.List;

public class MineRadioListActivity extends BaseBarActivity implements MineRadioViewer {

    private RecyclerView rv_radio;
    private LinearLayout ll_empty;
    private List<LocalHomeRadioListBean> dataList = new ArrayList<>();
    private MineRadioRvAdapter adapter;
    private DialogUtils releaseDialog, upVipDialog, payDialog;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_radio_list);
    }

    @PresenterLifeCycle
    private MineRadioPresenter mPresenter = new MineRadioPresenter(this);

    @Override
    protected void loadData() {
        setTitle("我的广播");
        rv_radio = bindView(R.id.rv_radio);
        ll_empty = bindView(R.id.ll_empty);
        rv_radio.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.getDatingByUserIdData("0");
        setRightMenu("我要广播", v -> mPresenter.initRadioConfigData("0"));
    }


    @Override
    public void getMineRadioList(MineRadioListBean mineRadioListBean) {
        if (mineRadioListBean != null) {
            if (mineRadioListBean.cdoList != null && mineRadioListBean.cdoList.size() != 0) {
                dataList.clear();
                for (int i = 0; i < mineRadioListBean.cdoList.size(); i++) {
                    MineRadioListBean.CdoListBean cdoListBean = mineRadioListBean.cdoList.get(i);
                    LocalHomeRadioListBean localHomeRadioListTitleBean = new LocalHomeRadioListBean();
                    if (cdoListBean.cdoUserData != null) {
                        localHomeRadioListTitleBean.headImg = cdoListBean.cdoUserData.sUserHeadPic;
                        localHomeRadioListTitleBean.userName = cdoListBean.cdoUserData.sNickName;
                        localHomeRadioListTitleBean.lUserId = cdoListBean.cdoUserData.lUserId;
                    }
                    localHomeRadioListTitleBean.sDatingRange = cdoListBean.sDatingRange;
                    localHomeRadioListTitleBean.sDatingTime = cdoListBean.sDatingTime;
                    localHomeRadioListTitleBean.sContent = cdoListBean.sContent;
                    localHomeRadioListTitleBean.sDatingHope = cdoListBean.sDatingHope;
                    localHomeRadioListTitleBean.sDatingTitle = cdoListBean.sDatingTitle;
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

                    localHomeRadioListBottomBean.cdoComment = cdoListBean.cdoComment;
                    localHomeRadioListBottomBean.cdoApply = cdoListBean.cdoApply;
                    localHomeRadioListBottomBean.nStatus = cdoListBean.nStatus;

                    if (cdoListBean.cdoUserData != null) {
                        localHomeRadioListBottomBean.headImg = cdoListBean.cdoUserData.sUserHeadPic;
                        localHomeRadioListBottomBean.userName = cdoListBean.cdoUserData.sNickName;
                        localHomeRadioListBottomBean.lUserId = cdoListBean.cdoUserData.lUserId;
                        localHomeRadioListBottomBean.bVIP = cdoListBean.cdoUserData.bVIP;
                    }
                    localHomeRadioListBottomBean.picList = cdoListBean.sImg;
                    localHomeRadioListBottomBean.sDatingRange = cdoListBean.sDatingRange;
                    localHomeRadioListBottomBean.sDatingTime = cdoListBean.sDatingTime;
                    localHomeRadioListBottomBean.sContent = cdoListBean.sContent;
                    localHomeRadioListBottomBean.sDatingTitle = cdoListBean.sDatingTitle;
                    localHomeRadioListBottomBean.cTime = cdoListBean.dCreateTime;
                    localHomeRadioListBottomBean.cdoLove = cdoListBean.cdoLove;
                    localHomeRadioListBottomBean.itemType = 2;
                    dataList.add(localHomeRadioListBottomBean);
                }

                if (adapter == null) {
                    adapter = new MineRadioRvAdapter(dataList, getActivity());
                    rv_radio.setAdapter(adapter);
                } else {
                    adapter.setNewData(dataList);
                }

                adapter.setOnPersonItemClickListener(new MineRadioRvAdapter.OnRadioItemClickListener() {
                    @Override
                    public void setOnRadioItemClick(DelayClickImageView iv_like, DelayClickTextView tv_like,
                                                    int position, int is_like, String lDatingId, String lJoinerId, String lInitiatorId) {
                        ToastUtils.show("你不能赞自己");
                    }

                    @Override
                    public void setOnPersonInfoItemClick(int lLoveUserId) {
                        getLaunchHelper().startActivity(
                                LookUserInfoActivity.getIntent(getActivity(), lLoveUserId, lLoveUserId, 2));
                    }

                    @Override
                    public void setOnAddContentItemClick(LocalHomeRadioListBean item) {
                        ToastUtils.show("你不能评论自己");
                    }

                    @Override
                    public void setOnAddDatingEnrollItemClick(LocalHomeRadioListBean item) {
                        String json = new Gson().toJson(item);
                        startActivity(
                                new Intent(MineRadioListActivity.this, ApplyDetailsActivity.class).putExtra("json",
                                        json));
                    }

                    @Override
                    public void setOnCloseEnrollItemClick(LocalHomeRadioListBean item) {
                        mPresenter.modifyStatus("1", item.lDatingId + "", item);
                    }
                });
                rv_radio.setVisibility(View.VISIBLE);
                ll_empty.setVisibility(View.GONE);
            } else {
                //空界面
                rv_radio.setVisibility(View.GONE);
                ll_empty.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void getModifyStatus(LocalHomeRadioListBean item) {
        ToastUtils.show("取消成功");
        item.nStatus = 1;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getConfigDataSuccess(GetRadioConfigListBean configListBean) {
        if (configListBean != null && configListBean.cdoList != null && configListBean.cdoList.size() != 0) {
            showReleaseDialog(configListBean);
        }
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
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        DateRadioListDialogAdapter adapter = new DateRadioListDialogAdapter(R.layout.item_date_radio_layout, configListBean.cdoList, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (releaseDialog.isShowing()) {
                releaseDialog.dismiss();
            }
            mPresenter.getDatingUserPayVIP(configListBean.cdoList.get(position).sDatingConfigName);
        });

    }

    @Override
    public void getDatingUserVIPPaySuccess(GetDatingUserVipBean getDatingUserVipBean, String name) {
        if (getDatingUserVipBean != null) {
            if (getDatingUserVipBean.nSex == 0) {
                if (getDatingUserVipBean.nAuthType != 0) {
                    startActivity(new Intent(getActivity(), ReleaseAppointmentActivity.class).putExtra("activity_title", name));
                } else {
                    //为认证
                    getLaunchHelper().startActivity(AuthCenterActivity.class);
                }
            } else {
                if (getDatingUserVipBean.bVIP) {
                    startActivity(new Intent(getActivity(), ReleaseAppointmentActivity.class).putExtra("activity_title", name));
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
                        startActivity(new Intent(getActivity(), ReleaseAppointmentActivity.class).putExtra("activity_title", name));
                    }

                    @Override
                    public void onFailed(int type) {
                        ToastUtils.show("支付失败，请重试");
                    }
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
                                .getIntent(getActivity(), UserProfile.getInstance().getUserId(),
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
                .settext("会员免费,非会员需支付" + getDatingUserVipBean.cdoList.get(0).nGoodsSaleFee + "假面币", R.id.title)
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
                        payType = 5;
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
                .settext(getDatingUserVipBean.cdoList.get(0).nGoodsSaleFee + "", R.id.tv_money)
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
}
