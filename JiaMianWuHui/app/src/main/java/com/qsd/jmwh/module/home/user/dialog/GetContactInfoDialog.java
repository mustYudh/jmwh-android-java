package com.qsd.jmwh.module.home.user.dialog;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.qsd.jmwh.R;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.qsd.jmwh.module.im.SessionHelper;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.ImageLoader;
import com.yu.common.windown.BasePopupWindow;

/**
 * @author yudenghao
 * @date 2019-07-14
 */
public class GetContactInfoDialog extends BasePopupWindow {

  @SuppressLint("CheckResult") public GetContactInfoDialog(Context context, int userId) {
    super(context, View.inflate(context, R.layout.dialog_get_conact_info_layout, null),
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    TextView qq = bindView(R.id.qq);
    TextView weChat = bindView(R.id.we_chat);
    bindView(R.id.to_chat, v -> {
      SessionHelper.startP2PSession(context, "im_" + userId);
      dismiss();
    });
    bindView(R.id.copy_we_chat, v -> {
      ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
      ClipData mClipData = ClipData.newPlainText("Label", weChat.getText().toString().trim());
      assert cm != null;
      cm.setPrimaryClip(mClipData);
      ToastUtils.show("复制成功");
    });

    bindView(R.id.copy_qq, v -> {
      ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
      ClipData mClipData = ClipData.newPlainText("Label", qq.getText().toString().trim());
      assert cm != null;
      cm.setPrimaryClip(mClipData);
      ToastUtils.show("复制成功");
    });

    XHttpProxy.proxy(ApiServices.class)
        .getOtherUserInfo(userId, UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng())
        .subscribeWith(new TipRequestSubscriber<OtherUserInfoBean>() {
          @Override protected void onSuccess(OtherUserInfoBean userCenterInfo) {
            qq.setText(TextUtils.isEmpty(userCenterInfo.cdoUserData.QQ) ? "未填写": userCenterInfo.cdoUserData.QQ);
            weChat.setText(TextUtils.isEmpty(userCenterInfo.cdoUserData.WX) ? "未填写": userCenterInfo.cdoUserData.WX);
            bindView(R.id.copy_qq,!TextUtils.isEmpty(userCenterInfo.cdoUserData.QQ));
            bindView(R.id.wechat,!TextUtils.isEmpty(userCenterInfo.cdoUserData.WX));
           TextView hint = bindView(R.id.hint);
           hint.setText(userCenterInfo.cdoUserData.nSex == 1 ? "他的社交账号" : "她的社交账号");
           TextView nickName = bindView(R.id.nick_name);
            ImageLoader.loadCenterCrop(context, userCenterInfo.cdoUserData.sUserHeadPic, bindView(R.id.header));
           nickName.setText(userCenterInfo.cdoUserData.sNickName);
          }
        });
    bindView(R.id.close,v -> dismiss());

  }

  @Override protected View getBackgroundShadow() {
    return bindView(R.id.root);
  }

  @Override protected View getContainer() {
    return bindView(R.id.content);
  }

}
