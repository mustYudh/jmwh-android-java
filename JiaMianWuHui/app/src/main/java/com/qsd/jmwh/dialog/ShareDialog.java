package com.qsd.jmwh.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.qsd.jmwh.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
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

    @Override
    public void onClick(View v) {
        ShareHelp shareHelp = new ShareHelp((Activity) getContext());
        SharesBean sharesBean = new SharesBean();
        sharesBean.title = "假面舞会";
        sharesBean.content = "欢迎使用假面舞会";
        sharesBean.targetUrl = "https://maskball.oss-cn-beijing.aliyuncs.com/app/ic_launcher.png";
        sharesBean.iconUrl = "https://maskball.oss-cn-beijing.aliyuncs.com/app/ic_launcher.png";
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
}
