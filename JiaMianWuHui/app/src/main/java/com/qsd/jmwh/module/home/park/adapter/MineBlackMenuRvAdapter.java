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
import com.yu.common.ui.DelayClickTextView;

import java.util.List;

public class MineBlackMenuRvAdapter extends BaseQuickAdapter<MineLikeBean.CdoListBean, BaseViewHolder> {
    private List<MineLikeBean.CdoListBean> data;
    private Context context;

    public MineBlackMenuRvAdapter(int layoutResId, @Nullable List<MineLikeBean.CdoListBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
        this.data = data;
    }


    @Override
    protected void convert(BaseViewHolder helper, MineLikeBean.CdoListBean item) {
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        Glide.with(context).load(item.sUserHeadPic).into(iv_headimg);
        helper.setText(R.id.tv_user_name, item.sNickName);
        DelayClickTextView tv_cancle = helper.getView(R.id.tv_cancle);
        if (item.is_black) {
            tv_cancle.setText("取消屏蔽");
        } else {
            tv_cancle.setText("对她屏蔽");
        }
        helper.getView(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMineLikeItemClickListener != null) {
                    onMineLikeItemClickListener.setOnMineLikeItemClick(tv_cancle, helper.getLayoutPosition(), item.is_black, item.lUserId + "");
                }
            }
        });
    }


    public interface OnMineLikeItemClickListener {
        void setOnMineLikeItemClick(DelayClickTextView tv_black, int position, boolean is_black, String lLoveUserId);
    }

    OnMineLikeItemClickListener onMineLikeItemClickListener;

    public void setOnPersonItemClickListener(OnMineLikeItemClickListener onMineLikeItemClickListener) {
        this.onMineLikeItemClickListener = onMineLikeItemClickListener;
    }
}
