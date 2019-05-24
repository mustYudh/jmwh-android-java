package com.qsd.jmwh.module.home.park.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.register.bean.RangeData;

import java.util.List;

public class PackCityRvAdapter extends BaseQuickAdapter<RangeData.Range, BaseViewHolder> {
    private Context context;

    public PackCityRvAdapter(int layoutResId, @Nullable List<RangeData.Range> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RangeData.Range item) {
        helper.setText(R.id.tv_city, item.sName);

        helper.getView(R.id.ll_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemCityClickListener != null){
                    onItemCityClickListener.setOnItemCityClick(item.lId,item.sName);
                }
            }
        });
    }

    public interface OnItemCityClickListener {
        void setOnItemCityClick(int id,String name);
    }

    OnItemCityClickListener onItemCityClickListener;

    public void setOnItemCityClickListener(OnItemCityClickListener onItemCityClickListener) {
        this.onItemCityClickListener = onItemCityClickListener;
    }
}
