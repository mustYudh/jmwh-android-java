package com.qsd.jmwh.module.home.user.adapter;

//public class DestroyPhotoTag extends BaseQuickAdapter<UserCenterMyInfo.CdoimgListBean, BaseViewHolder> {
//
//    public DestroyPhotoTag(int layoutResId, @Nullable List<UserCenterMyInfo.CdoimgListBean> data) {
//        super(layoutResId, data);
//    }
//
//
//    @Override
//    protected void convert(BaseViewHolder helper, UserCenterMyInfo.CdoimgListBean item) {
//        helper.setVisible(R.id.destroy_tag, item.nFileType == 1);
//        ImageView imageView = helper.getView(R.id.destroy_img);
//        ImageLoader.loadCenterCrop(imageView.getContext(), item.sFileUrl, imageView);
//    }
//}


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseHolder;
import com.qsd.jmwh.base.BasicAdapter;
import com.qsd.jmwh.module.home.user.bean.UserCenterMyInfo;
import com.yu.common.utils.ImageLoader;

import java.util.ArrayList;

public class DestroyPhotoTag extends BasicAdapter<UserCenterMyInfo.CdoimgListBean> {
    private AddImageListener addImageListener;
    public interface AddImageListener {
        void clickAdd();
    }

    public void setAddImageListener(AddImageListener addImageListener) {
        this.addImageListener = addImageListener;
    }

    public DestroyPhotoTag(ArrayList<UserCenterMyInfo.CdoimgListBean> list) {
        super(list);
    }

    @Override
    protected BaseHolder<UserCenterMyInfo.CdoimgListBean> getHolder(Context context) {
        return new BaseHolder<UserCenterMyInfo.CdoimgListBean>(context,R.layout.item_user_center_img) {
            @Override
            public void bindData(UserCenterMyInfo.CdoimgListBean item) {
                ImageView imageView = findViewId(R.id.destroy_img);
                if (item.last) {
                    imageView.setImageResource(R.drawable.ic_add_button);
                    imageView.setOnClickListener(v -> {
                        if (addImageListener != null) {
                            addImageListener.clickAdd();
                        }
                    });
                    findViewId(R.id.destroy_tag).setVisibility(View.GONE);
                } else {
                    findViewId(R.id.destroy_tag).setVisibility(item.nFileType == 1 ? View.VISIBLE : View.GONE);
                    ImageLoader.loadCenterCrop(imageView.getContext(), item.sFileUrl, imageView);
                }
            }
        };
    }
}