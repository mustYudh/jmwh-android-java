package com.qsd.jmwh.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.yu.common.windown.BasePopupWindow;

public class SelectHintPop extends BasePopupWindow {
    private final TextView content;
    private final TextView title;
    private final TextView ok;
    private final TextView cancel;
    private final View divider;



    public SelectHintPop(Context context) {
        super(context, LayoutInflater.from(context).inflate(R.layout.select_gender_hint_pop_layout, null),
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        ok = (TextView) findViewById(R.id.ok);
        cancel = (TextView) findViewById(R.id.cancel);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        divider = (View) findViewById(R.id.divider);

    }

    public SelectHintPop setTitle(CharSequence name) {
        if (TextUtils.isEmpty(name)) {
            title.setText("提示");
        } else {
            title.setText(name);
        }
        return this;
    }


    public SelectHintPop setMessage(CharSequence message) {
        if (!TextUtils.isEmpty(message)) {
            content.setText(message);
        }
        return this;
    }


    public SelectHintPop setNegativeButton(CharSequence name, View.OnClickListener listener) {
        if (TextUtils.isEmpty(name)) {
            cancel.setText("取消");
        }
        cancel.setOnClickListener(listener);
        return this;
    }


    public SelectHintPop setPositiveButton(CharSequence name, View.OnClickListener listener) {
        if (TextUtils.isEmpty(name)) {
            ok.setText("确定");
        }
        ok.setOnClickListener(listener);
        return this;
    }

    public SelectHintPop setSingleButton(CharSequence name, View.OnClickListener listener) {
        cancel.setVisibility(View.GONE);
        divider.setVisibility(View.GONE);
        ok.setVisibility(View.VISIBLE);
        setPositiveButton(name,listener);
        return this;
    }


    @Override
    protected View getBackgroundShadow() {
        return findViewById(R.id.base_shadow);
    }

    @Override
    protected View getContainer() {
        return findViewById(R.id.base_container);
    }
}
