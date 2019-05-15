package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.activity.LookUserInfoActivity;
import com.qsd.jmwh.module.home.park.adapter.MineLikeRvAdapter;
import com.qsd.jmwh.module.home.user.bean.MineLikeBean;
import com.qsd.jmwh.module.home.user.presenter.MineLikePresenter;
import com.qsd.jmwh.module.home.user.presenter.MineLikeViewer;
import com.qsd.jmwh.utils.DialogUtils;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;

import java.util.ArrayList;
import java.util.List;

public class MineLikeActivity extends BaseBarActivity implements MineLikeViewer {

    private RecyclerView rv_like;
    private MineLikeRvAdapter adapter;
    private DialogUtils loveDialog;
    private List<MineLikeBean.CdoListBean> list = new ArrayList<>();

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
                list = mineLikeBean.cdoList;
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).blove = true;
                }
                if (adapter == null) {
                    adapter = new MineLikeRvAdapter(R.layout.item_person, list, getActivity());
                    rv_like.setAdapter(adapter);
                } else {
                    adapter.setNewData(list);
                }

                adapter.setOnPersonItemClickListener(new MineLikeRvAdapter.OnMineLikeItemClickListener() {
                    @Override
                    public void setOnMineLikeItemClick(DelayClickImageView iv_love, int position, boolean is_love, String lLoveUserId) {
                        if (is_love) {
                            showLoveDialog(lLoveUserId, iv_love, position);
                        } else {
                            mPresenter.initAddLoveUser(lLoveUserId, "0", position, iv_love);
                        }
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
    public void addLoveUserSuccess(int position, DelayClickImageView iv_love) {
        list.get(position).blove = true;
        iv_love.setImageResource(R.drawable.collect_select);
        ToastUtils.show("收藏成功");
    }

    @Override
    public void delLoveUserSuccess(int position, DelayClickImageView iv_love) {
        list.get(position).blove = false;
        iv_love.setImageResource(R.drawable.collect);
        ToastUtils.show("取消收藏成功");
    }

    @Override
    public void addBlackUserSuccess(int position, DelayClickTextView tv_black) {

    }

    @Override
    public void delBlackUserSuccess(int position, DelayClickTextView tv_black) {

    }


    /**
     * 取消收藏弹窗
     */
    private void showLoveDialog(String lLoveUserId, DelayClickImageView iv_love, int position) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.cancle:
                        if (loveDialog.isShowing()) {
                            loveDialog.dismiss();
                        }
                        break;
                    case R.id.down:
                        if (loveDialog.isShowing()) {
                            loveDialog.dismiss();
                        }
                        mPresenter.initdelLoveUser(lLoveUserId, "0", position, iv_love);
                        break;
                }
            }
        };

        loveDialog = new DialogUtils.Builder(getActivity())
                .view(R.layout.dialog_layout)
                .settext("确定要取消收藏吗？", R.id.dialog_content)
                .setLeftButton("取消", R.id.cancle)
                .setRightButton("确定", R.id.down)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.cancle, listener)
                .addViewOnclick(R.id.down, listener)
                .build();
        loveDialog.show();
    }
}
