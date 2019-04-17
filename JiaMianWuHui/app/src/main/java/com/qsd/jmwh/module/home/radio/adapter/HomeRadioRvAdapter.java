package com.qsd.jmwh.module.home.radio.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.radio.bean.LocalHomeRadioListBean;
import com.qsd.jmwh.view.CircleImageView;
import com.qsd.jmwh.view.NoSlidingGridView;

import java.util.List;

public class HomeRadioRvAdapter extends BaseMultiItemQuickAdapter<LocalHomeRadioListBean, BaseViewHolder> {
    private Context context;

    public HomeRadioRvAdapter(List<LocalHomeRadioListBean> data, Context context) {
        super(data);
        addItemType(0, R.layout.item_home_radio_title);
        addItemType(1, R.layout.item_home_radio_label);
        addItemType(2, R.layout.item_home_radio_pic);
        addItemType(3, R.layout.item_home_radio_bottom);

        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalHomeRadioListBean item) {
        switch (item.itemType) {
            case 0:
                CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
                Glide.with(context).load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1319625412,2949035306&fm=26&gp=0.jpg").into(iv_headimg);
                helper.setText(R.id.tv_user_name, item.userName);
                helper.setText(R.id.tv_ctime, item.cTime);
                break;
            case 1:
                RecyclerView rv_label = helper.getView(R.id.rv_label);
                HomeRadioLacelRvAdapter labelAdapter = new HomeRadioLacelRvAdapter(R.layout.item_label_view, item.label, context);
                rv_label.setLayoutManager(new LinearLayoutManager(context));
                rv_label.setAdapter(labelAdapter);
                break;
            case 2:
                NoSlidingGridView gv_pic = helper.getView(R.id.gv_pic);
                HomeRadioPicGvAdapter picAdapter = new HomeRadioPicGvAdapter(context, item.picList);
                gv_pic.setAdapter(picAdapter);
                break;
            case 3:
                helper.setText(R.id.tv_comment, "评论(" + item.count_num + ")");
                break;
        }
    }
}
