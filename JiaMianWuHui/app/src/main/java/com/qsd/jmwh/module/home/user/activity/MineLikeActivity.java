package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.adapter.MineLikeRvAdapter;
import com.qsd.jmwh.module.home.user.bean.MineLikeBean;
import com.qsd.jmwh.module.home.user.presenter.MineLikePresenter;
import com.qsd.jmwh.module.home.user.presenter.MineLikeViewer;
import com.yu.common.mvp.PresenterLifeCycle;

public class MineLikeActivity extends BaseBarActivity implements MineLikeViewer {

    private RecyclerView rv_like;
    private MineLikeRvAdapter adapter;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_like);
    }

    @PresenterLifeCycle
    private MineLikePresenter mPresenter = new MineLikePresenter(this);

    @Override
    protected void loadData() {
        setTitle("我喜欢的");

        rv_like = bindView(R.id.rv_like);
        rv_like.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPresenter.getMineLikeData(UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "0");

    }

    @Override
    public void getMineLikeList(MineLikeBean mineLikeBean) {
        if (mineLikeBean != null) {
            if (mineLikeBean.cdoList != null && mineLikeBean.cdoList.size() != 0) {
                if (adapter == null) {
                    adapter = new MineLikeRvAdapter(R.layout.item_person, mineLikeBean.cdoList, getActivity());
                    rv_like.setAdapter(adapter);
                } else {
                    adapter.setNewData(mineLikeBean.cdoList);
                }
            } else {
                //空界面
            }
        }
    }
}
