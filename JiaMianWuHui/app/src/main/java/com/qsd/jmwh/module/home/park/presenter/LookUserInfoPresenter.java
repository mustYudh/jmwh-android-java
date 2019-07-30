package com.qsd.jmwh.module.home.park.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.qsd.jmwh.module.home.park.bean.SubViewCount;
import com.qsd.jmwh.module.im.SessionHelper;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

@SuppressLint("CheckResult") public class LookUserInfoPresenter
    extends BaseViewPresenter<LookUserInfoViewer> {

  private boolean isOpen = false;

  public LookUserInfoPresenter(LookUserInfoViewer viewer) {
    super(viewer);
  }

  public void getUserInfo(int lUserId, double nLat, double nLng) {
    XHttpProxy.proxy(ApiServices.class)
        .getOtherUserInfo(lUserId, nLat, nLng)
        .subscribeWith(new TipRequestSubscriber<OtherUserInfoBean>() {
          @Override protected void onSuccess(OtherUserInfoBean userCenterInfo) {
            assert getViewer() != null;
            getViewer().setUserInfo(userCenterInfo);
          }
        });
  }

  public void buyGalleryPay(int lBuyOtherUserId, int nPayVal) {
    XHttpProxy.proxy(OtherApiServices.class)
        .buyGalleryPay(lBuyOtherUserId, 5, nPayVal)
        .subscribeWith(new TipRequestSubscriber<Object>() {
          @Override protected void onSuccess(Object info) {
            ToastUtils.show("解锁成功");
            assert getViewer() != null;
            getViewer().refreshData();
          }
        });
  }

  public void buyContactPay(int lBuyOtherUserId, int count,int type) {
    SelectHintPop hint = new SelectHintPop(getActivity());
    hint.setTitle("温馨提示").setMessage("确认支付").setPositiveButton("确定", v1 -> {
      XHttpProxy.proxy(OtherApiServices.class)
          .getBuyContactPaySign(lBuyOtherUserId, 5, count)
          .subscribeWith(new TipRequestSubscriber<Object>() {
            @Override protected void onSuccess(Object o) {
              assert getViewer() != null;
              getViewer().refreshData();
              getViewer().payToChat(type);
            }
          });
      hint.dismiss();
    }).setNegativeButton("取消", v12 -> hint.dismiss()).showPopupWindow();
  }

  public void getSubViewCount(int type) {
    XHttpProxy.proxy(OtherApiServices.class)
        .getSubViewCount()
        .subscribeWith(new TipRequestSubscriber<SubViewCount>() {
          @Override protected void onSuccess(SubViewCount count) {
            assert getViewer() != null;
            getViewer().getViewCount(count,type);
          }
        });
  }

  public void toChat(String sNickName, int lBuyOtherUserId,int type) {
    if (!isOpen) {
      XHttpProxy.proxy(OtherApiServices.class)
          .getSubViewCount()
          .subscribeWith(new TipRequestSubscriber<SubViewCount>() {
            @Override protected void onSuccess(SubViewCount count) {
              boolean free = count.nSurContactViewCount > 0;
              SelectHintPop hint = new SelectHintPop(getActivity());
              hint.setTitle("联系" + sNickName)
                  .setMessage(
                      free ? "您还有" + count.nSurContactViewCount + "次查看联系方式机会" : "您的免费查看次数已上线")
                  .setSingleButton(free ? "确定" : "付费查看和私聊 (" + count.dContactVal + "假面币)", v -> {
                    if (free) {
                      addBrowsingHis(lBuyOtherUserId, 0, 0, 5,
                          () -> {
                            SessionHelper.startP2PSession(getActivity(),
                                "im_" + lBuyOtherUserId);
                            getViewer().refreshData();
                            isOpen = true;
                          });
                    } else {
                      SelectHintPop payChat = new SelectHintPop(getActivity());
                      payChat.setTitle("温馨提示").setMessage("确认支付").setPositiveButton("确定", v1 -> {
                        XHttpProxy.proxy(OtherApiServices.class)
                            .getBuyContactPaySign(lBuyOtherUserId, 5, count.dContactVal)
                            .subscribeWith(new TipRequestSubscriber<Object>() {
                              @Override protected void onSuccess(Object o) {
                                assert getViewer() != null;
                                getViewer().refreshData();
                                getViewer().payToChat(type);
                              }
                            });
                        payChat.dismiss();
                      }).setNegativeButton("取消", v12 -> payChat.dismiss()).showPopupWindow();
                    }
                    hint.dismiss();
                  })
                  .setBottomButton("取消", v13 -> hint.dismiss())
                  .showPopupWindow();
            }
          });
    } else {
      SessionHelper.startP2PSession(getActivity(),
          "im_" + lBuyOtherUserId);
    }
  }

  public void addBrowsingHis(int lUserId, int lBrowseInfoId, int nPayType, int nBrowseInfType,
      ConsumptionListener listener) {
    XHttpProxy.proxy(OtherApiServices.class)
        .addBrowsingHis(lUserId, lBrowseInfoId, nPayType, nBrowseInfType)
        .subscribeWith(new TipRequestSubscriber<Object>() {
          @Override protected void onSuccess(Object o) {
            listener.consumption();
          }
        });
  }

  public interface ConsumptionListener {
    void consumption();
  }
}
