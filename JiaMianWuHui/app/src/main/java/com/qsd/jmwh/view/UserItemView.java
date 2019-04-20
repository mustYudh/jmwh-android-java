package com.qsd.jmwh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.qsd.jmwh.R;

public class UserItemView extends LinearLayout {

    public UserItemView(Context context) {
        super(context,null);
    }

    public UserItemView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        initView(context,attrs);
    }


    public UserItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.inflate_user_item, this, true);
    }
}
