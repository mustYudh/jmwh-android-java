package com.qsd.jmwh.module.register.bean;

import android.text.TextUtils;
import com.qsd.jmwh.data.UserProfile;
import com.yu.common.toast.ToastUtils;

public class UploadUserInfoParams {
  public String sNickName = "";
  public String sDateRange = "";
  public String sAge = "";
  public String sJob = "";
  public String sDatePro = "";
  public String sHeight = "";
  public String sWeight = "";
  public String lUserId = "";
  public String sIntroduce = "";
  public String token = "";
  public String sBust = "";
  public String sUserHeadPic = "";
  public String QQ = "";
  public String WX = "";
  public boolean bHiddenQQandWX = true;

  public boolean registerCheckEmpty(boolean isGirl) {
    if (TextUtils.isEmpty(sUserHeadPic)) {
      ToastUtils.show("请设置头像");
      return false;
    } else if (TextUtils.isEmpty(sNickName)) {
      ToastUtils.show("昵称不能为空");
      return false;
    } else if (TextUtils.isEmpty(sDateRange)) {
      ToastUtils.show("约会范围不能为空");
      return false;
    } else if (TextUtils.isEmpty(sAge)) {
      ToastUtils.show("年龄不能为空");
      return false;
    } else if (TextUtils.isEmpty(sJob)) {
      ToastUtils.show("职业不能为空");
      return false;
    } else if (TextUtils.isEmpty(sDatePro)) {
      ToastUtils.show("约会节目不能为空");
      return false;
    } else if (TextUtils.isEmpty(QQ) && TextUtils.isEmpty(WX)) {
      ToastUtils.show("微信或者QQ必须填写一项");
      return false;
    } else if (TextUtils.isEmpty(sHeight)) {
      ToastUtils.show("身高不能为空");
      return false;
    } else if (TextUtils.isEmpty(sWeight)) {
      ToastUtils.show("体重不能为空");
      return false;
    } else if (isGirl && TextUtils.isEmpty(sBust)) {
      ToastUtils.show("胸围不能为空");
      return false;
    } else if (TextUtils.isEmpty(sIntroduce)) {
      ToastUtils.show("个人介绍不能为空");
      return false;
    } else {
      return true;
    }
  }

  public boolean editCheckEmpty() {
    boolean isGirl = UserProfile.getInstance().getSex() == 0;
    if (TextUtils.isEmpty(sNickName)) {
      ToastUtils.show("昵称不能为空");
      return false;
    } else if (TextUtils.isEmpty(sDateRange)) {
      ToastUtils.show("约会范围不能为空");
      return false;
    } else if (TextUtils.isEmpty(sAge)) {
      ToastUtils.show("年龄不能为空");
      return false;
    } else if (TextUtils.isEmpty(sJob)) {
      ToastUtils.show("职业不能为空");
      return false;
    } else if (TextUtils.isEmpty(sDatePro)) {
      ToastUtils.show("约会节目不能为空");
      return false;
    } else if (TextUtils.isEmpty(QQ) && TextUtils.isEmpty(WX)) {
      ToastUtils.show("微信或者QQ必须填写一项");
      return false;
    } else if (TextUtils.isEmpty(sHeight)) {
      ToastUtils.show("身高不能为空");
      return false;
    } else if (TextUtils.isEmpty(sWeight)) {
      ToastUtils.show("体重不能为空");
      return false;
    } else if (isGirl && TextUtils.isEmpty(sBust)) {
      ToastUtils.show("胸围不能为空");
      return false;
    } else if (TextUtils.isEmpty(sIntroduce)) {
      ToastUtils.show("个人介绍不能为空");
      return false;
    } else {
      return true;
    }
  }
}
