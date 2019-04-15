package com.qsd.jmwh.base;

import android.view.View;
import com.umeng.analytics.MobclickAgent;
import com.yu.common.framework.BasicFragment;

public abstract class BaseFragment extends BasicFragment {
    @Override
    protected void handleNetWorkError(View view) {

    }

    @Override public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }

    @Override public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
}
