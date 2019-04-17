package com.qsd.jmwh.module.register.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.register.bean.ProjectBean;

import java.util.List;

public class DateRangeProjectAdapter extends BaseQuickAdapter<ProjectBean, BaseViewHolder> {

    public DateRangeProjectAdapter(int layoutResId, @Nullable List<ProjectBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectBean item) {
        helper.setText(R.id.project_name,item.name);
        helper.getView(R.id.select_button).setSelected(item.selected);
        helper.getView(R.id.project_name).setSelected(item.selected);
    }
}
