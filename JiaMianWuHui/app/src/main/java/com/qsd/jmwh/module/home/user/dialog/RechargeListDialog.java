package com.qsd.jmwh.module.home.user.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.qsd.jmwh.R;
import com.yu.common.windown.BasePopupWindow;

/**
 * @author yudneghao
 * @date 2019-05-15
 */
public class RechargeListDialog extends BasePopupWindow {

  public RechargeListDialog(Context context) {
    super(context, LayoutInflater.from(context).inflate(R.layout.pop_recharge_list_layout, null),
        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
  }

  @Override protected View getBackgroundShadow() {
    return null;
  }

  @Override protected View getContainer() {
    return null;
  }
}
