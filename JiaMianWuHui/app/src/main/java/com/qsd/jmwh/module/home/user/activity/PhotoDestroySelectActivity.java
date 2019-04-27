package com.qsd.jmwh.module.home.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.user.presenter.PhotoDestroySelectPresenter;
import com.qsd.jmwh.module.home.user.presenter.PhotoDestroySelectViewer;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.ImageLoader;

public class PhotoDestroySelectActivity extends BaseBarActivity implements PhotoDestroySelectViewer, View.OnClickListener {

    @PresenterLifeCycle
    PhotoDestroySelectPresenter mPresenter = new PhotoDestroySelectPresenter(this);

    private final static String URL = "url";
    private ImageView selectDestroy;
    private LinearLayout selectDestroyBtn;
    private boolean selected = false;
    private int nFileType = 0;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.photo_destroy_select_layout);
    }

    public static Intent getIntent(Context context, String url) {
        Intent starter = new Intent(context, PhotoDestroySelectActivity.class);
        starter.putExtra(URL, url);
        return starter;
    }

    @Override
    protected void loadData() {
        ImageView resource = bindView(R.id.resource);
        ImageLoader.loadCenterCrop(getActivity(), getIntent().getStringExtra(URL), resource);
        selectDestroy = bindView(R.id.select_destroy_btn);
        selectDestroyBtn = bindView(R.id.select_destroy, this);
        TextView nextAction = bindView(R.id.next_action, this);
        selectDestroy.setSelected(selected);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_destroy:
                selected = !selected;
                selectDestroy.setSelected(selected);
                break;
            case R.id.next_action:
                PersistenceResponse response = UploadImage.uploadImage(getActivity(), UserProfile.getInstance().getObjectName(), getIntent().getStringExtra(URL));
                mPresenter.uploadFile(response.cloudUrl, 0, 0, nFileType, 0);
                break;
        }
    }
}
