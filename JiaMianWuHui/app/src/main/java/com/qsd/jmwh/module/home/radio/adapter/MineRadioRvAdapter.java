package com.qsd.jmwh.module.home.radio.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.radio.activity.ViewBigImageActivity;
import com.qsd.jmwh.module.home.radio.bean.LocalHomeRadioListBean;
import com.qsd.jmwh.view.CircleImageView;
import com.qsd.jmwh.view.NoSlidingGridView;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;
import com.yu.common.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MineRadioRvAdapter extends BaseMultiItemQuickAdapter<LocalHomeRadioListBean, BaseViewHolder> {
    private Context context;

    public MineRadioRvAdapter(List<LocalHomeRadioListBean> data, Context context) {
        super(data);
        addItemType(0, R.layout.item_home_radio_title);
        addItemType(1, R.layout.item_home_radio_pic);
        addItemType(2, R.layout.item_mine_radio_bottom);

        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalHomeRadioListBean item) {
        switch (item.itemType) {
            case 0:
                CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
                Glide.with(context).load(item.headImg).into(iv_headimg);
                ImageLoader.loadCenterCrop(context, item.headImg, iv_headimg, R.drawable.zhanwei);
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
                helper.getView(R.id.ll_root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onRadioItemClickListener != null) {
                            onRadioItemClickListener.setOnPersonInfoItemClick(item.lUserId);
                        }
                    }
                });
                break;
            case 1:
                NoSlidingGridView gv_pic = helper.getView(R.id.gv_pic);
                if (item.picList != null && item.picList.size() != 0) {
                    HomeRadioPicGvAdapter picAdapter = new HomeRadioPicGvAdapter(context, item.picList);
                    gv_pic.setAdapter(picAdapter);
                    gv_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("selet", 2);// 2,大图显示当前页数，1,头像，不显示页数
                            bundle.putInt("code", i);//第几张
                            bundle.putStringArrayList("imageuri", (ArrayList<String>) item.picList);
                            Intent intent = new Intent(context, ViewBigImageActivity.class);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                }
                break;
            case 2:
                DelayClickImageView iv_like = helper.getView(R.id.iv_like);
                DelayClickTextView tv_like = helper.getView(R.id.tv_like);
                DelayClickTextView tv_apply = helper.getView(R.id.tv_apply);
                DelayClickTextView tv_close = helper.getView(R.id.tv_close);

                if (item.nStatus == 0) {
                    tv_close.setVisibility(View.VISIBLE);
                } else {
                    tv_close.setVisibility(View.GONE);
                }
                tv_apply.setText("查看报名(" + item.nApplyCount + ")");
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

                helper.getView(R.id.ll_comment).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onRadioItemClickListener != null) {
                            onRadioItemClickListener.setOnAddContentItemClick(item);
                        }
                    }
                });

                helper.getView(R.id.ll_enroll).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (onRadioItemClickListener != null) {
                            onRadioItemClickListener.setOnAddDatingEnrollItemClick(item);
                        }
                    }
                });

                LinearLayout ll_content = helper.getView(R.id.ll_content);
                LinearLayout ll_root = helper.getView(R.id.ll_root);
                if (item.cdoComment != null && item.cdoComment.size() != 0) {
                    ll_content.setVisibility(View.VISIBLE);
                    ll_root.removeAllViews();
                    for (int i = 0; i < item.cdoComment.size(); i++) {
                        View view = View.inflate(context, R.layout.item_mine_bottom_content, null);
                        TextView tv_name = view.findViewById(R.id.tv_name);
                        TextView tv_content = view.findViewById(R.id.tv_content);

                        tv_name.setText(item.cdoComment.get(i).sNickName + "：");
                        tv_content.setText(item.cdoComment.get(i).sContent);
                        ll_root.addView(view);
                    }
                } else {
                    ll_content.setVisibility(View.GONE);
                }

                tv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onRadioItemClickListener != null) {
                            onRadioItemClickListener.setOnCloseEnrollItemClick(item);
                        }
                    }
                });

                LinearLayout ll_user = helper.getView(R.id.ll_user);
                HorizontalScrollView hs = helper.getView(R.id.hs);
                if (item.cdoLove != null && item.cdoLove.size() != 0) {
                    ll_user.removeAllViews();
                    for (int i = 0; i < item.cdoLove.size(); i++) {
                        View view = View.inflate(context, R.layout.item_love_user, null);
                        CircleImageView iv_headimg_love = view.findViewById(R.id.iv_headimg);
                        Glide.with(context).load(item.cdoLove.get(i).sUserHeadPic).into(iv_headimg_love);
                        ll_user.addView(view);
                    }
                    hs.setVisibility(View.VISIBLE);
                } else {
                    hs.setVisibility(View.GONE);
                }
                break;
        }
    }


    public interface OnRadioItemClickListener {
        void setOnRadioItemClick(DelayClickImageView iv_like, DelayClickTextView tv_like, int position, int is_like, String lDatingId, String lJoinerId, String lInitiatorId);

        void setOnPersonInfoItemClick(int lLoveUserId);

        void setOnAddContentItemClick(LocalHomeRadioListBean item);

        void setOnAddDatingEnrollItemClick(LocalHomeRadioListBean item);

        void setOnCloseEnrollItemClick(LocalHomeRadioListBean item);

    }

    OnRadioItemClickListener onRadioItemClickListener;

    public void setOnPersonItemClickListener(OnRadioItemClickListener onRadioItemClickListener) {
        this.onRadioItemClickListener = onRadioItemClickListener;
    }
}
