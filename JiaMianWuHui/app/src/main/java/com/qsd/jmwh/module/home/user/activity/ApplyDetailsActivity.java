package com.qsd.jmwh.module.home.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.radio.activity.ViewBigImageActivity;
import com.qsd.jmwh.module.home.radio.adapter.HomeRadioPicGvAdapter;
import com.qsd.jmwh.module.home.radio.bean.LocalHomeRadioListBean;
import com.qsd.jmwh.module.home.user.adapter.MineRadilLoveUserGvAdapter;
import com.qsd.jmwh.view.CircleImageView;
import com.qsd.jmwh.view.NoSlidingGridView;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;

import java.util.ArrayList;

public class ApplyDetailsActivity extends BaseBarActivity {


    private DelayClickTextView tv_auth_type;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_apply_details);
    }

    @Override
    protected void loadData() {
        setTitle("我的广播");
        String json = getIntent().getStringExtra("json");
        CircleImageView iv_headimg = bindView(R.id.iv_headimg);
        tv_auth_type = bindView(R.id.tv_auth_type);
        DelayClickTextView tv_user_name = bindView(R.id.tv_user_name);
        DelayClickTextView tv_ctime = bindView(R.id.tv_ctime);
        DelayClickTextView tv_label_top = bindView(R.id.tv_label_top);
        DelayClickTextView tv_label_bottom = bindView(R.id.tv_label_bottom);
        DelayClickTextView tv_label_middle = bindView(R.id.tv_label_middle);
        DelayClickImageView iv_sex = bindView(R.id.iv_sex);
        DelayClickImageView iv_like = bindView(R.id.iv_like);
        NoSlidingGridView gv_pic = bindView(R.id.gv_pic);
        LinearLayout ll_zan = bindView(R.id.ll_zan);
        LinearLayout ll_comment = bindView(R.id.ll_comment);
        LinearLayout ll_content = bindView(R.id.ll_content);
        LinearLayout ll_root = bindView(R.id.ll_root);
        LinearLayout ll_user = bindView(R.id.ll_user);
        DelayClickTextView tv_like = bindView(R.id.tv_like);
        DelayClickTextView tv_comment = bindView(R.id.tv_comment);
        DelayClickTextView tv_close = bindView(R.id.tv_close);
        DelayClickTextView tv_apply = bindView(R.id.tv_apply);
        NoSlidingGridView gv_user = bindView(R.id.gv_user);
        HorizontalScrollView hs = bindView(R.id.hs);

        ll_zan.setOnClickListener(view -> {
            ToastUtils.show("你不能赞自己");
        });

        ll_comment.setOnClickListener(view -> {
            ToastUtils.show("你不能评论自己");
        });
        Gson gson = new Gson();
        final LocalHomeRadioListBean localHomeRadioListBean = gson.fromJson(json, LocalHomeRadioListBean.class);
        if (localHomeRadioListBean != null) {
            Glide.with(ApplyDetailsActivity.this).load(localHomeRadioListBean.headImg).into(iv_headimg);

            if (localHomeRadioListBean.sex == 1) {
                //男
                iv_sex.setImageResource(R.drawable.man);
            } else if (localHomeRadioListBean.sex == 0) {
                //女
                iv_sex.setImageResource(R.drawable.woman);
            }

            if (localHomeRadioListBean.sex == 0) {
                //女性
                if (localHomeRadioListBean.nAuthType == 0) {
                    //未认证
                    bindText(R.id.tv_auth_type,"未认证");
                    tv_auth_type.setBackground(getResources().getDrawable(R.drawable.shape_home_person_no));
                } else {
                    //已认证
                    bindText(R.id.tv_auth_type,"真实");
                    tv_auth_type.setBackground(getResources().getDrawable(R.drawable.shape_home_person));
                }
                tv_auth_type.setVisibility(View.VISIBLE);
            } else {
                //男性
                if (localHomeRadioListBean.bVIP) {
                    tv_auth_type.setVisibility(View.VISIBLE);
                    tv_auth_type.setText("VIP");
                    tv_auth_type.setBackground(getResources().getDrawable(R.drawable.shape_home_person_vip));
                } else {
                    tv_auth_type.setVisibility(View.GONE);
                }
            }

            tv_user_name.setText(localHomeRadioListBean.userName);
            tv_ctime.setText(localHomeRadioListBean.cTime);
            tv_label_top.setText(localHomeRadioListBean.sDatingTitle + " / " + localHomeRadioListBean.sDatingTime + "," + localHomeRadioListBean.sDatingRange);
            tv_label_bottom.setText(localHomeRadioListBean.sContent);
            tv_label_middle.setText(localHomeRadioListBean.sDatingHope);


            if (localHomeRadioListBean.picList != null && localHomeRadioListBean.picList.size() != 0) {
                HomeRadioPicGvAdapter picAdapter = new HomeRadioPicGvAdapter(ApplyDetailsActivity.this, localHomeRadioListBean.picList);
                gv_pic.setAdapter(picAdapter);
                gv_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("selet", 2);// 2,大图显示当前页数，1,头像，不显示页数
                        bundle.putInt("code", i);//第几张
                        bundle.putStringArrayList("imageuri", (ArrayList<String>) localHomeRadioListBean.picList);
                        Intent intent = new Intent(ApplyDetailsActivity.this, ViewBigImageActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            if (localHomeRadioListBean.nStatus == 0) {
                tv_close.setVisibility(View.VISIBLE);
            } else {
                tv_close.setVisibility(View.GONE);
            }

            tv_apply.setText("查看报名(" + localHomeRadioListBean.nApplyCount + ")");
            tv_comment.setText("评论(" + localHomeRadioListBean.count_num + ")");
            tv_like.setText("赞(" + localHomeRadioListBean.like_count + ")");
            if (localHomeRadioListBean.is_like == 0) {
                iv_like.setImageResource(R.drawable.zan);
            } else {
                iv_like.setImageResource(R.drawable.zan_select);
            }

            if (localHomeRadioListBean.cdoComment != null && localHomeRadioListBean.cdoComment.size() != 0) {
                ll_content.setVisibility(View.VISIBLE);
                ll_root.removeAllViews();
                for (int i = 0; i < localHomeRadioListBean.cdoComment.size(); i++) {
                    View view = View.inflate(ApplyDetailsActivity.this, R.layout.item_mine_bottom_content, null);
                    TextView tv_name = view.findViewById(R.id.tv_name);
                    TextView tv_content = view.findViewById(R.id.tv_content);

                    tv_name.setText(localHomeRadioListBean.cdoComment.get(i).sNickName + "：");
                    tv_content.setText(localHomeRadioListBean.cdoComment.get(i).sContent);
                    ll_root.addView(view);
                }
            } else {
                ll_content.setVisibility(View.GONE);
            }


            if (localHomeRadioListBean.cdoLove != null && localHomeRadioListBean.cdoLove.size() != 0) {
                ll_user.removeAllViews();
                for (int i = 0; i < localHomeRadioListBean.cdoLove.size(); i++) {
                    View view = View.inflate(ApplyDetailsActivity.this, R.layout.item_love_user, null);
                    CircleImageView iv_headimg_love = view.findViewById(R.id.iv_headimg);
                    Glide.with(ApplyDetailsActivity.this).load(localHomeRadioListBean.cdoLove.get(i).sUserHeadPic).into(iv_headimg_love);
                    ll_user.addView(view);
                }
                hs.setVisibility(View.VISIBLE);
            } else {
                hs.setVisibility(View.GONE);
            }

            if (localHomeRadioListBean.cdoApply != null && localHomeRadioListBean.cdoApply.size() != 0) {
                MineRadilLoveUserGvAdapter adapter = new MineRadilLoveUserGvAdapter(localHomeRadioListBean.cdoApply, ApplyDetailsActivity.this);
                gv_user.setAdapter(adapter);
                gv_user.setVisibility(View.VISIBLE);
            } else {
                gv_user.setVisibility(View.GONE);
            }
        }
    }
}
