package com.qsd.jmwh.module.home.user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qsd.jmwh.R;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.park.activity.LookUserInfoActivity;
import com.qsd.jmwh.module.home.park.bean.SubViewCount;
import com.qsd.jmwh.module.home.park.presenter.LookUserInfoPresenter;
import com.qsd.jmwh.module.home.user.activity.AuthCenterActivity;
import com.qsd.jmwh.module.home.user.bean.MineRadioListBean;
import com.qsd.jmwh.module.home.user.dialog.GetContactInfoDialog;
import com.qsd.jmwh.module.register.ToByVipActivity;
import com.qsd.jmwh.view.CircleImageView;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.ui.DelayClickTextView;

import java.util.List;

@SuppressLint("CheckResult") public class MineRadilLoveUserGvAdapter extends BaseAdapter {
  private List<MineRadioListBean.CdoListBean.CdoApplyBean> list;
  private Context context;
  public static boolean vip;
  public static int nAuthType;

  public MineRadilLoveUserGvAdapter(List<MineRadioListBean.CdoListBean.CdoApplyBean> list,
      boolean vip, int nAuthType, Context context) {
    this.list = list;
    this.context = context;
    this.vip = vip;
    this.nAuthType = nAuthType;
  }

  @Override public int getCount() {
    return list.size();
  }

  @Override public Object getItem(int i) {
    return null;
  }

  @Override public long getItemId(int i) {
    return 0;
  }

  @Override public View getView(int i, View view, ViewGroup viewGroup) {
    ViewHolder holder = null;
    if (view == null) {
      view = View.inflate(context, R.layout.item_apply_user, null);
      holder = new ViewHolder();
      holder.iv_headimg = view.findViewById(R.id.iv_headimg);
      holder.gv_pic = view.findViewById(R.id.gv_pic);
      holder.tv_name = view.findViewById(R.id.tv_name);
      holder.tv_time = view.findViewById(R.id.tv_time);
      holder.tv_get = view.findViewById(R.id.tv_get);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }

    holder.tv_name.setText(list.get(i).sNickName);
    Glide.with(context).load(list.get(i).sUserHeadPic).into(holder.iv_headimg);
    Glide.with(context).load(list.get(i).sContent).into(holder.gv_pic);
    holder.tv_get.setOnClickListener(view1 -> {
      if (UserProfile.getInstance().getSex() == 1) {
        if (vip) {
          getSubViewCount(list.get(i));
        } else {
          SelectHintPop hint = new SelectHintPop(context);
          hint.setTitle("提示");
          hint.setMessage("开通会员才能查看联系方式哦");
          hint.setSingleButton("去开通",
              v -> LauncherHelper.from(context).startActivity(ToByVipActivity.class));
          hint.setBottomButton("取消", v13 -> hint.dismiss());
          hint.showPopupWindow();
        }
      } else {
        if (nAuthType == 0) {
          SelectHintPop hint = new SelectHintPop(context);
          hint.setTitle("提示");
          hint.setMessage("女士认证后才能查看联系方式哦");
          hint.setSingleButton("去认证",
              v -> LauncherHelper.from(context).startActivity(AuthCenterActivity.class));
          hint.setBottomButton("取消", v13 -> hint.dismiss());
          hint.showPopupWindow();
        } else if (nAuthType == 3) {
          showInfoDialog(list.get(i).lUserId);
        }
      }
    });
    holder.iv_headimg.setOnClickListener(v -> {
      LauncherHelper.from(context)
          .startActivity(
              LookUserInfoActivity.getIntent(context, list.get(i).lUserId, list.get(i).lUserId, 2));
    });
    holder.tv_get.setText(UserProfile.getInstance().getSex() == 1 ? "联系她" : "联系他");

    return view;
  }

  class ViewHolder {
    CircleImageView iv_headimg;
    DelayClickTextView tv_name, tv_time, tv_get;
    ImageView gv_pic;
  }

  private void getSubViewCount(MineRadioListBean.CdoListBean.CdoApplyBean cdoApplyBean) {
    XHttpProxy.proxy(OtherApiServices.class)
        .getSubViewCount()
        .subscribeWith(new TipRequestSubscriber<SubViewCount>() {
          @Override protected void onSuccess(SubViewCount count) {
            boolean free = count.nSurContactViewCount > 0;
            SelectHintPop hint = new SelectHintPop(context);
            hint.setTitle("联系" + cdoApplyBean.sNickName)
                .setMessage(free ? "您还有" + count.nSurContactViewCount + "次查看联系方式机会" : "您的免费查看次数已上线")
                .setSingleButton(free ? "确定" : "付费查看和私聊 (" + count.dContactVal + "假面币)", v -> {
                  if (free) {
                    addBrowsingHis(cdoApplyBean.lUserId, 0, 0, 5, () -> {
                      showInfoDialog(cdoApplyBean.lUserId);
                    });
                  } else {
                    buyContactPay(cdoApplyBean.lUserId, count.dContactVal);
                  }
                  hint.dismiss();
                })
                .setBottomButton("取消", v13 -> hint.dismiss())
                .showPopupWindow();
          }
        });
  }

  public void addBrowsingHis(int lUserId, int lBrowseInfoId, int nPayType, int nBrowseInfType,
      LookUserInfoPresenter.ConsumptionListener listener) {
    XHttpProxy.proxy(OtherApiServices.class)
        .addBrowsingHis(lUserId, lBrowseInfoId, nPayType, nBrowseInfType)
        .subscribeWith(new TipRequestSubscriber<Object>() {
          @Override protected void onSuccess(Object o) {
            listener.consumption();
          }
        });
  }

  private void showInfoDialog(int userId) {
    GetContactInfoDialog dialog = new GetContactInfoDialog(context, userId);
    dialog.showPopupWindow();
  }

  private void buyContactPay(int lBuyOtherUserId, int count) {
    SelectHintPop hint = new SelectHintPop(context);
    hint.setTitle("温馨提示").setMessage("确认支付").setPositiveButton("确定", v1 -> {
      XHttpProxy.proxy(OtherApiServices.class)
          .getBuyContactPaySign(lBuyOtherUserId, 5, count)
          .subscribeWith(new TipRequestSubscriber<Object>() {
            @Override protected void onSuccess(Object o) {
              showInfoDialog(lBuyOtherUserId);
            }
          });
      hint.dismiss();
    }).setNegativeButton("取消", v12 -> hint.dismiss()).showPopupWindow();
  }
}
