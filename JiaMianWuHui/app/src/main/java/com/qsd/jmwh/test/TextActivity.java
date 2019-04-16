package com.qsd.jmwh.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yu.share.AuthLoginHelp;
import com.yu.share.callback.AuthLoginCallback;
import java.util.Map;

/**
 * @author yudneghao
 * @date 2019/4/15
 */
public class TextActivity extends BaseActivity implements AuthLoginCallback {
  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.test_activity);
  }

  @Override protected void loadData() {

    AuthLoginHelp share = new AuthLoginHelp(this);
    share.callback(this);
    bindView(R.id.text, v -> {
      share.login(SHARE_MEDIA.WEIXIN);

    });

    bindView(R.id.text2, v -> {
      share.login(SHARE_MEDIA.QQ);
    });




  }


  @Override public void onStart(SHARE_MEDIA media) {

  }

  @Override public void onComplete(SHARE_MEDIA media, int code, Map<String, String> map) {

  }

  @Override public void onError(SHARE_MEDIA media, int code, Throwable throwable) {
    Log.e("=====>","onShareFailed" + code);

  }

  @Override public void onCancel(SHARE_MEDIA media, int i) {
    Log.e("=====>","onShareCancel");

  }
}
