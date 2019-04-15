package com.qsd.jmwh.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.denghao.control.TabItem;
import com.denghao.control.TabView;
import com.denghao.control.view.BottomNavigationView;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.integrated.IntegratedRadioFragment;
import com.qsd.jmwh.module.home.message.MessageFragment;
import com.qsd.jmwh.module.home.park.ParkFragment;
import com.qsd.jmwh.module.home.user.UserFragment;
import com.qsd.jmwh.base.BaseActivity;

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
        items.add(new TabView(createTabView("假面舞会",R.drawable.tab_01), new ParkFragment()));
        items.add(new TabView(createTabView("消息中心",R.drawable.tab_02), new MessageFragment()));
        items.add(new TabView(createTabView("约会电台",R.drawable.tab_03), new IntegratedRadioFragment()));
        items.add(new TabView(createTabView("个人中心",R.drawable.tab_04), new UserFragment()));
        navigationView.initControl(this).setPagerView(items, 0);
        navigationView.getNavgation().setTabControlHeight(60);
        navigationView.getControl().setOnTabClickListener((position, view) -> {

        });

    }



    public View createTabView(String name,int drawable) {
        View view = LayoutInflater.from(this).inflate(R.layout.home_table_layout,navigationView,false);
        ImageView imageView =  view.findViewById(R.id.tab_icon);
        TextView tabName =  view.findViewById(R.id.tab_name);
        imageView.setImageResource(drawable);
        tabName.setText(name);
        return view;
    }

}
