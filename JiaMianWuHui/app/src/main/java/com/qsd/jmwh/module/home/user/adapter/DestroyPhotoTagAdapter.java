package com.qsd.jmwh.module.home.user.adapter;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseHolder;
import com.qsd.jmwh.base.BasicAdapter;
import com.qsd.jmwh.module.home.user.bean.UserCenterInfo;
import com.yu.common.ui.Res;
import com.yu.common.utils.ImageLoader;

import java.util.ArrayList;

public class DestroyPhotoTagAdapter extends BasicAdapter<UserCenterInfo.CdoimgListBean> {
  private AddImageListener addImageListener;

  public interface AddImageListener {
    void clickAdd();

    void clickImage(UserCenterInfo.CdoimgListBean cdoimgListBean);
  }

  public void setAddImageListener(AddImageListener addImageListener) {
    this.addImageListener = addImageListener;
  }

  public DestroyPhotoTagAdapter(ArrayList<UserCenterInfo.CdoimgListBean> list) {
    super(list);
  }

  @Override protected BaseHolder<UserCenterInfo.CdoimgListBean> getHolder(Context context) {
    return new BaseHolder<UserCenterInfo.CdoimgListBean>(context, R.layout.item_user_center_img) {
      @Override public void bindData(UserCenterInfo.CdoimgListBean item) {
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
          TextView tag = findViewId(R.id.destroy_tag);
          boolean isVideo = item.nAttribute == 1;
          if (!isVideo) {
            tag.setVisibility(item.nFileType != 0 ? View.VISIBLE : View.GONE);
          } else {
            tag.setVisibility(View.VISIBLE);
            if (item.nFileType == 0) {
              tag.setBackgroundColor(Res.color(R.color.color_815DBE));
              tag.setText("视频");
            }
          }
          if (item.nFileType == 1) {
            tag.setBackgroundColor(Res.color(R.color.color_815DBE));
            tag.setText(isVideo ? "阅后即焚视频" : "阅后即焚");
          } else if (item.nFileType == 2) {
            if (item.nStatus == 3) {
              tag.setBackgroundColor(Res.color(R.color.color_BD955C));
              tag.setText("待审核");
            } else if (item.nStatus == 4) {
              tag.setBackgroundColor(Res.color(R.color.color_5B5751));
              tag.setText("审核失败");
            } else {
              tag.setBackgroundColor(Res.color(R.color.color_BC6C6D));
              tag.setText(isVideo ? "红包视频" : "红包");
            }
          } else if (item.nFileType == 3) {
            if (item.nStatus == 3) {
              tag.setBackgroundColor(Res.color(R.color.color_BD955C));
              tag.setText("待审核");
            } else if (item.nStatus == 4) {
              tag.setBackgroundColor(Res.color(R.color.color_5B5751));
              tag.setText("审核失败");
            } else {
              tag.setBackgroundColor(Res.color(R.color.color_BC6C6D));
              tag.setText(isVideo ? "阅后即焚红包视频" : "阅后即焚红包");
            }
          }
          ImageLoader.loadCenterCrop(imageView.getContext(),
              isVideo ? item.sFileCoverUrl : item.sFileUrl, imageView);
          imageView.setOnClickListener(v -> {
            if (addImageListener != null) {
              addImageListener.clickImage(item);
            }
          });
        }
      }
    };
  }
}