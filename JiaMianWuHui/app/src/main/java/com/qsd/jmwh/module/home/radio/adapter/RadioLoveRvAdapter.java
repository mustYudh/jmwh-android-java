package com.qsd.jmwh.module.home.radio.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;

import java.util.HashMap;
import java.util.List;

public class RadioLoveRvAdapter extends BaseQuickAdapter<GetRadioConfigListBean.CdoListBean, BaseViewHolder> {
    private Context context;
    public static HashMap<Integer, Boolean> isSelected;
    private List<GetRadioConfigListBean.CdoListBean> data;

    public RadioLoveRvAdapter(int layoutResId, @Nullable List<GetRadioConfigListBean.CdoListBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
        this.data = data;
        isSelected = new HashMap<Integer, Boolean>();
        // 初始化数据
        initDate();
    }

    // 初始化isSelected的数据
    public void initDate() {
        for (int i = 0; i < data.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    @Override
    protected void convert(BaseViewHolder helper, GetRadioConfigListBean.CdoListBean item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);

        //按钮点击选择事件
        helper.getView(R.id.ll_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView iv_check = view.findViewById(R.id.iv_icon);
                if (!isSelected.get(helper.getLayoutPosition())) {
                    isSelected.put(helper.getLayoutPosition(), true);
                    iv_check.setImageResource(R.drawable.ic_button_selected);
                } else {
                    isSelected.put(helper.getLayoutPosition(), false);
                    iv_check.setImageResource(R.drawable.ic_button_unselected);
                }
            }
        });


        if (isSelected.get(helper.getLayoutPosition())) {
            iv_icon.setImageResource(R.drawable.ic_button_selected);
            helper.setTextColor(R.id.tv_type, context.getResources().getColor(R.color.app_main_bg_color));
        } else {
            iv_icon.setImageResource(R.drawable.ic_button_unselected);
            helper.setTextColor(R.id.tv_type, context.getResources().getColor(R.color.color_333333));
        }
        helper.setText(R.id.tv_type, item.sDatingConfigName);
    }
}
