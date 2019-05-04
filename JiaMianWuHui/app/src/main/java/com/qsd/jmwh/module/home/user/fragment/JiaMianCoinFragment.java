package com.qsd.jmwh.module.home.user.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseFragment;
import com.qsd.jmwh.module.home.user.dialog.PayPop;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.withdrawal:
                PayPop payPop = new PayPop(getActivity());
                payPop.showPopupWindow();
                payPop.setNegativeButton(v1 -> {
                    payPop.dismiss();
                });
                break;
        }
    }
}
