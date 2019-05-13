package com.qsd.jmwh.module.home.user.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseFragment;
import com.qsd.jmwh.module.home.user.dialog.PayPop;
import com.qsd.jmwh.module.home.user.dialog.SelePayTypePop;

public class JiaMianCoinFragment extends BaseFragment implements View.OnClickListener {
    @Override
    protected int getContentViewId() {
        return R.layout.jia_mian_coin_fragment;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        bindView(R.id.withdrawal, this);
        bindView(R.id.top_up, this);
        bindView(R.id.ll_withdrawal, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_withdrawal:
                PayPop withdrawal = new PayPop(getActivity());
                withdrawal.showPopupWindow();
                withdrawal.setNegativeButton(v1 -> {
                    withdrawal.dismiss();
                });
                break;
            case R.id.withdrawal:
                PayPop payPop = new PayPop(getActivity());
                payPop.showPopupWindow();
                payPop.setNegativeButton(v1 -> {
                    payPop.dismiss();
                });
                break;
            case R.id.top_up:
                SelePayTypePop selePayTypePop = new SelePayTypePop(getActivity());
                selePayTypePop.showPopupWindow();
                break;

        }
    }
}
