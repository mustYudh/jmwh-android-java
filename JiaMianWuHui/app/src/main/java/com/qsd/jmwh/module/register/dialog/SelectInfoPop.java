package com.qsd.jmwh.module.register.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.register.adapter.SelectPopDataAdapter;
import com.qsd.jmwh.module.register.bean.SelectData;
import com.yu.common.windown.BasePopupWindow;
import java.util.List;

/**
 * @author yudneghao
 * @date 2019/4/17
 */
public class SelectInfoPop extends BasePopupWindow {

  private DataSelectedListener mDataSelectedListener;

  public interface DataSelectedListener {
    void onSelected(SelectData selectData);
  }

  public void setoNDataSelectedListener(DataSelectedListener dataSelectedListener) {
    mDataSelectedListener = dataSelectedListener;
  }

  public SelectInfoPop(Context context) {
    super(context, LayoutInflater.from(context).inflate(R.layout.select_info_pop, null),
        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
  }

  public void setTitle(String titleName) {
    TextView title = bindView(R.id.title);
    if (!TextUtils.isEmpty(titleName) && title != null) {
      title.setText(titleName);
    }
  }

  public void setData(List<SelectData> selectData) {
    RecyclerView recyclerView = bindView(R.id.content_list);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    SelectPopDataAdapter adapter = new SelectPopDataAdapter(R.layout.item_select_data, selectData);
    recyclerView.setAdapter(adapter);
    adapter.setOnItemClickListener((adapter1, view, position) -> {
      if (mDataSelectedListener != null) {
        mDataSelectedListener.onSelected((SelectData) adapter1.getData().get(position));
      }
    });
  }

  @Override protected View getBackgroundShadow() {
    return findViewById(R.id.base_shadow);
  }

  @Override protected View getContainer() {
    return findViewById(R.id.base_container);
  }
}
