package com.yu.common.loading.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yu.common.R;

public class LoadingView extends LinearLayout {
    private ProgressBar progressBar;
    private TextView tv;
    private ImageView iv;
    private Context context;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.loading_view, this, true);
        progressBar = view.findViewById(R.id.progressBar);
        tv = findViewById(R.id.tv);
        iv = findViewById(R.id.iv);
    }

    /**
     * loading
     */
    public void showLoading() {
        iv.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
    }


    public void showSuccess() {
        iv.setImageResource(R.drawable.load_success);
        iv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(GONE);
    }


    public void showFail() {
        iv.setImageResource(R.drawable.load_fail);
        iv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(GONE);
    }


    public void setText(String txt) {
        tv.setText(txt);
    }


    public void setText(@StringRes int txtId) {
        tv.setText(txtId);
    }
}

