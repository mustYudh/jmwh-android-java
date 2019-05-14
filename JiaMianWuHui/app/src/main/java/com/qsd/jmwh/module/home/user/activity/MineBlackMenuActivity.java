package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.activity.LookUserInfoActivity;
import com.qsd.jmwh.module.home.park.adapter.MineBlackMenuRvAdapter;
import com.qsd.jmwh.module.home.user.bean.MineLikeBean;
import com.qsd.jmwh.module.home.user.presenter.MineLikePresenter;
import com.qsd.jmwh.module.home.user.presenter.MineLikeViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;

import java.util.ArrayList;
import java.util.List;

public class MineBlackMenuActivity extends BaseBarActivity implements MineLikeViewer {

    private RecyclerView rv_list;
    private MineBlackMenuRvAdapter adapter;
    private List<MineLikeBean.CdoListBean> list = new ArrayList<>();

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_black_menu);
    }

    @PresenterLifeCycle
    private MineLikePresenter mPresenter = new MineLikePresenter(this);

    @Override
    protected void loadData() {
        setTitle("黑名单");
        rv_list = bindView(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPresenter.getMineLikeData(UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "0");
    }

    @Override
    public void getMineLikeList(MineLikeBean mineLikeBean) {
        if (mineLikeBean != null) {
            if (mineLikeBean.cdoList != null && mineLikeBean.cdoList.size() != 0) {
                list = mineLikeBean.cdoList;
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).is_black = true;
                }
                if (adapter == null) {
                    adapter = new MineBlackMenuRvAdapter(R.layout.item_mine_black_menu, list, getActivity());
                    rv_list.setAdapter(adapter);
                } else {
                    adapter.setNewData(list);
                }
                adapter.setOnPersonItemClickListener(new MineBlackMenuRvAdapter.OnMineLikeItemClickListener() {
                    @Override
                    public void setOnMineLikeItemClick(DelayClickTextView tv_black, int position, boolean is_black, String lLoveUserId) {
                        mPresenter.initAddBlackUser(lLoveUserId, "1", is_black, position, tv_black);
                    }

                    @Override
                    public void setOnPersonInfoItemClick(int lLoveUserId) {
                        getLaunchHelper().startActivity(LookUserInfoActivity.getIntent(getActivity(), lLoveUserId));
                    }
                });
            } else {
                //空界面
            }
        }

    }

    @Override
    public void addLoveUserSuccess(boolean is_love, int position, DelayClickImageView iv_love) {

    }

    @Override
    public void addBlackUserSuccess(boolean is_black, int position, DelayClickTextView tv_black) {
        if (is_black) {
            list.get(position).is_black = false;
            tv_black.setText("对她屏蔽");
            ToastUtils.show("取消黑名单成功");
        } else {
            list.get(position).is_black = true;
            tv_black.setText("取消屏蔽");
            ToastUtils.show("加入黑名单成功");
        }
    }
}
