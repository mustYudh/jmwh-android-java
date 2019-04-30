package com.qsd.jmwh.wxapi;

import android.os.Bundle;

import com.qsd.jmwh.R;
import com.qsd.jmwh.module.register.bean.PayInfo;
import com.qsd.jmwh.utils.PayUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import static com.qsd.jmwh.utils.PayUtils.PAY_TYPE;
import static com.qsd.jmwh.utils.PayUtils.WX_PAY_INFO;

/**
 * @author yudneghao
 * @date 2019-04-30
 */
public class WXPayEntryActivity extends WXEntryActivity implements IWXAPIEventHandler {

    private PayInfo info = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        info = (PayInfo) getIntent().getSerializableExtra(WX_PAY_INFO);
        PayUtils.getInstance().wxPay(this, 2, info);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        PayUtils.PayCallBack wxPayCallBack = PayUtils.getInstance().getWXPayCallback();
        int type = getIntent().getIntExtra(PAY_TYPE, -1);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
//          Toast.makeText(this, "微信支付成功", Toast.LENGTH_SHORT).show();
                    PayUtils.getInstance().checkPaySuccess(type, info, wxPayCallBack);
                    break;
                case BaseResp.ErrCode.ERR_COMM:
//          ToastUtils.show("微信支付失败");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    if (wxPayCallBack != null) {
                        wxPayCallBack.onFailed(type);
                    }
//          ToastUtils.show("未支付");
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    if (wxPayCallBack != null) {
                        wxPayCallBack.onFailed(type);
                    }
//          ToastUtils.show("微信支付无权限");
                    break;
                default:
                    if (wxPayCallBack != null) {
                        wxPayCallBack.onFailed(type);
                    }
//          ToastUtils.show("微信支付出错");
                    break;
            }
            PayUtils.getInstance().getPayResult(null);
        }
        finish();
        overridePendingTransition(R.anim.activity_alpha_out, R.anim.activity_alpha_out);
    }
}
