package com.qsd.jmwh.module.home.user.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.module.home.user.adapter.EvaluationAdapter;
import com.qsd.jmwh.module.home.user.bean.EvaluationBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.utils.ImageLoader;
import com.yu.common.windown.BasePopupWindow;
public class EvaluationDialog extends BasePopupWindow {

    public EvaluationDialog(Context context,String header,String sNickName,int userID) {
        super(context, View.inflate(context, R.layout.user_evaluation_dialog, null),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageLoader.loadCenterCrop(context, header, bindView(R.id.header));
        TextView nickName = bindView(R.id.sNickName);
        nickName.setText(sNickName);
        GridView list = bindView(R.id.list);
        initEvaluation(userID,list);
        initListener();

    }

    @SuppressLint("CheckResult")
    private void initEvaluation(int userID,GridView list) {
        XHttpProxy.proxy(OtherApiServices.class)
                .getEvaluation(userID)
                .subscribeWith(new NoTipRequestSubscriber<EvaluationBean>() {
                    @Override
                    protected void onSuccess(EvaluationBean evaluationBean) {
                        EvaluationAdapter adapter = new EvaluationAdapter(evaluationBean.cdoList);
                        list.setAdapter(adapter);
                    }
                });
    }

    private void initListener() {
        bindView(R.id.close, v -> dismiss());
    }

    @Override
    protected View getBackgroundShadow() {
        return null;
    }

    @Override
    protected View getContainer() {
        return null;
    }
}
