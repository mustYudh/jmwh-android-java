package com.qsd.jmwh.module.home.park.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.user.bean.MineLikeBean;
import com.qsd.jmwh.view.CircleImageView;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;

import java.util.List;

public class MineLikeRvAdapter extends BaseQuickAdapter<MineLikeBean.CdoListBean, BaseViewHolder> {
  private Context context;

  public MineLikeRvAdapter(int layoutResId, @Nullable List<MineLikeBean.CdoListBean> data,
      Context context) {
    super(layoutResId, data);
    this.context = context;
  }

  @Override protected void convert(BaseViewHolder helper, MineLikeBean.CdoListBean item) {
    CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
    DelayClickTextView tv_online = helper.getView(R.id.tv_online);
    Glide.with(context).load(item.sUserHeadPic).into(iv_headimg);
    helper.setText(R.id.tv_user_name, item.sNickName);
    helper.setText(R.id.tv_photo_num, item.nGalaryCapacity + "");
    if (item.distance_um >= 1000) {
      helper.setText(R.id.tv_middle,
          item.sDateRange + " · " + item.sJob + " · " + item.sAge + " · " + (item.distance_um
              / 1000) + "km");
    } else {
      helper.setText(R.id.tv_middle,
          item.sDateRange + " · " + item.sJob + " · " + item.sAge + " · " + item.distance_um + "m");
    }
    if (item.nOnLine == 0) {
      //在线
      tv_online.setText("当前在线");
      tv_online.setTextColor(context.getResources().getColor(R.color.app_main_color));
    } else if (item.nOnLine == 1) {
      tv_online.setText(getTime(item.nOffLineMin));
      tv_online.setTextColor(context.getResources().getColor(R.color.color_666666));
    }

    DelayClickTextView tv_auth_type = helper.getView(R.id.tv_auth_type);

    if (item.nSex == 0) {
      if (item.nAuthType == 3) {
        //已认证
        helper.setText(R.id.tv_auth_type, "真实");
        tv_auth_type.setBackground(
            context.getResources().getDrawable(R.drawable.shape_home_person));
      } else {
        //未认证
        helper.setText(R.id.tv_auth_type, "未认证");
        tv_auth_type.setBackground(
            context.getResources().getDrawable(R.drawable.shape_home_person_no));
      }
    } else {
      //男性
      if (item.bVip) {
        tv_auth_type.setVisibility(View.VISIBLE);
        tv_auth_type.setText("VIP");
        tv_auth_type.setBackground(
            context.getResources().getDrawable(R.drawable.shape_home_person_vip));
      } else {
        tv_auth_type.setVisibility(View.GONE);
      }
    }

    DelayClickImageView iv_love = helper.getView(R.id.iv_love);
    if (item.blove) {
      iv_love.setImageResource(R.drawable.collect_select);
    } else {
      iv_love.setImageResource(R.drawable.collect);
    }
    helper.getView(R.id.rl_love).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (onMineLikeItemClickListener != null) {
          onMineLikeItemClickListener.setOnMineLikeItemClick(iv_love, helper.getLayoutPosition(),
              item.blove, item.lUserId + "");
        }
      }
    });
    helper.getView(R.id.ll_root).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (onMineLikeItemClickListener != null) {
          onMineLikeItemClickListener.setOnPersonInfoItemClick(item.lUserId);
        }
      }
    });
  }

  public interface OnMineLikeItemClickListener {
    void setOnMineLikeItemClick(DelayClickImageView iv_love, int position, boolean is_love,
        String lLoveUserId);

    void setOnPersonInfoItemClick(int lLoveUserId);
  }

  OnMineLikeItemClickListener onMineLikeItemClickListener;

  public void setOnPersonItemClickListener(
      OnMineLikeItemClickListener onMineLikeItemClickListener) {
    this.onMineLikeItemClickListener = onMineLikeItemClickListener;
  }

  private String getTime(long time) {
    String showTime = "";
    if (time / 60 < 24) {
      showTime = (time / 60) + "小时前";
    }
    if (time / 60 >= 24) {
      showTime = (time / 24) > 3 ? "3天前" : (time / 24) + "天";
    }
    return showTime;
  }
}
