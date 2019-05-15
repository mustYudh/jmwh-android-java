package com.qsd.jmwh.module.home.radio.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qsd.jmwh.R;
import com.qsd.jmwh.utils.ScreentUtils;
import com.yu.common.utils.ImageLoader;

import java.util.List;

public class HomeRadioPicGvAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;


    public HomeRadioPicGvAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_pic_view, null);
            holder = new ViewHolder();
            holder.iv_pic = view.findViewById(R.id.thumb_iv);
            holder.ll_root = view.findViewById(R.id.ll_root);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        int width = ScreentUtils.getScreenWidth(context);//固定宽度
        //宽度固定,然后根据原始宽高比得到此固定宽度需要的高度
        ViewGroup.LayoutParams para = holder.iv_pic.getLayoutParams();
        para.width = (int) (width / 3);
        para.height = (int) (width / 3);
        holder.iv_pic.setLayoutParams(para);
        ImageLoader.loadCenterCrop(context, list.get(i), holder.iv_pic, R.drawable.zhanwei);
        return view;
    }

    class ViewHolder {
        ImageView iv_pic;
        LinearLayout ll_root;
    }
}
