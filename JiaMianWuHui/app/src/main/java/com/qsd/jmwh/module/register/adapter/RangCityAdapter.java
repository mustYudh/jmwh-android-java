package com.qsd.jmwh.module.register.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.register.bean.RangeData;
import java.util.List;

/**
 * @author yudneghao
 * @date 2019/4/17
 */
public class RangCityAdapter extends BaseQuickAdapter<RangeData.Range, BaseViewHolder> {

  public RangCityAdapter(int layoutResId, @Nullable List<RangeData.Range> data) {
    super(layoutResId, data);
  }

  @Override protected void convert(BaseViewHolder helper, RangeData.Range item) {
    helper.setText(R.id.city_name,item.sName);
    TextView textView = helper.getView(R.id.city_name);
    ImageView imageView = helper.getView(R.id.select_button);
    textView.setSelected(item.selected);
    imageView.setSelected(item.selected);
  }
}
