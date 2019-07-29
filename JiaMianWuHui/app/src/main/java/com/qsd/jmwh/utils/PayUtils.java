package com.qsd.jmwh.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.register.bean.PayInfo;
import com.qsd.jmwh.utils.bean.PayResult;
import com.qsd.jmwh.utils.countdown.RxCountDown;
import com.qsd.jmwh.utils.countdown.RxCountDownAdapter;
import com.qsd.jmwh.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.toast.ToastUtils;
import com.yu.share.WXPayUtils;

import java.util.Map;

/**
 * @author yudneghao
 * @date 2019-04-30
 */
@SuppressLint("CheckResult")
public class PayUtils {
    private static final PayUtils ourInstance = new PayUtils();
    public final static String WX_PAY_INFO = "wx_pay_info";
    public final static String PAY_TYPE = "pay_type";
    private RxCountDown looperTime = new RxCountDown();
    private Handler handler = new Handler();
    private PayCallBack payCallback;

    private boolean needCheck = false;
    private boolean isStart = false;


    public interface PayCallBack {
        void onPaySuccess(int type);

        void onFailed(int type);
    }

    public static PayUtils getInstance() {
        return ourInstance;
    }

    private PayUtils() {
    }

    private void startPay(Activity context, int type, PayInfo info) {
        if (type == 1) {
            if (checkAliPayInstalled(context)) {
                final Runnable payRunnable = () -> {
                    PayTask aliPay = new PayTask(context);
                    Map<String, String> result = aliPay.payV2(info.sPaySign, true);
                    PayResult payResult = new PayResult(result);
                    context.runOnUiThread(() -> {
                        String resultStatus = payResult.getResultStatus();
                        if (TextUtils.equals(resultStatus, "9000")) {
                            checkPaySuccess(type, info, payCallback);
                        } else {
                            if (payCallback != null) {
                                payCallback.onFailed(type);
                            }
                        }
                    });
                };
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            } else {
                ToastUtils.show("请先安装支付宝");
            }

        } else if (type == 2) {
            PayReq request = new PayReq();
            request.appId = info.appid;
            request.partnerId = info.partnerid;
            request.prepayId = info.prepayid;
            request.packageValue = info.packageX;
            request.nonceStr = info.noncestr;
            request.timeStamp = info.timestamp;
            request.sign = info.sign;
            WXPayUtils.getInstance(context).sendRequest(request);
        } else {
            needCheck = false;
            if (payCallback != null) {
                payCallback.onPaySuccess(type);
            }
        }
        looperTime.setCountDownTimeListener(new RxCountDownAdapter() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                needCheck = false;
                isStart = false;
            }

            @Override
            public void onComplete() {
                super.onComplete();
                isStart = false;
                needCheck = false;
            }
        });
    }


    public void getPayResult(PayCallBack payCallback) {
        this.payCallback = payCallback;
    }

    public PayCallBack getWXPayCallback() {
        return payCallback;
    }


    private void startWXPay(Activity context, int type, PayInfo info) {
        boolean installWeChat =
                UMShareAPI.get(context).isInstall(context, SHARE_MEDIA.WEIXIN);
        if (installWeChat) {
            Intent starter = new Intent(context, WXPayEntryActivity.class);
            starter.putExtra(WX_PAY_INFO, info);
            starter.putExtra(PAY_TYPE, type);
            context.startActivity(starter);
        } else {
            ToastUtils.show("请先安装微信");
        }
    }


    public PayUtils pay(Activity context, int type, PayInfo info) {
        needCheck = true;
        if (type == 2) {
            startWXPay(context, type, info);
        } else {
            startPay(context, type, info);
        }

        return this;
    }


    public void wxPay(Activity context, int type, PayInfo info) {
        startPay(context, type, info);
    }


    private static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }


    public void checkPaySuccess(int type, PayInfo info, PayCallBack payCallback) {
        if (!isStart) {
            looperTime.start(30);
            isStart = true;
        }
        if (needCheck) {
            XHttpProxy.proxy(ApiServices.class)
                    .checkPaySuccess(info.lOrderId)
                    .subscribeWith(new TipRequestSubscriber<PayInfo>() {
                        @Override
                        protected void onSuccess(PayInfo payInfo) {
                            needCheck = false;
                            if (payCallback != null) {
                                payCallback.onPaySuccess(type);
                            }
                            recycle();
                        }

                        @Override
                        public void onError(ApiException e) {
                            handler.postDelayed(() -> checkPaySuccess(type, info, payCallback), 2000);

                        }
                    });
        }
    }


    public void recycle() {
        needCheck = false;
        if (payCallback != null) {
            getPayResult(null);
        }
        if (looperTime != null) {
            looperTime.stop();
        }
        if (handler != null) {
            handler.removeCallbacks(null);
        }
    }
}
