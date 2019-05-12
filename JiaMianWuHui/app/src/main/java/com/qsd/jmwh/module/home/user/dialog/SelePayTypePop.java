package com.qsd.jmwh.module.home.user.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.qsd.jmwh.R;
import com.yu.common.windown.BasePopupWindow;

public class SelePayTypePop extends BasePopupWindow {
    public SelePayTypePop(Context context) {
        super(context, LayoutInflater.from(context).inflate(R.layout.sele_pay_type_pop_layout, null),
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        bindView(R.id.close, v -> dismiss());
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
