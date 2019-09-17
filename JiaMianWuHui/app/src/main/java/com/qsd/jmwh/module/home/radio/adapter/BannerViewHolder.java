package com.qsd.jmwh.module.home.radio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.radio.bean.HomeBannerBean;
import com.qsd.jmwh.web.WebViewActivity;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.utils.ImageLoader;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * Created by mks on 2019/4/1.
 */

public class BannerViewHolder implements MZViewHolder<HomeBannerBean.CdoListBean> {
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局P2PMessageInfo
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        mImageView = (ImageView) view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, HomeBannerBean.CdoListBean data) {
        if (data != null) {
            // 数据绑定
            ImageLoader.loadCenterCrop(context, data.sImg, mImageView, R.drawable.zhanwei);
            mImageView.setOnClickListener(view -> {
                if (data.sTargetUrl.startsWith("http")) {
                    LauncherHelper.from(context).startActivity(WebViewActivity.callIntent(context, data.sInfo, data.sTargetUrl));
                }
            });
        }
    }
}
