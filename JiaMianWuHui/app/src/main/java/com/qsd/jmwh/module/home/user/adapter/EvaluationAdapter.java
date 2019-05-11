package com.qsd.jmwh.module.home.user.adapter;

import android.content.Context;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseHolder;
import com.qsd.jmwh.base.BasicAdapter;
import com.qsd.jmwh.module.home.user.bean.EvaluationBean;

import java.util.ArrayList;

public class EvaluationAdapter extends BasicAdapter<EvaluationBean.CdoListBean> {
    public EvaluationAdapter(ArrayList<EvaluationBean.CdoListBean> list) {
        super(list);
    }

    @Override
    protected BaseHolder<EvaluationBean.CdoListBean> getHolder(Context context) {
        return new BaseHolder<EvaluationBean.CdoListBean>(context,R.layout.item_evaluation_layout) {
            @Override
            public void bindData(EvaluationBean.CdoListBean data) {
                TextView count = findViewId(R.id.count);
                TextView evaluation = findViewId(R.id.evaluation);
                count.setText(data.nValue);
                evaluation.setText(data.sName);
                count.setSelected(data.selected);
                count.setOnClickListener(v -> {
                    data.selected = !data.selected;
                    notifyDataSetChanged();
                });
            }
        };
    }
}