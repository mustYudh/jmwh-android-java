package com.qsd.jmwh.module.home.user.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.yu.common.toast.ToastUtils;
import com.yu.common.windown.BasePopupWindow;

public class PayPop extends BasePopupWindow {

    private final TextView ok;
    private final TextView cancel;
    public interface WithdrawalListener {
        void onWithdrawal(String account,String userName);
    }

    public PayPop(Context context) {
        super(context, LayoutInflater.from(context).inflate(R.layout.pay_pop_layouy, null),
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        ok = (TextView) findViewById(R.id.ok);
        cancel = (TextView) findViewById(R.id.cancel);
        setFocusable(true);
    }


    public PayPop setPositiveButton(WithdrawalListener listener) {
        ok.setOnClickListener(v -> {
            if (listener != null) {
                EditText name = bindView(R.id.name);
                EditText account = bindView(R.id.account);
                if (TextUtils.isEmpty(account.getText().toString().trim())) {
                    ToastUtils.show("账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(name.getText().toString().trim())) {
                    ToastUtils.show("姓名不能为空");
                    return;
                }
                listener.onWithdrawal(account.getText().toString().trim(),name.getText().toString().trim());
            }
        });
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
