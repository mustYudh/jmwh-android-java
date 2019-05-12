package com.qsd.jmwh.module.home.park.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseHolder;
import com.qsd.jmwh.base.BasicAdapter;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.activity.LookPhotoActivity;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.utils.ImageLoader;

import java.util.ArrayList;

public class UserPhotoAdapter extends BasicAdapter<OtherUserInfoBean.CdoFileListDataBean> {
    private boolean isOpenAll = true;
    private boolean isVip;
    private int userId;

    public UserPhotoAdapter(ArrayList<OtherUserInfoBean.CdoFileListDataBean> list, boolean isOpenAll, boolean isVip, int userId) {
        super(list);
        this.isOpenAll = isOpenAll;
        this.isVip = isVip;
        this.userId = userId;
    }

    @Override
    protected BaseHolder<OtherUserInfoBean.CdoFileListDataBean> getHolder(Context context) {
        return new BaseHolder<OtherUserInfoBean.CdoFileListDataBean>(context, R.layout.item_user_photo_layout) {
            @Override
            public void bindData(OtherUserInfoBean.CdoFileListDataBean data) {
                ImageView userPic = findViewId(R.id.user_img);
                ImageView photoBg = findViewId(R.id.photo_bg);
                TextView hint = findViewId(R.id.image_hint);
                if (userId != UserProfile.getInstance().getAppAccount()) {

                    if (isOpenAll) {
                        if (data.nFileType == 0) {
                            photoBg.setVisibility(View.GONE);
                            ImageLoader.loadCenterCrop(userPic.getContext(), data.sFileUrl, userPic);
                        } else if (data.nFileType == 1) {
                            photoBg.setVisibility(View.VISIBLE);
                            ImageLoader.blurTransformation(userPic.getContext(), data.sFileUrl, userPic);
                            if (data.bView == 0) {
                                photoBg.setImageResource(R.drawable.ic_destroy_photo);
                                hint.setText("照片已焚毁");
                            } else {
                                photoBg.setImageResource(R.drawable.ic_destroy_img_bg);
                                hint.setText("阅后即焚\n照片");
                            }

                        }
                        if (data.nFileType == 2) {
                            photoBg.setVisibility(View.VISIBLE);
                            ImageLoader.blurTransformation(userPic.getContext(), data.sFileUrl, userPic);
                            if (data.bView == 0) {
                                photoBg.setImageResource(R.drawable.ic_destroy_photo);
                                hint.setText("照片已焚毁");
                            } else {
                                photoBg.setImageResource(R.drawable.ic_red_bag_bg);
                                hint.setText("阅后即焚的\n红包照片");
                            }

                        }
                        userPic.setOnClickListener(v -> LauncherHelper.from(context).startActivity(LookPhotoActivity.getIntent(context, data, isVip, userId)));
                    } else {
                        ImageLoader.blurTransformation(userPic.getContext(), data.sFileUrl, userPic);
                    }
                } else {
                    photoBg.setVisibility(View.GONE);
                    ImageLoader.loadCenterCrop(userPic.getContext(), data.sFileUrl, userPic);
                }

            }
        };
    }
}
