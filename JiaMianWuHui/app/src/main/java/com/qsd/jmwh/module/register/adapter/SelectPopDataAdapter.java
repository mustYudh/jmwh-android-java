package com.qsd.jmwh.module.register.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.register.bean.SelectData;
import java.util.List;

/**
 * @author yudneghao
 * @date 2019/4/17
 */
public class SelectPopDataAdapter extends BaseQuickAdapter<SelectData, BaseViewHolder> {

  public SelectPopDataAdapter(int layoutResId, @Nullable List<SelectData> data) {
    super(layoutResId, data);
  }

  @Override protected void convert(BaseViewHolder helper, SelectData item) {
    helper.setText(R.id.info, item.value);
  }
}
