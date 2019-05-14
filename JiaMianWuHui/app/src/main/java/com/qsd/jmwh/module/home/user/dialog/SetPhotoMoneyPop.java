package com.qsd.jmwh.module.home.user.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.ImageLoader;
import com.yu.common.windown.BasePopupWindow;

public class SetPhotoMoneyPop extends BasePopupWindow {
    public SetPhotoMoneyPop(Context context, String headerUrl, String userName,SettingSuccessListener settingSuccessListener) {
        super(context, LayoutInflater.from(context).inflate(R.layout.set_photo_money_pop_layouy, null),
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        ImageView header = bindView(R.id.header);
        EditText money = bindView(R.id.money);
        TextView name = bindView(R.id.name);
        name.setText(userName);
        ImageLoader.loadCenterCrop(context, headerUrl, header);
        bindView(R.id.cancel, v -> dismiss());
        bindView(R.id.ok, v -> {
            String moneyCount = money.getText().toString().trim();
            if (!TextUtils.isEmpty(moneyCount)) {
                int count = Integer.parseInt(moneyCount);
                if (count == 0) {
                    ToastUtils.show("输入金额不能为0");
                } else {
                    settingSuccessListener.success(count);
                    dismiss();
                }
            } else {
                ToastUtils.show("输入金额不能为空");
            }

        });
    }


    public interface SettingSuccessListener {
        void success(int money);
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
