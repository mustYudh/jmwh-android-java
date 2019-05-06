package com.qsd.jmwh.module.home.park;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseFragment;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.activity.LookUserInfoActivity;
import com.qsd.jmwh.module.home.park.adapter.PersonRvAdapter;
import com.qsd.jmwh.module.home.park.bean.HomePersonListBean;
import com.qsd.jmwh.module.home.park.presenter.PersonPresenter;
import com.qsd.jmwh.module.home.park.presenter.PersonViewer;
import com.yu.common.mvp.PresenterLifeCycle;

public class PersonFragment extends BaseFragment implements PersonViewer {
    @PresenterLifeCycle
    PersonPresenter mPresenter = new PersonPresenter(this);
    private RecyclerView rv_person;
    private PersonRvAdapter adapter;
    private TextView tv_top_num;

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
            String home_type = bundle.getString("HOMETYPE");
            initView(home_type);
        }


    }

    private void initView(String home_type) {
        rv_person = bindView(R.id.rv_person);
        tv_top_num = bindView(R.id.tv_top_num);
        rv_person.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPresenter.initPersonListData(UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), home_type, "", "0", "0");
    }

    @Override
    public void getDataSuccess(HomePersonListBean homePersonListBean) {
        if (homePersonListBean != null) {
            if (homePersonListBean.cdoList != null && homePersonListBean.cdoList.size() != 0) {
                if (adapter == null) {
                    adapter = new PersonRvAdapter(R.layout.item_person, homePersonListBean.cdoList, getActivity());
                    rv_person.setAdapter(adapter);
                } else {
                    adapter.setNewData(homePersonListBean.cdoList);
                }
                adapter.setOnItemClickListener((adapter, view, position) -> {
                   HomePersonListBean.CdoListBean cdoListBean = (HomePersonListBean.CdoListBean) adapter.getData().get(position);
                   getLaunchHelper().startActivity(LookUserInfoActivity.getIntent(getActivity(),cdoListBean.lUserId));
                });
            } else {
                //空页面
            }
            if (homePersonListBean.nNotExistGalaryCount != 0){
                tv_top_num.setVisibility(View.VISIBLE);
                tv_top_num.setText("已经隐藏" + homePersonListBean.nNotExistGalaryCount + "位没有照片的女士");
            }else {
                tv_top_num.setVisibility(View.GONE);
            }

        } else {
            //错误界面
            tv_top_num.setVisibility(View.GONE);
        }
    }
}
