package com.qsd.jmwh.module.home.user.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.user.bean.UserCenterMyInfo;
import com.yu.common.utils.ImageLoader;

import java.util.List;

public class DestroyPhotoTag extends BaseQuickAdapter<UserCenterMyInfo.CdoimgListBean, BaseViewHolder> {

    public DestroyPhotoTag(int layoutResId, @Nullable List<UserCenterMyInfo.CdoimgListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, UserCenterMyInfo.CdoimgListBean item) {
        helper.setVisible(R.id.destroy_tag, item.nFileType == 1);
        ImageView imageView = helper.getView(R.id.destroy_img);
        ImageLoader.loadCenterCrop(imageView.getContext(), item.sFileUrl, imageView);
    }
}
