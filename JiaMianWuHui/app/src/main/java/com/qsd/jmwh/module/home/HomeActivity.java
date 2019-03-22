package com.qsd.jmwh.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.denghao.control.TabItem;
import com.denghao.control.TabView;
import com.denghao.control.view.BottomNavigationView;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.message.MessageFragment;
import com.qsd.jmwh.module.home.park.ParkFragment;
import com.qsd.jmwh.module.home.radio.RadioFragment;
import com.qsd.jmwh.module.home.user.UserFragment;
import com.test.myapplication.fragment.IntegratedRadioFragment;
import com.yu.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.home_activity);
    }

    @Override
    protected void loadData() {
        BottomNavigationView navigationView =  findViewById(R.id.bottom_navigation_view);
        List<TabItem> items = new ArrayList<>();
        items.add(new TabView(createTabView(), new ParkFragment()));
        items.add(new TabView(createTabView(), new MessageFragment()));
        items.add(new TabView(createTabView(), new IntegratedRadioFragment()));
        items.add(new TabView(createTabView(), new RadioFragment()));
        items.add(new TabView(createTabView(), new UserFragment()));
        navigationView.initControl(this).setPagerView(items, 0);
        navigationView.getNavgation().setTabControlHeight(60);
        navigationView.getControl().setOnTabClickListener((position, view) -> {

        });

    }

    public View createTabView() {
        TextView textView = new TextView(getActivity());
        textView.setText("12314");
        return textView;
    }
}
