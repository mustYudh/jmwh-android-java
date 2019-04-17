package com.qsd.jmwh.module.register.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qsd.jmwh.R;
import com.yu.common.base.BaseRecyclerAdapter;

/**
 * @author yudneghao
 * @date 2019/4/17
 */
public class RangeProvinceAdapter extends BaseRecyclerAdapter<Range> {
  public RangeProvinceAdapter(Context context) {
    super(context);
  }

  @Override protected View newView(Context context, ViewGroup viewGroup, int viewType) {
    return LayoutInflater.from(context).inflate(R.layout.item_range_province,viewGroup,false);
  }

  @Override protected void bindView(RecyclerView.ViewHolder holder, int pos, Range range) {

  }
}
