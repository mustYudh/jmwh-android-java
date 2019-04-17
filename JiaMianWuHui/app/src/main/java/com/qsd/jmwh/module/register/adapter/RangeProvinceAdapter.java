package com.qsd.jmwh.module.register.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.register.bean.RangeData;
import java.util.List;

/**
 * @author yudneghao
 * @date 2019/4/17
 */
public class RangeProvinceAdapter extends BaseQuickAdapter<RangeData.Range, BaseViewHolder> {
  public RangeProvinceAdapter(int layoutResId, @Nullable List<RangeData.Range> data) {
    super(layoutResId, data);
  }

  @Override protected void convert(BaseViewHolder helper, RangeData.Range item) {
    helper.setText(R.id.province_name,item.sName);
  }
}
