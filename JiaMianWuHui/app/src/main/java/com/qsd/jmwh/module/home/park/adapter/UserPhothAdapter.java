package com.qsd.jmwh.module.home.park.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseHolder;
import com.qsd.jmwh.base.BasicAdapter;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.yu.common.utils.ImageLoader;

import java.util.ArrayList;

public class UserPhothAdapter extends BasicAdapter<OtherUserInfoBean.CdoFileListDataBean> {

    public UserPhothAdapter(ArrayList<OtherUserInfoBean.CdoFileListDataBean> list) {
        super(list);
    }

    @Override
    protected BaseHolder<OtherUserInfoBean.CdoFileListDataBean> getHolder(Context context) {
        return new BaseHolder<OtherUserInfoBean.CdoFileListDataBean>(context, R.layout.item_user_photo_layout) {
            @Override
            public void bindData(OtherUserInfoBean.CdoFileListDataBean data) {
                ImageView userPic = findViewId(R.id.user_img);
                ImageLoader.loadCenterCrop(userPic.getContext(), data.sFileUrl, userPic);
            }
        };
    }
}
