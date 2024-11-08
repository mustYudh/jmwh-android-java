package com.qsd.jmwh.module.home.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class PhotoDestroySelectActivity extends BaseBarActivity
    implements PhotoDestroySelectViewer, View.OnClickListener {

  @PresenterLifeCycle PhotoDestroySelectPresenter mPresenter =
      new PhotoDestroySelectPresenter(this);

  private final static String URL = "url";
  private final static String FILE_TYPE = "file_type";
  private final static String FILE_ID = "file_id";
  private final static String NEED_MONEY = "need_money";
  private final static String FILE_FEE = "file_fee";
  private ImageView selectDestroy;
  private LinearLayout selectDestroyBtn;
  private boolean selected = false;
  private boolean needMoney;
  private static boolean itemClick = false;

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
    itemClick = true;
    return starter;
  }

  @Override protected void loadData() {
    ImageView resource = bindView(R.id.resource);
    String url = getIntent().getStringExtra(URL);
    ImageLoader.loadCenterCrop(getActivity(), url, resource);
    needMoney = getIntent().getBooleanExtra(NEED_MONEY, false);
    selectDestroy = bindView(R.id.select_destroy_btn, !needMoney);
    selectDestroyBtn = bindView(R.id.select_destroy, this);
    TextView nextAction = bindView(R.id.next_action, this);
    if (needMoney) {
      bindText(R.id.hint, "上传为红包图片");
    }
    int fileId = getIntent().getIntExtra(FILE_ID, -1);
    int fileType = getIntent().getIntExtra(FILE_TYPE, -1);
    if (fileId != -1) {
      selected = fileType == 1 || fileType == 3;
      selectDestroy.setSelected(selected);
      nextAction.setText("删除");
      setTitle("删除图片");
    } else {
      setTitle("选择图片");
      selectDestroy.setSelected(selected);
    }
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.select_destroy:
        selected = !selected;
        int fileType = getIntent().getIntExtra(FILE_TYPE, 0);
        int fileFee = getIntent().getIntExtra(FILE_FEE, 0);
        int id = getIntent().getIntExtra(FILE_ID, -1);
        if (itemClick) {
          if (fileType == 0 || fileType == 1) {
            ToastUtils.show(selected ? "设为阅后即焚照片" : "设为普通照片");
            fileType = selected ? 1 : 0;
          } else if (fileType == 2 || fileType == 3) {
            ToastUtils.show(selected ? "设为阅后即焚红包照片" : "设为普红包照片");
            fileType = selected ? 3 : 2;
          }
          mPresenter.modifyFile(fileType, fileFee, id);
        }
        break;
      case R.id.next_action:
        int fileId = getIntent().getIntExtra(FILE_ID, -1);
        if (fileId == -1) {
          PersistenceResponse response =
              UploadImage.uploadImage(getActivity(), UserProfile.getInstance().getObjectName(),
                  getIntent().getStringExtra(URL));
          if (!needMoney) {
            mPresenter.uploadFile(response.cloudUrl, 0, 0, selected ? 1 : 0, 0);
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
                    // TODO: 2019/5/17 阅后即焚红包需要完善
                    mPresenter.uploadFile(response.cloudUrl, 0, 0, selected ? 3 : 2,
                        Integer.parseInt(input));
                    selectHintPop.dismiss();
                  }
                })
                .setHint("不能超过30假面币")
                .setBottomButton("取消", v12 -> selectHintPop.dismiss())
                .showPopupWindow();
          }
        } else {
          SelectHintPop selectHintPop = new SelectHintPop(getActivity());
          selectHintPop.setTitle("提示").setMessage("确定要删除这张照片吗？").setNegativeButton("取消", v1 -> {
            selectHintPop.dismiss();
          }).setPositiveButton("删除", v12 -> {
            mPresenter.deleteFile(fileId, getIntent().getIntExtra(FILE_TYPE, -1));
            selectHintPop.dismiss();
          }).showPopupWindow();
        }
        break;
    }
  }

  @Override public void modifySuccess(int fileType) {
    selectDestroy.setSelected(selected);

  }
}
