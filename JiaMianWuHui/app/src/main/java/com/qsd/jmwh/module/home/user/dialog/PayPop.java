package com.qsd.jmwh.module.home.user.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.yu.common.windown.BasePopupWindow;

public class PayPop extends BasePopupWindow {

    private final TextView ok;
    private final TextView cancel;


    public PayPop(Context context) {
        super(context, LayoutInflater.from(context).inflate(R.layout.pay_pop_layouy, null),
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        ok = (TextView) findViewById(R.id.ok);
        cancel = (TextView) findViewById(R.id.cancel);
    }


    public PayPop setPositiveButton(View.OnClickListener listener) {
        ok.setOnClickListener(listener);
        return this;
    }

    public PayPop setNegativeButton(View.OnClickListener listener) {
        cancel.setOnClickListener(listener);
        return this;
    }

    @Override
    protected View getBackgroundShadow() {
        return null;
    }

    @Override
    protected View getContainer() {
        return null;
    }
}
