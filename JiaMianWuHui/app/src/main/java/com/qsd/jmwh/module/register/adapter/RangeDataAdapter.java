package com.qsd.jmwh.module.register.adapter;

import android.support.annotation.Nullable;
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
public class RangeDataAdapter extends BaseQuickAdapter<RangeData.Range, BaseViewHolder> {
  public RangeDataAdapter(int layoutResId, @Nullable List<RangeData.Range> data) {
    super(layoutResId, data);
  }

  @Override protected void convert(BaseViewHolder helper, RangeData.Range item) {
    TextView textView = helper.getView(R.id.province_name);
    helper.setText(R.id.province_name, item.sName);
    textView.setSelected(item.selected);
    if (item.selected) {
      textView.setBackgroundColor(
          textView.getContext().getResources().getColor(R.color.app_main_bg_color));
    } else {
      textView.setBackgroundColor(textView.getContext().getResources().getColor(R.color.white));
    }
  }
}
