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
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.module.home.user.bean.UserCenterMyInfo;
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
    private final static String FILE_TYPE = "file_type";
    private final static String FILE_ID = "file_id";
    private ImageView selectDestroy;
    private LinearLayout selectDestroyBtn;
    private boolean selected = false;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.photo_destroy_select_layout);
    }

    public static Intent getIntent(Context context, String url) {
        Intent starter = new Intent(context, PhotoDestroySelectActivity.class);
        starter.putExtra(URL, url);
        return starter;
    }

    public static Intent getIntent(Context context, UserCenterMyInfo.CdoimgListBean cdoimgListBean) {
        Intent starter = new Intent(context, PhotoDestroySelectActivity.class);
        starter.putExtra(URL, cdoimgListBean.sFileUrl);
        starter.putExtra(FILE_TYPE, cdoimgListBean.nFileType);
        starter.putExtra(FILE_ID, cdoimgListBean.lFileId);
        return starter;
    }

    @Override
    protected void loadData() {
        ImageView resource = bindView(R.id.resource);
        String url = getIntent().getStringExtra(URL);
        ImageLoader.loadCenterCrop(getActivity(), url, resource);
        selectDestroy = bindView(R.id.select_destroy_btn);
        selectDestroyBtn = bindView(R.id.select_destroy, this);
        TextView nextAction = bindView(R.id.next_action, this);
        int fileId = getIntent().getIntExtra(FILE_ID, -1);
        if (fileId != -1) {
            selectDestroy.setSelected(getIntent().getIntExtra(FILE_TYPE, -1) == 1);
            selectDestroyBtn.setClickable(false);
            nextAction.setText("删除");
            setTitle("删除图片");
        } else {
            setTitle("选择图片");
            selectDestroy.setSelected(selected);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_destroy:
                selected = !selected;
                selectDestroy.setSelected(selected);
                break;
            case R.id.next_action:
                int fileId = getIntent().getIntExtra(FILE_ID, -1);
                if (fileId == -1) {
                    PersistenceResponse response = UploadImage.uploadImage(getActivity(), UserProfile.getInstance().getObjectName(), getIntent().getStringExtra(URL));
                    mPresenter.uploadFile(response.cloudUrl, 0, 0, selected ? 1 : 0, 0);
                } else {
                    SelectHintPop selectHintPop = new SelectHintPop(getActivity());
                    selectHintPop.setTitle("提示").setMessage("确定要删除这张照片吗？").setNegativeButton("取消", v1 -> {
                        selectHintPop.dismiss();
                    }).setPositiveButton("删除", v12 -> {
                        mPresenter.deleteFile( fileId, getIntent().getIntExtra(FILE_TYPE,-1));
                        selectHintPop.dismiss();
                    }).showPopupWindow();
                }
                break;
        }
    }
}
