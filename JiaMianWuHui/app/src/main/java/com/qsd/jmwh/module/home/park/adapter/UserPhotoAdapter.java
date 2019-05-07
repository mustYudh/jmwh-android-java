package com.qsd.jmwh.module.home.park.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseHolder;
import com.qsd.jmwh.base.BasicAdapter;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
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
                TextView hint = findViewId(R.id.image_hint);
                if (data.nFileType == 0) {
                    ImageLoader.loadCenterCrop(userPic.getContext(), data.sFileUrl,userPic);
                } else if (data.nFileType == 1) {
                    ImageLoader.blurTransformation(userPic.getContext(), data.sFileUrl,userPic);
                    hint.setText("阅后即焚\n照片");
                }if (data.nFileType == 2) {
                    ImageLoader.blurTransformation(userPic.getContext(), data.sFileUrl,userPic);
                    hint.setText("阅后即焚的\n红包照片");
                }

            }
        };
    }
}
