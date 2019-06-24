package com.qsd.jmwh.module.home.message.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.message.bean.SystemMessageBean;

/**
 * @author yudneghao
 * @date 2019-06-24
 */
public class MessageDetailAdapter
    extends BaseQuickAdapter<SystemMessageBean.CdoListBean, BaseViewHolder> {
  public MessageDetailAdapter() {
    super(R.layout.item_message_detail_layout, null);
  }

  @Override protected void convert(BaseViewHolder helper, SystemMessageBean.CdoListBean item) {
    helper.setText(R.id.content,item.sContent).setText(R.id.time,item.dCreateTime);
  }
}
