package com.qsd.jmwh.module.home.park;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class PersonFragment extends BaseFragment {
    private List<String> list = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.person_fragment_layout;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        initView();
    }

    private void initView() {
        RecyclerView rv_person = bindView(R.id.rv_person);
        rv_person.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}
