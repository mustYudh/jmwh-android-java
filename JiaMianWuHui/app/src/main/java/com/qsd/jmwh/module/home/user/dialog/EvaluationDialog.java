package com.qsd.jmwh.module.home.user.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.yu.common.utils.ImageLoader;
import com.yu.common.windown.BasePopupWindow;
public class EvaluationDialog extends BasePopupWindow {

    public EvaluationDialog(Context context,String header,String sNickName) {
        super(context, View.inflate(context, R.layout.user_evaluation_dialog, null),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageLoader.loadCenterCrop(context, header, bindView(R.id.header));
        TextView nickName = bindView(R.id.sNickName);
        nickName.setText(sNickName);
        initListener();

    }

    private void initListener() {
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
