package com.qsd.jmwh.module.home.user.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.module.home.user.bean.UserCenterInfo;
import com.qsd.jmwh.module.home.user.presenter.PhotoDestroySelectPresenter;
import com.qsd.jmwh.module.home.user.presenter.PhotoDestroySelectViewer;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.ImageLoader;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PhotoDestroySelectActivity extends BaseBarActivity
    implements PhotoDestroySelectViewer, View.OnClickListener {

  @PresenterLifeCycle PhotoDestroySelectPresenter mPresenter =
      new PhotoDestroySelectPresenter(this);

  private final static String URL = "url";
  private final static String FILE_TYPE = "file_type";
  private final static String FILE_ID = "file_id";
  private final static String NEED_MONEY = "need_money";
  private final static String FILE_FEE = "file_fee";
  private final static String FILE_COVER_URL = "file_cover_url";
  private ImageView selectDestroy;
  private boolean selected = false;
  private boolean needMoney;
  private boolean isVideo;

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.photo_destroy_select_layout);
  }

  public static Intent getIntent(Context context, String url, boolean needMoney) {
    Intent starter = new Intent(context, PhotoDestroySelectActivity.class);
    starter.putExtra(URL, url);
    starter.putExtra(NEED_MONEY, needMoney);
    return starter;
  }

  public static Intent getIntent(Context context, UserCenterInfo.CdoimgListBean cdoimgListBean) {
    Intent starter = new Intent(context, PhotoDestroySelectActivity.class);
    starter.putExtra(URL, cdoimgListBean.sFileUrl);
    starter.putExtra(FILE_TYPE, cdoimgListBean.nFileType);
    starter.putExtra(FILE_ID, cdoimgListBean.lFileId);
    starter.putExtra(FILE_FEE, cdoimgListBean.nFileFee);
    starter.putExtra(FILE_COVER_URL, cdoimgListBean.sFileCoverUrl);
    return starter;
  }

  @Override protected void loadData() {
    ImageView resource = bindView(R.id.resource);
    String fileCoverUrl = getIntent().getStringExtra(FILE_COVER_URL);
    String url =getIntent().getStringExtra(URL);
    ImageLoader.loadCenterCrop(getActivity(), url, resource);
    bindView(R.id.play_button, !TextUtils.isEmpty(fileCoverUrl)).setOnClickListener(
        v -> getLaunchHelper().startActivity(
            PlayVideoActivity.getIntent(getActivity(), url, fileCoverUrl)));
    needMoney = getIntent().getBooleanExtra(NEED_MONEY, false);
    selectDestroy = bindView(R.id.select_destroy_btn, !needMoney);
    TextView nextAction = bindView(R.id.next_action, this);
    if (needMoney) {
      bindText(R.id.hint, TextUtils.isEmpty(fileCoverUrl) ? "上传为红包视频" : "上传为红包图片");
    }
    int fileId = getIntent().getIntExtra(FILE_ID, -1);
    int fileType = getIntent().getIntExtra(FILE_TYPE, -1);
    if (fileId != -1) {
      selected = fileType == 1 || fileType == 3;
      selectDestroy.setSelected(selected);
      nextAction.setText("删除");
      this.isVideo = !TextUtils.isEmpty(fileCoverUrl);
      setTitle(isVideo ? "删除视频" : "删除图片");
      bindView(R.id.select_destroy, !isVideo);
      bindView(R.id.divider, isVideo);
      //if (isVideo) {
      //  bindText(R.id.hint,"");
      //}
    } else {
      setTitle("选择图片");
      selectDestroy.setSelected(selected);
    }
    bindView(R.id.select_destroy, this);
  }

  @SuppressLint("CheckResult") @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.select_destroy:
        selected = !selected;
        int fileType = getIntent().getIntExtra(FILE_TYPE, 0);
        int fileFee = getIntent().getIntExtra(FILE_FEE, 0);
        int id = getIntent().getIntExtra(FILE_ID, -1);
        if (fileType == 0 || fileType == 1) {
          ToastUtils.show(selected ? "设为阅后即焚照片" : "设为普通照片");
          fileType = selected ? 1 : 0;
        } else if (fileType == 2 || fileType == 3) {
          ToastUtils.show(selected ? "设为阅后即焚红包照片" : "设为普红包照片");
          fileType = selected ? 3 : 2;
        }
        mPresenter.modifyFile(fileType, fileFee, id);
        break;
      case R.id.next_action:
        int fileId = getIntent().getIntExtra(FILE_ID, -1);
        if (fileId == -1) {
          String url = getIntent().getStringExtra(URL);
          Observable.just(url)
              .observeOn(Schedulers.io())
              .subscribe(threadTask1 -> {
                PersistenceResponse response =
                    UploadImage.uploadImage(getActivity(), UserProfile.getInstance().getObjectName(),
                        getIntent().getStringExtra(URL));
                Observable.just(response)
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(result -> {
                  if (!needMoney) {
                    mPresenter.uploadFile(result.cloudUrl, 0, 0, selected ? 1 : 0, 0);
                  } else {
                    SelectHintPop selectHintPop = new SelectHintPop(getActivity());
                    selectHintPop.
                        setTitle("设置红包照片查看金额")
                        .setMessage("每次选择1张照片作为红包照片，用户必须向你发假面币红包才能查看（红包金额随机，每个不超过30假面币）")
                        .setEditButton("确定", input -> {
                          if (TextUtils.isEmpty(input)) {
                            ToastUtils.show("输入金额不能为空");
                          } else if (Integer.parseInt(input) > 30) {
                            ToastUtils.show("不能超过30假面币");
                          } else {
                            mPresenter.uploadFile(response.cloudUrl, 0, 0, selected ? 3 : 2,
                                Integer.parseInt(input));
                            selectHintPop.dismiss();
                          }
                        })
                        .setHint("不能超过30假面币")
                        .setBottomButton("取消", v12 -> selectHintPop.dismiss())
                        .showPopupWindow();
                  }
                }


                );
              });
        } else {
          SelectHintPop selectHintPop = new SelectHintPop(getActivity());
          String title = isVideo ? "个视频" : "张照片";
          selectHintPop.setTitle("提示")
              .setMessage("确定要删除这" + title + "吗？")
              .setNegativeButton("取消", v1 -> {
                selectHintPop.dismiss();
              })
              .setPositiveButton("删除", v12 -> {
                mPresenter.deleteFile(fileId, getIntent().getIntExtra(FILE_TYPE, -1));
                selectHintPop.dismiss();
              })
              .showPopupWindow();
        }
        break;
      default:
    }
  }

  @Override public void modifySuccess(int fileType) {
    selectDestroy.setSelected(selected);
  }
}
