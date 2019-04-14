package com.qsd.jmwh.module.userinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;
import cn.finalteam.rxgalleryfinal.utils.Logger;

public class EditUserDataActivity extends BaseBarActivity implements View.OnClickListener {


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.edit_user_data_activity);

    }

    @Override
    protected void loadData() {
        setTitle("完善资料");
        initListener();

    }

    private void initListener() {
        bindView(R.id.select_pic, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_pic:
                RxGalleryFinalApi instance = RxGalleryFinalApi.getInstance(EditUserDataActivity.this);

                instance
                        .openGalleryRadioImgDefault(
                                new RxBusResultDisposable<ImageRadioResultEvent>() {
                                    @Override
                                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                                        Logger.i("只要选择图片就会触发");
                                    }
                                })
                        .onCropImageResult(
                                new IRadioImageCheckedListener() {
                                    @Override
                                    public void cropAfter(Object t) {
                                        Logger.i("裁剪完成");
                                    }

                                    @Override
                                    public boolean isActivityFinish() {
                                        Logger.i("返回false不关闭，返回true则为关闭");
                                        return true;
                                    }
                                });
                break;
            default:
        }
    }
}
