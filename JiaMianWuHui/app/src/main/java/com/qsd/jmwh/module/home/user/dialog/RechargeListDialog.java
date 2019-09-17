package com.qsd.jmwh.module.home.user.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.user.adapter.RechargeAdapter;
import com.qsd.jmwh.module.home.user.bean.GoodsInfoBean;
import com.qsd.jmwh.utils.PayUtils;
import com.yu.common.windown.BasePopupWindow;

import java.util.List;

/**
 * @author yudneghao
 * @date 2019-05-15
 */
public class RechargeListDialog extends BasePopupWindow {

  public RechargeListDialog(Context context, List<GoodsInfoBean.CdoListBean> list,
      PayUtils.PayCallBack payCallBack) {
    super(context, LayoutInflater.from(context).inflate(R.layout.pop_recharge_list_layout, null),
        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    RecyclerView recyclerView = bindView(R.id.list);
    ImageView cancel = bindView(R.id.cancel);
    cancel.setOnClickListener(v -> dismiss());
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    RechargeAdapter adapter = new RechargeAdapter(R.layout.item_jia_mian_coin_layout, list);
    recyclerView.setAdapter(adapter);
    adapter.setOnItemChildClickListener((adapter1, view, position) -> {
      if (view.getId() == R.id.goods_sale_fee) {
        GoodsInfoBean.CdoListBean data =
            (GoodsInfoBean.CdoListBean) adapter1.getData().get(position);
        SelectedPayTypePop selePayTypePop =
            new SelectedPayTypePop(context, data, new PayUtils.PayCallBack() {
              @Override public void onPaySuccess(int type) {
                payCallBack.onPaySuccess(type);
              }

              @Override public void onFailed(int type) {
                payCallBack.onFailed(type);
              }
            });
        selePayTypePop.showPopupWindow();
        dismiss();
      }
    });
  }

  @Override protected View getBackgroundShadow() {
    return null;
  }

  @Override protected View getContainer() {
    return null;
  }
}
