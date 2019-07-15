package com.qsd.jmwh.module.home.park;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseFragment;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.activity.LookUserInfoActivity;
import com.qsd.jmwh.module.home.park.adapter.PersonRvAdapter;
import com.qsd.jmwh.module.home.park.bean.HomePersonListBean;
import com.qsd.jmwh.module.home.park.presenter.PersonPresenter;
import com.qsd.jmwh.module.home.park.presenter.PersonViewer;
import com.qsd.jmwh.utils.DialogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickImageView;
import java.util.ArrayList;
import java.util.List;

public class PersonFragment extends BaseFragment implements PersonViewer {
    @PresenterLifeCycle
    PersonPresenter mPresenter = new PersonPresenter(this);
    private RecyclerView rv_person;
    private PersonRvAdapter adapter;
    private DialogUtils loveDialog;
    private List<HomePersonListBean.CdoListBean> list = new ArrayList<>();
    private SmartRefreshLayout refresh;
    private LinearLayout ll_empty;
    public int pageIndex = 0;
    public String home_type;

    @Override
    protected int getContentViewId() {
        return R.layout.person_fragment_layout;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    public static PersonFragment newInstance(String homeType) {
        PersonFragment personFragment = new PersonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("HOMETYPE", homeType);
        personFragment.setArguments(bundle);
        return personFragment;
    }

    @Override
    protected void loadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            home_type = bundle.getString("HOMETYPE");
            initView(home_type);
        }


    }

    private void initView(String home_type) {
        ll_empty = bindView(R.id.ll_empty);
        refresh = bindView(R.id.refresh);
        rv_person = bindView(R.id.rv_person);
        rv_person.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPresenter.initPersonListData(UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), home_type, "", pageIndex + "", UserProfile.getInstance().getHomeSexType() + "", UserProfile.getInstance().getHomeCityName());


        refresh.setOnRefreshListener(refreshLayout -> {
            pageIndex = 0;
            mPresenter.initPersonListData(UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), home_type, "", pageIndex + "", UserProfile.getInstance().getHomeSexType() + "", UserProfile.getInstance().getHomeCityName());

        });
        refresh.setOnLoadMoreListener(refreshLayout -> {
            pageIndex++;
            mPresenter.initPersonListData(UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), home_type, "", pageIndex + "", UserProfile.getInstance().getHomeSexType() + "", UserProfile.getInstance().getHomeCityName());

        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getDataSuccess(HomePersonListBean homePersonListBean) {
        if (homePersonListBean != null) {
            refresh.finishLoadMore();
            refresh.finishRefresh();
            if (homePersonListBean.cdoList != null && homePersonListBean.cdoList.size() != 0) {
                if (pageIndex > 0) {

                } else {
                    list.clear();
                }

                list.addAll(homePersonListBean.cdoList);

//                if (adapter == null) {
//                    adapter = new PersonRvAdapter(R.layout.item_person, list, getActivity(),sex+"");
//                    rv_person.setAdapter(adapter);
//                } else {
//                    adapter.setNewData(list);
//                }
                adapter = new PersonRvAdapter(R.layout.item_person, list, getActivity(), UserProfile.getInstance().getHomeSexType() + "");
                rv_person.setAdapter(adapter);

                adapter.setOnItemClickListener((adapter, view, position) -> {
                    HomePersonListBean.CdoListBean cdoListBean = (HomePersonListBean.CdoListBean) adapter.getData().get(position);
                    getLaunchHelper().startActivity(LookUserInfoActivity.getIntent(getActivity(), cdoListBean.lUserId, cdoListBean.lUserId, 2));
                });

                adapter.setOnPersonItemClickListener(new PersonRvAdapter.OnPersonItemClickListener() {
                    @Override
                    public void setOnPersonItemClick(DelayClickImageView iv_love, int position, boolean is_love, String lLoveUserId) {
                        if (is_love) {
                            showLoveDialog(lLoveUserId, iv_love, position);
                        } else {
                            mPresenter.initAddLoveUser(lLoveUserId, "0", position, iv_love);
                        }
                    }
                });
                ll_empty.setVisibility(View.GONE);
                refresh.setVisibility(View.VISIBLE);
            } else {
                //空页面
                if (pageIndex > 0) {
                    ToastUtils.show("没有更多了");
                } else {
                    ll_empty.setVisibility(View.VISIBLE);
                    refresh.setVisibility(View.GONE);
                }
            }
            if ("0".equals(home_type)) {
                if (homePersonListBean.nNotExistGalaryCount != 0) {
                    if (UserProfile.getInstance().getSex() == 0) {
                    } else {
                        //tv_top_num.setVisibility(View.VISIBLE);
                    }
                    //tv_top_num.setText("已经隐藏" + homePersonListBean.nNotExistGalaryCount + "位没有照片的" + (UserProfile.getInstance().getHomeSexType() == 0 ? "女士" : "男士"));
                } else {
                }
            } else {
            }


        } else {
            //错误界面
        }
    }

    //收藏成功
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
