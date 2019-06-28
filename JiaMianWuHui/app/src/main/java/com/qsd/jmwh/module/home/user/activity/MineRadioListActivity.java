package com.qsd.jmwh.module.home.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.park.activity.LookUserInfoActivity;
import com.qsd.jmwh.module.home.radio.adapter.MineRadioRvAdapter;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.qsd.jmwh.module.home.radio.bean.LocalHomeRadioListBean;
import com.qsd.jmwh.module.home.radio.dialog.RadioItemPop;
import com.qsd.jmwh.module.home.user.bean.MineRadioListBean;
import com.qsd.jmwh.module.home.user.bean.UserCenterInfo;
import com.qsd.jmwh.module.home.user.presenter.MineRadioPresenter;
import com.qsd.jmwh.module.home.user.presenter.MineRadioViewer;
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
        mPresenter.getMyInfo();
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
                    public void setOnRadioItemClick(DelayClickImageView iv_like, DelayClickTextView tv_like, int position, int is_like, String lDatingId, String lJoinerId, String lInitiatorId) {
                        ToastUtils.show("你不能赞自己");
                    }

                    @Override
                    public void setOnPersonInfoItemClick(int lLoveUserId) {
                        getLaunchHelper().startActivity(LookUserInfoActivity.getIntent(getActivity(), lLoveUserId,lLoveUserId,2));
                    }

                    @Override
                    public void setOnAddContentItemClick(LocalHomeRadioListBean item) {
                        ToastUtils.show("你不能评论自己");
                    }

                    @Override
                    public void setOnAddDatingEnrollItemClick(LocalHomeRadioListBean item) {
                        String json = new Gson().toJson(item);
                        startActivity(new Intent(MineRadioListActivity.this, ApplyDetailsActivity.class).putExtra("json", json));
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
            RadioItemPop infoPop = new RadioItemPop(getActivity());
            infoPop.setOutsideTouchable(true);
            infoPop.setTitle("发布约会广播").setData(configListBean.cdoList).showPopupWindow();
        }
    }

    @Override public void getUserInfo(UserCenterInfo userCenterMyInfo) {
        setRightMenu("我要广播", v -> mPresenter.initRadioConfigData("0"));

//        if (userCenterMyInfo.cdoUser.bVIP) {
//            setRightMenu("我要广播", v -> mPresenter.initRadioConfigData("0"));
//        } else {
//            getLaunchHelper().startActivity(
//                ToByVipActivity.getIntent(getActivity(), UserProfile.getInstance().getUserId(),
//                    UserProfile.getInstance().getAppToken()));
//        }
    }
}
