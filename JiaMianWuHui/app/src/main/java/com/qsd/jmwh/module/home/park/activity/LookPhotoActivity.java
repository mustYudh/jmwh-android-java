package com.qsd.jmwh.module.home.park.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.yu.common.utils.ImageLoader;

public class LookPhotoActivity extends BaseActivity {
    private final static String DATA = "data";

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.look_photo_layout);
    }

    public static Intent getIntent(Context context, OtherUserInfoBean.CdoFileListDataBean data) {
        Intent starter = new Intent(context, LookPhotoActivity.class);
        starter.putExtra(DATA, data);
        return starter;
    }

    @Override
    protected void loadData() {
        OtherUserInfoBean.CdoFileListDataBean data = (OtherUserInfoBean.CdoFileListDataBean) getIntent().getSerializableExtra(DATA);
        String url = data.sFileUrl;
        int type = data.nFileType;
        ImageView photo = bindView(R.id.photo);
        if (type == 0) {
            bindView(R.id.destroy_img_toot, false);
            ImageLoader.loadCenterCrop(getActivity(), url, photo);
        } else if (type == 1) {
            ImageView imageView = bindView(R.id.destroy_root_bg, true);
            imageView.setImageResource(R.drawable.ic_destroy_img_bg);
            ImageLoader.blurTransformation(getActivity(), url, photo, 20, 3);
            bindView(R.id.destroy_img_toot, true);
        } else if (type == 2) {
            ImageView imageView = bindView(R.id.destroy_root_bg, true);
            imageView.setImageResource(R.drawable.ic_red_bag_bg);
            ImageLoader.blurTransformation(getActivity(), url, photo, 20, 3);
            bindView(R.id.red_bag_root, true);
            bindView(R.id.pay_btn, true);
        }
        bindView(R.id.back, v -> onBackPressed());
    }
}
