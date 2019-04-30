package com.qsd.jmwh.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.qsd.jmwh.module.register.bean.PayInfo;
import com.qsd.jmwh.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yu.common.toast.ToastUtils;
import com.yu.share.WXPayUtils;

/**
 * @author yudneghao
 * @date 2019-04-30
 */
public class PayUtils {
  private static final PayUtils ourInstance = new PayUtils();
  public final static String WX_PAY_INFO = "wx_pay_info";

  private WXPayCallBack payCallback;

  public interface WXPayCallBack {
     void onPaySuccess();
     void onCancel();
  }

  public static PayUtils getInstance() {
    return ourInstance;
  }

  private PayUtils() {
  }

  public void pay(Context context, int type, PayInfo info) {
    if (type == 2) {
      PayReq request = new PayReq();
      request.appId = info.appid;
      request.partnerId = info.partnerid;
      request.prepayId = info.prepayid;
      request.packageValue = info.packageX;
      request.nonceStr = info.noncestr;
      request.timeStamp = info.timestamp;
      request.sign = info.sign;
      WXPayUtils.getInstance(context).sendRequest(request);
    }
  }

  public PayUtils setWXPayResult(WXPayCallBack payCallback) {
    this.payCallback = payCallback;
    return this;
  }

  public WXPayCallBack getWXPayCallback() {
    return payCallback;
  }


  public PayUtils startWXPay(Activity context, PayInfo info) {
    boolean installWeChat =
        UMShareAPI.get(context).isInstall(context, SHARE_MEDIA.WEIXIN);
    if (installWeChat) {
      Intent starter = new Intent(context, WXPayEntryActivity.class);
      starter.putExtra(WX_PAY_INFO, info);
      context.startActivity(starter);
    } else {
      ToastUtils.show("请先安装微信");
    }
    return this;
  }
}
