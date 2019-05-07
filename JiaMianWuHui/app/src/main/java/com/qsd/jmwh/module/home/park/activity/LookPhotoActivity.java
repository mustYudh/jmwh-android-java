package com.qsd.jmwh.module.home.park.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.yu.common.utils.ImageLoader;

public class LookPhotoActivity extends BaseActivity {
    private final static String URL = "url";
    private final static String TYPE = "type";

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.look_photo_layout);
    }

    public static Intent getIntent(Context context, String url, int type) {
        Intent starter = new Intent(context, LookPhotoActivity.class);
        starter.putExtra(URL, url);
        starter.putExtra(TYPE, type);
        return starter;
    }

    @Override
    protected void loadData() {
        String imageUrl = getIntent().getStringExtra(URL);
        int type = getIntent().getIntExtra(TYPE, 0);
        ImageView photo = bindView(R.id.photo);
        if (type == 0) {
            ImageLoader.loadCenterCrop(getActivity(), imageUrl, photo);
        } else if (type == 1) {
            bindView(R.id.destroy_img_toot,true);
            ImageLoader.blurTransformation(getActivity(), imageUrl, photo);
        } else if (type == 2) {
            bindView(R.id.destroy_img_toot,true);
            bindView(R.id.pay_btn,true);
            ImageLoader.blurTransformation(getActivity(), imageUrl, photo);
        }
        bindView(R.id.back, v -> onBackPressed());
    }
}
