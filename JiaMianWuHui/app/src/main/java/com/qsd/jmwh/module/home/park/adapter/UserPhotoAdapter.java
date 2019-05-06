package com.qsd.jmwh.module.home.park.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseHolder;
import com.qsd.jmwh.base.BasicAdapter;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.qsd.jmwh.utils.FastBlurUtil;
import com.yu.common.utils.ImageLoader;

import java.util.ArrayList;

public class UserPhotoAdapter extends BasicAdapter<OtherUserInfoBean.CdoFileListDataBean> {

    public UserPhotoAdapter(ArrayList<OtherUserInfoBean.CdoFileListDataBean> list) {
        super(list);
    }

    @Override
    protected BaseHolder<OtherUserInfoBean.CdoFileListDataBean> getHolder(Context context) {
        return new BaseHolder<OtherUserInfoBean.CdoFileListDataBean>(context, R.layout.item_user_photo_layout) {
            @Override
            public void bindData(OtherUserInfoBean.CdoFileListDataBean data) {
                ImageView userPic = findViewId(R.id.user_img);
//                ImageLoader.loadCenterCrop(userPic.getContext(), data.sFileUrl, userPic);
//                userPic.setImageBitmap(FastBlurUtil.doBlur(ImageLoader.getBitmap(userPic.getContext(), data.sFileUrl), 10, false));
                ImageLoader.getBitmap(userPic.getContext(), data.sFileUrl, new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap bitmap = FastBlurUtil.doBlur(resource, 10, false);
                        userPic.setImageBitmap(FastBlurUtil.doBlur(bitmap, 100, true));
                    }
                });
            }
        };
    }
}
