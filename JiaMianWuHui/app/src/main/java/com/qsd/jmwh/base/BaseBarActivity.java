package com.qsd.jmwh.base;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.yu.common.base.BaseActivity;
import com.yu.common.utils.BarUtils;

public abstract class BaseBarActivity extends BaseActivity {

    protected int getActionBarLayoutId() {
        return R.layout.action_bar_blue;
    }

    @Override
    protected boolean darkMode() {
        return getActionBarLayoutId() != R.layout.action_bar_blue;
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        initBack();
    }


    @Override
    protected View onReplaceRootView(@LayoutRes int layoutResID) {
        View rootView = super.onReplaceRootView(layoutResID);
        FrameLayout container = (FrameLayout) rootView.findViewById(R.id.action_bar_container);
        container.setVisibility(View.VISIBLE);
        View actionBar = getLayoutInflater().inflate(getActionBarLayoutId(), container, false);
        container.addView(actionBar);
        BarUtils.setActionBarLayout(actionBar);
        return rootView;
    }


    private void initBack() {
        FrameLayout back = (FrameLayout) findViewById(R.id.back);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }


    public void setTitle(String titleName) {
        if (!TextUtils.isEmpty(titleName)) {
            TextView title = (TextView) findViewById(R.id.title);
            if (title != null) {
                title.setText(titleName);
            }
        }
    }

    public void setRightMenu(CharSequence text, View.OnClickListener onClickListener) {
        if (!TextUtils.isEmpty(text)) {
            TextView right = bindView(R.id.right_menu, onClickListener);
            right.setText(text);
            right.setVisibility(View.VISIBLE);
        }
    }
}
