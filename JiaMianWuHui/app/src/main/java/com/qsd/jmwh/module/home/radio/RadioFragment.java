package com.qsd.jmwh.module.home.radio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarFragment;
import com.qsd.jmwh.module.home.radio.adapter.HomeRadioRvAdapter;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.qsd.jmwh.module.home.radio.bean.HomeRadioListBean;
import com.qsd.jmwh.module.home.radio.bean.LocalHomeRadioListBean;
import com.qsd.jmwh.module.home.radio.presenter.RadioPresenter;
import com.qsd.jmwh.module.home.radio.presenter.RadioViewer;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;

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
        mPresenter.initRadioData("30.17722", "120.2007", "", "1", "0", "");

    }

    private void initView() {
        TextView tv_left = bindView(R.id.tv_left);
        TextView tv_right = bindView(R.id.tv_right);
        rv_radio = bindView(R.id.rv_radio);
        ll_empty = bindView(R.id.ll_empty);
        rv_radio.setLayoutManager(new LinearLayoutManager(getActivity()));

        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.initRadioConfigData("0");
            }
        });
    }


    @Override
    public void getDataSuccess(HomeRadioListBean homeRadioListBean) {
        if (homeRadioListBean != null) {
            if (homeRadioListBean.cdoList != null && homeRadioListBean.cdoList.size() != 0) {
                for (int i = 0; i < homeRadioListBean.cdoList.size(); i++) {
                    HomeRadioListBean.CdoListBean cdoListBean = homeRadioListBean.cdoList.get(i);
                    LocalHomeRadioListBean localHomeRadioListTitleBean = new LocalHomeRadioListBean();
                    if (cdoListBean.cdoUserData != null) {
                        localHomeRadioListTitleBean.headImg = cdoListBean.cdoUserData.sUserHeadPic;
                        localHomeRadioListTitleBean.userName = cdoListBean.cdoUserData.sNickName;
                    }
                    localHomeRadioListTitleBean.sDatingRange = cdoListBean.sDatingRange;
                    localHomeRadioListTitleBean.sDatingTime = cdoListBean.sDatingTime;
                    localHomeRadioListTitleBean.sContent = cdoListBean.sContent;
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
                    localHomeRadioListBottomBean.is_like = 1;
                    localHomeRadioListBottomBean.like_count = cdoListBean.nLikeCount;
                    localHomeRadioListBottomBean.count_num = cdoListBean.nCommentCount;
                    localHomeRadioListBottomBean.is_apply = 0;
                    localHomeRadioListBottomBean.itemType = 2;
                    dataList.add(localHomeRadioListBottomBean);
                }

                HomeRadioRvAdapter adapter = new HomeRadioRvAdapter(dataList, getActivity());
                rv_radio.setAdapter(adapter);
                ll_empty.setVisibility(View.GONE);
            } else {
                ll_empty.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void getConfigDataSuccess(GetRadioConfigListBean configListBean) {
        if (configListBean != null && configListBean.cdoList != null && configListBean.cdoList.size() != 0){
            
        }
    }
}
