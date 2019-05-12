package com.qsd.jmwh.module.home.park.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.qsd.jmwh.R;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.park.activity.ToReportActivity;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.toast.ToastUtils;
import com.yu.common.windown.BasePopupWindow;

public class MoreActionDialog extends BasePopupWindow implements View.OnClickListener {
    private int userId;
    public MoreActionDialog(Context context,int userId) {
        super(context, View.inflate(context, R.layout.more_action_dialog, null),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindView(R.id.cancel, this);
        bindView(R.id.to_black_list, this);
        bindView(R.id.to_report, this);
        this.userId = userId;
    }

    @Override
    protected View getBackgroundShadow() {
        return null;
    }

    @Override
    protected View getContainer() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.to_black_list:
                SelectHintPop hint = new SelectHintPop(getContext());
                hint.setTitle("提示")
                        .setMessage("你们将无法再在假面舞会与会对方，确定吗？")
                        .setSingleButton("确定拉黑", v1 -> {
                            toBlackList();
                            hint.dismiss();
                        }).setBottomButton("取消", v13 -> hint.dismiss())
                        .showPopupWindow();
                dismiss();
                break;
            case R.id.to_report:
                LauncherHelper.from(getContext()).startActivity(ToReportActivity.class);
                dismiss();
                break;
        }
    }


    @SuppressLint("CheckResult")
    private void toBlackList() {
        XHttpProxy.proxy(OtherApiServices.class)
                    .toBlackList(userId,1).subscribeWith(new TipRequestSubscriber<Object>() {
            @Override
            protected void onSuccess(Object o) {
                ToastUtils.show("已拉黑");
            }
        });
    }
}
