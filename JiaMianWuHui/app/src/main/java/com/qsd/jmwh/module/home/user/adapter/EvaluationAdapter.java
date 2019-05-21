package com.qsd.jmwh.module.home.user.adapter;

import android.content.Context;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseHolder;
import com.qsd.jmwh.base.BasicAdapter;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.user.bean.EvaluationBean;

import java.util.ArrayList;

public class EvaluationAdapter extends BasicAdapter<EvaluationBean.CdoListBean> {
    private int userID;

    public EvaluationAdapter(ArrayList<EvaluationBean.CdoListBean> list, int userID) {
        super(list);
        this.userID = userID;
    }

    @Override
    protected BaseHolder<EvaluationBean.CdoListBean> getHolder(Context context) {
        return new BaseHolder<EvaluationBean.CdoListBean>(context, R.layout.item_evaluation_layout) {
            @Override
            public void bindData(EvaluationBean.CdoListBean data) {
                TextView count = findViewId(R.id.count);
                TextView evaluation = findViewId(R.id.evaluation);
                count.setText(data.nValue);
                evaluation.setText(data.sName);
                count.setSelected(data.selected);
                if (userID != UserProfile.getInstance().getAppAccount()) {
                    count.setOnClickListener(v -> {
                        data.selected = !data.selected;
                        int countValue = Integer.parseInt(data.nValue);
                        data.nValue = data.selected ?  (countValue + 1) + "" : (countValue - 1) + "";
                        notifyDataSetChanged();
                    });
                }
            }
        };
    }
}
