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
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.ImageLoader;

import java.util.ArrayList;

public class UserPhotoAdapter extends BasicAdapter<OtherUserInfoBean.CdoFileListDataBean> {
    private boolean isOpenAll = true;
    private boolean isVip;
    private int userId;
    private int authType;

    public UserPhotoAdapter(ArrayList<OtherUserInfoBean.CdoFileListDataBean> list, boolean isOpenAll, boolean isVip, int userId,int authType) {
        super(list);
        this.isOpenAll = isOpenAll;
        this.isVip = isVip;
        this.userId = userId;
        this.authType = authType;
    }

    @Override
    protected BaseHolder<OtherUserInfoBean.CdoFileListDataBean> getHolder(Context context,int position) {
        return new BaseHolder<OtherUserInfoBean.CdoFileListDataBean>(context, R.layout.item_user_photo_layout) {
            @Override
            public void bindData(OtherUserInfoBean.CdoFileListDataBean data) {
                ImageView userPic = findViewId(R.id.user_img);
                ImageView photoBg = findViewId(R.id.photo_bg);
                TextView hint = findViewId(R.id.image_hint);
                boolean canClick = true;
                if (userId != UserProfile.getInstance().getUserId()) {
                    if (isOpenAll) {
                        //普通照片
                        if (data.nFileType == 0) {
                            //展示照片
                            photoBg.setVisibility(View.GONE);
                            ImageLoader.loadCenterCrop(userPic.getContext(), data.sFileUrl, userPic);
                            //阅后即焚照片
                        } else if (data.nFileType == 1) {
                            photoBg.setVisibility(View.VISIBLE);
                            ImageLoader.blurTransformation(userPic.getContext(), data.sFileUrl, userPic);
                            if (data.bView == 0) {
                                //已被焚毁不展示
                                photoBg.setImageResource(R.drawable.ic_destroy_photo);
                                hint.setText("照片已焚毁");
                                canClick = false;
                            } else {
                                //为被焚毁需要
                                photoBg.setImageResource(R.drawable.ic_destroy_img_bg);
                                hint.setText("阅后即焚\n照片");
                            }

                        } else if (data.nFileType == 2) {
                            photoBg.setVisibility(View.VISIBLE);
                            if (data.bView == 0) {
                                ImageLoader.loadCenterCrop(userPic.getContext(), data.sFileUrl, userPic);
                            } else {
                                ImageLoader.blurTransformation(userPic.getContext(), data.sFileUrl, userPic);
                                photoBg.setImageResource(R.drawable.ic_red_bag_bg);
                                hint.setText("红包照片");
                            }
                        } else if (data.nFileType == 3) {
                            photoBg.setVisibility(View.VISIBLE);
                            ImageLoader.blurTransformation(userPic.getContext(), data.sFileUrl, userPic);
                            if (data.bView == 0) {
                                photoBg.setImageResource(R.drawable.ic_destroy_photo);
                                hint.setText("照片已焚毁");
                                canClick = false;
                            } else {
                                photoBg.setImageResource(R.drawable.ic_red_bag_bg);
                                hint.setText("阅后即焚的\n红包照片");
                            }
                        }
                        boolean finalCanClick = canClick;
                        userPic.setOnClickListener(v -> {
                            if (finalCanClick) {
                                LauncherHelper.from(context).startActivity(LookPhotoActivity.getIntent(context, data, isVip, userId, false,authType));
                            } else {
                                ToastUtils.show("照片已焚毁");
                            }
                        });
                    } else {
                        ImageLoader.blurTransformation(userPic.getContext(), data.sFileUrl, userPic);
                    }

                } else {
                    photoBg.setVisibility(View.GONE);
                    ImageLoader.loadCenterCrop(userPic.getContext(), data.sFileUrl, userPic);
                    userPic.setOnClickListener(v -> LauncherHelper.from(context).startActivity(LookPhotoActivity.getIntent(context, data, isVip, userId, true,authType)));
                }

            }
        };
    }
}
