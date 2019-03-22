package com.denghao.control;

import android.view.View;
import android.widget.FrameLayout;

/**
 * @author yudneghao
 * @date 2018/6/11
 */

public interface TabViewControl {
    /**
     * 获取填充页
     * @return 填充页
     */
    FrameLayout getContentView();

    /**
     * 添加TabVie
     *
     * @param view TabView
     */
    void addViewTabView(View view);

    /**
     * 移除所有的TabView
     */
    void removeAllTabView();

    /**
     * 获取当前选中的TabView的索引
     *
     * @return
     */
    int getCurrentPosition(int currentPosition);

    /**
     * 获取当前的Tab数量
     *
     * @return 当前Tab数量
     */
    int getTabCount();


    View getOtherView(View view);

    void setOnTabClickListener(TabClickListener onTabClickListener);

     interface TabClickListener {
        void onTabClickListener(int position,View view);
    }
}
