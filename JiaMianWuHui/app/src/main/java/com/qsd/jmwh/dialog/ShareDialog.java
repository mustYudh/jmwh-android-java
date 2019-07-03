package com.qsd.jmwh.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.qsd.jmwh.R;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.windown.BasePopupWindow;
import com.yu.share.ShareHelp;
import com.yu.share.SharesBean;
import com.yu.share.callback.ShareCallback;

public class ShareDialog extends BasePopupWindow implements ViewGroup.OnClickListener {
    public ShareDialog(Context context) {
        super(context, View.inflate(context, R.layout.share_dialog_layout, null),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindView(R.id.cancel, this);
        bindView(R.id.we_chat, this);
        bindView(R.id.we_chat_circle, this);
        bindView(R.id.sina, this);
        bindView(R.id.qq, this);

    }

    @Override
    protected View getBackgroundShadow() {
        return null;
    }

    @Override
    protected View getContainer() {
        return null;
    }

    @SuppressLint("CheckResult") @Override
    public void onClick(View v) {
        XHttpProxy.proxy(OtherApiServices.class).getShareInfo().subscribeWith(new TipRequestSubscriber<SharesBean>() {
            @Override protected void onSuccess(SharesBean sharesBean) {
                ShareHelp shareHelp = new ShareHelp((Activity) getContext());
                switch (v.getId()) {
                    case R.id.cancel:
                        dismiss();
                        break;
                    case R.id.we_chat:
                        sharesBean.type = SHARE_MEDIA.WEIXIN;
                        shareHelp.share(sharesBean);
                        dismiss();
                        break;
                    case R.id.we_chat_circle:
                        sharesBean.type = SHARE_MEDIA.WEIXIN_CIRCLE;
                        shareHelp.share(sharesBean);
                        dismiss();
                        break;
                    case R.id.sina:
                        sharesBean.type = SHARE_MEDIA.SINA;
                        shareHelp.share(sharesBean);
                        dismiss();
                        break;
                    case R.id.qq:
                        sharesBean.type = SHARE_MEDIA.QQ;
                        shareHelp.share(sharesBean);
                        dismiss();
                        break;
                    default:
                }

                shareHelp.callback(new ShareCallback() {
                    @Override
                    public void onShareStart(SHARE_MEDIA shareMedia) {

                    }

                    @Override
                    public void onShareSuccess(SHARE_MEDIA media) {

                    }

                    @Override
                    public void onShareFailed(SHARE_MEDIA media, Throwable throwable) {

                    }

                    @Override
                    public void onShareCancel(SHARE_MEDIA shareMedia) {

                    }
                });
            }
        });

    }
}
