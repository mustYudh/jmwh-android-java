package com.qsd.jmwh.module.home.park.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.dialog.ShareDialog;
import com.qsd.jmwh.module.home.park.adapter.UserPhotoAdapter;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.qsd.jmwh.module.home.park.dialog.MoreActionDialog;
import com.qsd.jmwh.module.home.park.presenter.LookUserInfoPresenter;
import com.qsd.jmwh.module.home.park.presenter.LookUserInfoViewer;
import com.qsd.jmwh.module.home.user.dialog.EvaluationDialog;
import com.qsd.jmwh.module.im.SessionHelper;
import com.qsd.jmwh.module.register.ToByVipActivity;
import com.qsd.jmwh.view.NormaFormItemVIew;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.ImageLoader;
import java.util.ArrayList;

public class LookUserInfoActivity extends BaseActivity
    implements LookUserInfoViewer, View.OnClickListener {

  @PresenterLifeCycle private LookUserInfoPresenter mPresenter = new LookUserInfoPresenter(this);
  private String sNickName;
  private String header;
  private boolean isVip;
  private int userID;
  private int dGalaryVal;
  private int authType;
  private boolean firstLoading = true;

  private final static String USER_ID = "user_id";
  private final static String TYPE = "type";
  private final static String BIZ_ID = "biz_id";

  public static Intent getIntent(Context context, int userId, int lBizId, int nType) {
    Intent starter = new Intent(context, LookUserInfoActivity.class);
    starter.putExtra(USER_ID, userId);
    starter.putExtra(BIZ_ID, lBizId);
    starter.putExtra(TYPE, nType);
    return starter;
  }

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.look_user_info_layout);
  }

  @Override protected void loadData() {
    initListener();
    userID = getIntent().getIntExtra(USER_ID, -1);
    bindView(R.id.more_action, UserProfile.getInstance().getAppAccount() != userID);
  }

  @Override protected void onResume() {
    super.onResume();
    mPresenter.getUserInfo(getIntent().getIntExtra(USER_ID, -1), UserProfile.getInstance().getLat(),
        UserProfile.getInstance().getLng(), firstLoading);
    firstLoading = false;
  }

  private void initListener() {
    bindView(R.id.back, this);
    bindView(R.id.evaluation, this);
    bindView(R.id.social_account, this);
    bindView(R.id.chat, this);
    bindView(R.id.share, this);
    bindView(R.id.more_action, this);
  }

  @Override public void setUserInfo(OtherUserInfoBean userCenterInfo) {
    OtherUserInfoBean.CdoUserDataBean userData = userCenterInfo.cdoUserData;
    isVip = userCenterInfo.bVIP;

    int nSubViewUserCount = userCenterInfo.nSubViewUserCount;
    if (nSubViewUserCount <= 3) {
      SelectHintPop hint = new SelectHintPop(this);
      hint.setTitle("查看人数提示")
          .setMessage("你今天还能查看" + nSubViewUserCount + "位女士，非会员用户每天只能查看10位女士。")
          .setPositiveButton("升级会员", v1 -> {
            buyVip();
            hint.dismiss();
          })
          .setNegativeButton("继续查看", v12 -> hint.dismiss())
          .showPopupWindow();
    }
    NormaFormItemVIew bust = bindView(R.id.bust);
    if (userData.nSex == 0) {
      bust.setContentText(userData.sBust);
      NormaFormItemVIew qq = bindView(R.id.qq, this);
      qq.setContent(isVip ? userData.QQ : "已填写，点击查看");
      NormaFormItemVIew weChat = bindView(R.id.wechat, this);
      weChat.setContent(isVip ? userData.WX : "已填写，点击查看");

    } else {
      bindView(R.id.bust, false);
      bindView(R.id.qq, false);
      bindView(R.id.we_chat, false);
    }
    if (UserProfile.getInstance().getSex() == 1) {
      bindView(R.id.social_account,
          !isVip && UserProfile.getInstance().getAppAccount() != userID);
    }

    ImageLoader.loadCenterCrop(getActivity(), userData.sUserHeadPic, bindView(R.id.header));
    header = userData.sUserHeadPic;
    sNickName = userData.sNickName;

    ImageLoader.blurTransformation(getActivity(), userData.sUserHeadPic, bindView(R.id.header_bg),
        4, 10);
    bindText(R.id.sNickName, userData.sNickName);
    NormaFormItemVIew height = bindView(R.id.height);
    height.setContentText(userData.sHeight);
    NormaFormItemVIew weight = bindView(R.id.weight);
    weight.setContentText(userData.sWeight);
    bust.setContentText(userData.sBust);
    NormaFormItemVIew project = bindView(R.id.project);
    project.setContentText(userData.sDatePro);
    NormaFormItemVIew sIntroduce = bindView(R.id.sIntroduce);
    sIntroduce.setContentText(userData.sIntroduce);
    bindText(R.id.job_age_loc, userData.sCity
        + " · "
        + userData.sJob
        + " · "
        + userData.sAge
        + " · "
        + userData.nOnLine
        + "m");
    bindText(R.id.sDateRange, "约会范围：" + userData.sDateRange + " · " + userData.nOffLineMin + "分钟前");
    authType = userData.nAuthType;
    @DrawableRes int result;
    if (authType == 0) {
      result = R.drawable.ic_not_auth;
    } else if (authType == 3) {
      bindView(R.id.video_auth, true);
      result = R.drawable.ic_video_auth;
    } else {
      if (userData.nSex == 1) {
        result = R.drawable.ic_reliable;
      } else {
        result = R.drawable.ic_info_auth;
      }
    }
    ImageView authStatus = bindView(R.id.auth_type);
    authStatus.setImageResource(result);
    bindText(R.id.auth_info, userData.sAuthInfo);
    ArrayList<OtherUserInfoBean.CdoFileListDataBean> list = userCenterInfo.cdoFileListData;
    bindView(R.id.empty_view, list.size() == 0);
    GridView gridView = bindView(R.id.user_center_photo, list.size() > 0);
    boolean isOpen = userCenterInfo.bOpenImg || isVip;
    gridView.setAdapter(new UserPhotoAdapter(list, isOpen, isVip, userID));
    bindView(R.id.unlock_all_photo_root, !userCenterInfo.bOpenImg && !isVip);
    bindText(R.id.dGalaryVal, "解锁相册" + userData.dGalaryVal + "假面币，会员免费");
    bindView(R.id.dGalaryVal, this);
    dGalaryVal = userData.dGalaryVal;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.back:
        finish();
        break;
      case R.id.evaluation:
        EvaluationDialog evaluationDialog =
            new EvaluationDialog(getActivity(), header, sNickName, userID);
        evaluationDialog.showPopupWindow();
        break;
      case R.id.dGalaryVal:
        SelectHintPop selectHintPop = new SelectHintPop(getActivity());
        selectHintPop.setTitle("解锁付费相册").setPositiveButton("成为会员，免费解锁相册", v1 -> {
          buyVip();
          selectHintPop.dismiss();
        }).setNegativeButton("付费解锁 (" + dGalaryVal + "假面币)", v12 -> {
          mPresenter.buyGalleryPay(userID, dGalaryVal);
          selectHintPop.dismiss();
        }).setBottomButton("取消", v13 -> selectHintPop.dismiss());
        selectHintPop.showPopupWindow();
        break;
      case R.id.chat:
        if (userID == UserProfile.getInstance().getAppAccount()) {
          ToastUtils.show("不能私信自己");
        } else {
          SessionHelper.startP2PSession(getActivity(), "im_" + userID);
        }
        break;
      case R.id.qq:
        toChat();
        break;
      case R.id.wechat:
        toChat();
        break;
      case R.id.social_account:
        toChat();
        break;
      case R.id.share:
        ShareDialog shareDialog = new ShareDialog(getActivity());
        shareDialog.showPopupWindow();
        break;
      case R.id.more_action:
        Intent intent = getIntent();
        MoreActionDialog moreActionDialog =
            new MoreActionDialog(getActivity(), userID, intent.getIntExtra(BIZ_ID, -1),
                intent.getIntExtra(TYPE, -1));
        moreActionDialog.showPopupWindow();
        break;
      default:
    }
  }

  private void toChat() {
    if (UserProfile.getInstance().getSex() == 1) {
      if (!isVip) {
        SelectHintPop hint = new SelectHintPop(this);
        hint.setTitle("联系她")
            .setMessage("查看 " + sNickName + " 的全部资料和私聊她")
            .setPositiveButton("成为会员 免费私聊", v1 -> {
              buyVip();
              hint.dismiss();
            })
            .setNegativeButton("付费查看和私聊 (10假面币)", v12 -> {
              mPresenter.buyContactPay(userID);
              hint.dismiss();
            })
            .setBottomButton("取消", v13 -> hint.dismiss())
            .showPopupWindow();
      } else {
        SessionHelper.startP2PSession(getActivity(), "im_" + userID);
      }
    } else {
      if (authType == 0) {
        SelectHintPop hint = new SelectHintPop(this);
        hint.setTitle("你还没有进行认证")
            .setMessage("认证你的真实性之后，才能私聊男士用户。")
            .setPositiveButton("马上认证", v1 -> {
              hint.dismiss();
            })
            .setNegativeButton("取消", v12 -> hint.dismiss())
            .showPopupWindow();
      } else {
        SessionHelper.startP2PSession(getActivity(), "im_" + userID);
      }
    }
  }

  private void buyVip() {
    LauncherHelper.from(getActivity())
        .startActivity(
            ToByVipActivity.getIntent(getActivity(), UserProfile.getInstance().getAppAccount(),
                UserProfile.getInstance().getAppToken()));
  }

  @Override public void refreshData() {
    loadData();
  }
}
