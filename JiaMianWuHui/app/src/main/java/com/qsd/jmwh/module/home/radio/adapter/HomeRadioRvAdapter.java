package com.qsd.jmwh.module.home.radio.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.radio.bean.LocalHomeRadioListBean;
import com.qsd.jmwh.view.CircleImageView;
import com.qsd.jmwh.view.NoSlidingGridView;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;

import java.util.List;

public class HomeRadioRvAdapter extends BaseMultiItemQuickAdapter<LocalHomeRadioListBean, BaseViewHolder> {
    private Context context;

    public HomeRadioRvAdapter(List<LocalHomeRadioListBean> data, Context context) {
        super(data);
        addItemType(0, R.layout.item_home_radio_title);
        addItemType(1, R.layout.item_home_radio_pic);
        addItemType(2, R.layout.item_home_radio_bottom);

        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalHomeRadioListBean item) {
        switch (item.itemType) {
            case 0:
                CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
                Glide.with(context).load(item.headImg).into(iv_headimg);
                helper.setText(R.id.tv_user_name, item.userName);
                helper.setText(R.id.tv_ctime, item.cTime);
                helper.setText(R.id.tv_label_top, item.sDatingTitle + " / " + item.sDatingTime + "," + item.sDatingRange);
                helper.setText(R.id.tv_label_bottom, item.sContent);
                DelayClickImageView iv_sex = helper.getView(R.id.iv_sex);
                if (item.sex == 1) {
                    //男
                    iv_sex.setImageResource(R.drawable.man);
                } else if (item.sex == 0) {
                    //女
                    iv_sex.setImageResource(R.drawable.woman);
                }
                break;
            case 1:
                NoSlidingGridView gv_pic = helper.getView(R.id.gv_pic);
                if (item.picList != null && item.picList.size() != 0) {
                    HomeRadioPicGvAdapter picAdapter = new HomeRadioPicGvAdapter(context, item.picList);
                    gv_pic.setAdapter(picAdapter);
                }
                break;
            case 2:
                DelayClickImageView iv_like = helper.getView(R.id.iv_like);
                DelayClickImageView iv_apply = helper.getView(R.id.iv_apply);
                DelayClickTextView tv_like = helper.getView(R.id.tv_like);
                DelayClickTextView tv_apply = helper.getView(R.id.tv_apply);
                if (item.is_apply == 0) {
                    iv_apply.setImageResource(R.drawable.apply);
                } else {
                    iv_apply.setImageResource(R.drawable.apply_select);
                }
                tv_apply.setText("报名(" + item.nApplyCount + ")");
                if (item.is_like == 0) {
                    iv_like.setImageResource(R.drawable.zan);
                } else {
                    iv_like.setImageResource(R.drawable.zan_select);
                }
                helper.setText(R.id.tv_comment, "评论(" + item.count_num + ")");
                helper.setText(R.id.tv_like, "赞(" + item.like_count + ")");

                helper.getView(R.id.ll_zan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onRadioItemClickListener != null) {
                            onRadioItemClickListener.setOnRadioItemClick(iv_like, tv_like, helper.getLayoutPosition(), item.is_like, item.lDatingId + "", UserProfile.getInstance().getAppAccount() + "", item.lUserId + "");
                        }
                    }
                });
                break;
        }
    }


    public interface OnRadioItemClickListener {
        void setOnRadioItemClick(DelayClickImageView iv_like, DelayClickTextView tv_like, int position, int is_like, String lDatingId, String lJoinerId, String lInitiatorId);
    }

    OnRadioItemClickListener onRadioItemClickListener;

    public void setOnPersonItemClickListener(OnRadioItemClickListener onRadioItemClickListener) {
        this.onRadioItemClickListener = onRadioItemClickListener;
    }
}
