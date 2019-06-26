package com.qsd.jmwh.module.home.park.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.presenter.ToReportPresenter;
import com.qsd.jmwh.module.home.park.presenter.ToReportViewer;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.qsd.jmwh.view.UserItemView;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.ImageLoader;

public class ToReportActivity extends BaseBarActivity implements ToReportViewer {

  private String imageUrl = "";
  @PresenterLifeCycle private ToReportPresenter mPresenter = new ToReportPresenter(this);

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.to_report_layout);
  }

  private String a = "";
  private String b = "";
  private String c = "";
  private String d = "";
  private String e = "";

  private final static String BE_REPORTED_USER_ID = "be_reported_user_id";
  private final static String BIZ_ID = "biz_id";
  private final static String TYPE = "type";

  public static Intent getIntent(Context context, int lBeReportedUserId, int lBizId, int nType) {
    Intent intent = new Intent(context, ToReportActivity.class);
    intent.putExtra(BE_REPORTED_USER_ID, lBeReportedUserId);
    intent.putExtra(BIZ_ID, lBizId);
    intent.putExtra(TYPE, nType);
    return intent;
  }

  @Override protected void loadData() {
    UserItemView user_item_one = bindView(R.id.user_item_one);
    UserItemView user_item_two = bindView(R.id.user_item_two);
    UserItemView user_item_three = bindView(R.id.user_item_three);
    UserItemView user_item_four = bindView(R.id.user_item_four);
    UserItemView user_item_five = bindView(R.id.user_item_five);

    user_item_one.setSwichlistener(switchStatus -> a = switchStatus ? "发广告" : "");
    user_item_two.setSwichlistener(switchStatus -> b = switchStatus ? "骚扰谩骂" : "");
    user_item_three.setSwichlistener(switchStatus -> c = switchStatus ? "虚假照片" : "");
    user_item_four.setSwichlistener(switchStatus -> d = switchStatus ? "色情低俗" : "");
    user_item_five.setSwichlistener(switchStatus -> e = switchStatus ? "她是骗子" : "");

    bindView(R.id.commit, v -> {
      Intent intent = getIntent();
      mPresenter.addFeedBack(intent.getIntExtra(BE_REPORTED_USER_ID, -1),
          intent.getIntExtra(BIZ_ID, -1), a + b + c + d + e, intent.getIntExtra(TYPE, -1),
          imageUrl);
    });

    bindView(R.id.report_image, v -> {
      RxGalleryFinalApi instance = RxGalleryFinalApi.getInstance(getActivity());
      instance.openSingGalleryRadioImgDefault(new RxBusResultDisposable<ImageRadioResultEvent>() {
        @Override protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
          String imgUrl = imageRadioResultEvent.getResult().getOriginalPath();
          PersistenceResponse response = UploadImage.uploadImage(getActivity(),
              UserProfile.getInstance().getObjectName("report"), imgUrl);
          ImageLoader.loadCenterCrop(getActivity(), response.cloudUrl, bindView(R.id.report_image));
          imageUrl = response.cloudUrl;
        }
      });
    });
  }
}
