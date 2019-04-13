package com.qsd.jmwh.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.denghao.control.TabItem;
import com.denghao.control.TabView;
import com.denghao.control.view.BottomNavigationView;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.integrated.IntegratedRadioFragment;
import com.qsd.jmwh.module.home.message.MessageFragment;
import com.qsd.jmwh.module.home.park.ParkFragment;
import com.qsd.jmwh.module.home.user.UserFragment;
import com.yu.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private BottomNavigationView navigationView;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.home_activity);
    }

    @Override
    protected void loadData() {
        setTitle("首页");
        navigationView = findViewById(R.id.bottom_navigation_view);
        List<TabItem> items = new ArrayList<>();
        items.add(new TabView(createTabView("假面舞会"), new ParkFragment()));
        items.add(new TabView(createTabView("消息中心"), new MessageFragment()));
        items.add(new TabView(createTabView("约会电台"), new IntegratedRadioFragment()));
        items.add(new TabView(createTabView("个人中心"), new UserFragment()));
        navigationView.initControl(this).setPagerView(items, 0);
        navigationView.getNavgation().setTabControlHeight(60);
        navigationView.getControl().setOnTabClickListener((position, view) -> {

        });

    }



    public View createTabView(String name) {
        View view = LayoutInflater.from(this).inflate(R.layout.home_table_layout,navigationView,false);
        TextView tabName = (TextView) view.findViewById(R.id.tab_name);
        tabName.setText(name);
        return view;
    }

}
