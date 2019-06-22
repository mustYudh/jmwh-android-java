package com.qsd.jmwh.module.home.user.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import com.qsd.jmwh.R;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.adapter.EvaluationAdapter;
import com.qsd.jmwh.module.home.user.bean.EvaluationBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.ImageLoader;
import com.yu.common.windown.BasePopupWindow;

public class EvaluationDialog extends BasePopupWindow {

  private int userID;
  private int count1;
  private int count2;
  private int count3;
  private int count4;
  private int count5;
  private int count6;
  private int count7;
  private int count8;

  public EvaluationDialog(Context context, String header, String sNickName, int userID) {
    super(context, View.inflate(context, R.layout.user_evaluation_dialog, null),
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    ImageLoader.loadCenterCrop(context, header, bindView(R.id.header));
    TextView nickName = bindView(R.id.sNickName);
    nickName.setText(sNickName);
    GridView list = bindView(R.id.list);
    this.userID = userID;
    initEvaluation(userID, list);
    initListener();
  }

  @SuppressLint("CheckResult") private void initEvaluation(int userID, GridView list) {
    XHttpProxy.proxy(OtherApiServices.class)
        .getEvaluation(userID)
        .subscribeWith(new NoTipRequestSubscriber<EvaluationBean>() {
          @Override protected void onSuccess(EvaluationBean evaluationBean) {
            EvaluationAdapter adapter = new EvaluationAdapter(evaluationBean.cdoList, userID);
            list.setAdapter(adapter);
            adapter.setOnEvaluationClickedListener((selected, position) -> {
              int result = selected ? 1 : 0;
              switch (position) {
                case 0:
                  count1 = result;
                  break;
                case 1:
                  count2 = result;
                  break;
                case 2:
                  count3 = result;
                  break;
                case 3:
                  count4 = result;
                  break;
                case 4:
                  count5 = result;
                  break;
                case 5:
                  count6 = result;
                  break;
                case 6:
                  count7 = result;
                  break;
                case 7:
                  count8 = result;
                  break;
                default:
              }
            });
          }
        });
    bindView(R.id.evaluation, userID != UserProfile.getInstance().getAppAccount());
  }

  @SuppressLint("CheckResult") private void initListener() {
    bindView(R.id.close, v -> dismiss());
    bindView(R.id.evaluation, v -> XHttpProxy.proxy(OtherApiServices.class)
        .updateValiate(userID, count1, count2, count3, count4, count5, count6, count7, count8)
        .subscribeWith(new TipRequestSubscriber<Object>() {
          @Override protected void onSuccess(Object o) {
            ToastUtils.show("评价成功");
            dismiss();
          }

          @Override protected void onError(ApiException apiException) {
            super.onError(apiException);
            dismiss();
          }
        }));
  }

  @Override protected View getBackgroundShadow() {
    return null;
  }

  @Override protected View getContainer() {
    return null;
  }
}
