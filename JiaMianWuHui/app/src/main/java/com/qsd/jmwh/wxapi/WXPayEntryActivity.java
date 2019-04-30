package com.qsd.jmwh.wxapi;

import android.os.Bundle;
import android.widget.Toast;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.register.bean.PayInfo;
import com.qsd.jmwh.utils.PayUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.yu.common.toast.ToastUtils;

import static com.qsd.jmwh.utils.PayUtils.WX_PAY_INFO;

/**
 * @author yudneghao
 * @date 2019-04-30
 */
public class WXPayEntryActivity extends WXEntryActivity implements IWXAPIEventHandler {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    PayInfo info = (PayInfo) getIntent().getSerializableExtra(WX_PAY_INFO);
    PayUtils.getInstance().pay(this, 2, info);
  }

  @Override public void onResp(BaseResp baseResp) {
    PayUtils.WXPayCallBack wxPayCallBack = PayUtils.getInstance().getWXPayCallback();
    if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
      switch (baseResp.errCode) {
        case BaseResp.ErrCode.ERR_OK:
          Toast.makeText(this, "微信支付成功", Toast.LENGTH_SHORT).show();
          if (wxPayCallBack != null) {
            wxPayCallBack.onPaySuccess();
          }
          break;
        case BaseResp.ErrCode.ERR_COMM:
          ToastUtils.show("微信支付失败");
          break;
        case BaseResp.ErrCode.ERR_USER_CANCEL:
          if (wxPayCallBack != null) {
            wxPayCallBack.onCancel();
          }
          ToastUtils.show("未支付");
          break;
        case BaseResp.ErrCode.ERR_AUTH_DENIED:
          ToastUtils.show("微信支付无权限");
          break;
        default:
          ToastUtils.show("微信支付出错");
          break;
      }
      PayUtils.getInstance().setWXPayResult(null);
    }
    finish();
    overridePendingTransition(R.anim.activity_alpha_out, R.anim.activity_alpha_out);
  }
}
