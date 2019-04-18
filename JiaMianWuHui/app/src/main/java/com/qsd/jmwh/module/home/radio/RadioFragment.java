package com.qsd.jmwh.module.home.radio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarFragment;
import com.qsd.jmwh.module.home.radio.adapter.HomeRadioRvAdapter;
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

    private List<String> list = new ArrayList<>();
    private List<LocalHomeRadioListBean> dataList = new ArrayList<>();
    @PresenterLifeCycle
    RadioPresenter mPresenter = new RadioPresenter(this);
    private RecyclerView rv_radio;

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
        list.add("夜蒲聚会 / 不限时间，上海市");
        list.add("大叔勿扰");

//        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555493287325&di=98f49975e296e6f5c11981c0c56097dc&imgtype=0&src=http%3A%2F%2Fi1.bbs.fd.zol-img.com.cn%2Ft_s800x5000%2Fg5%2FM00%2F0B%2F0F%2FChMkJln10OuIHZh8AACJEEEqIzAAAhqsgBp1mIAAIko407.jpg");
//        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555493287324&di=fef30213ca19443872acb22e26d26c8a&imgtype=0&src=http%3A%2F%2Fimg2.ph.126.net%2F2zB3_wWPXlEW0RdwQa8d6A%3D%3D%2F2268688312388037455.jpg");
//        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555493287324&di=1629a936b3378aafdc65adf49c811730&imgtype=0&src=http%3A%2F%2Fs14.sinaimg.cn%2Fmw690%2F001R5mFczy77zmp12q15d%26690");
//        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555493287324&di=b5645b9945daadcdb356e63ed3815fc1&imgtype=0&src=http%3A%2F%2Fs1.sinaimg.cn%2Fmw690%2F006fmRRJzy745Jb6KxG00%26690");
    }

    private void initView() {
        TextView tv_left = bindView(R.id.tv_left);
        TextView tv_right = bindView(R.id.tv_right);
        rv_radio = bindView(R.id.rv_radio);
        rv_radio.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPresenter.initRadioData("30.17722", "120.2007", "", "1", "0", "");

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
            }
        }

    }
}
