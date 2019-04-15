package com.qsd.jmwh.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * @author yudneghao
 * @date 2019/4/15
 */
public class TextActivity extends BaseActivity {
  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.test_activity);
  }

  @Override protected void loadData() {
    UMImage thumb =  new UMImage(this, R.drawable.ic_back_arrow);
    thumb.setThumb(thumb);
      bindView(R.id.text, new View.OnClickListener() {
        @Override public void onClick(View v) {
          new ShareAction(TextActivity.this)
              .setPlatform(SHARE_MEDIA.SINA)//传入平台
              .withText("hello")//分享内容
              .withMedia(thumb)
              .setCallback(new UMShareListener() {
                @Override public void onStart(SHARE_MEDIA media) {
                  Log.e("=====>","start" + media.getName());
                }

                @Override public void onResult(SHARE_MEDIA media) {
                  Log.e("=====>","onResult" + media.getName());
                }

                @Override public void onError(SHARE_MEDIA media, Throwable throwable) {
                  Log.e("=====>","onError" + media.getName());
                }

                @Override public void onCancel(SHARE_MEDIA media) {
                  Log.e("=====>","onCancel" + media.getName());
                }
              })//回调监听器
              .share();
        }
      });
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
  }
}
