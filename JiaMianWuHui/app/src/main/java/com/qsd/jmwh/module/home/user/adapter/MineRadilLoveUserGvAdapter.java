package com.qsd.jmwh.module.home.user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.bumptech.glide.Glide;
import com.qsd.jmwh.R;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.park.bean.SubViewCount;
import com.qsd.jmwh.module.home.user.bean.MineRadioListBean;
import com.qsd.jmwh.module.register.ToByVipActivity;
import com.qsd.jmwh.view.CircleImageView;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.ui.DelayClickTextView;
import java.util.List;

public class MineRadilLoveUserGvAdapter extends BaseAdapter {
    private List<MineRadioListBean.CdoListBean.CdoApplyBean> list;
    private Context context;

    public MineRadilLoveUserGvAdapter(List<MineRadioListBean.CdoListBean.CdoApplyBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_apply_user, null);
            holder = new ViewHolder();
            holder.iv_headimg = view.findViewById(R.id.iv_headimg);
            holder.tv_name = view.findViewById(R.id.tv_name);
            holder.tv_time = view.findViewById(R.id.tv_time);
            holder.tv_get = view.findViewById(R.id.tv_get);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_name.setText(list.get(i).sNickName);
        Glide.with(context).load(list.get(i).sUserHeadPic).into(holder.iv_headimg);
        holder.tv_get.setOnClickListener(view1 -> {
            getSubViewCount(list.get(i));
        });
        return view;
    }

    class ViewHolder {
        CircleImageView iv_headimg;
        DelayClickTextView tv_name, tv_time, tv_get;

    }


    @SuppressLint("CheckResult") public void getSubViewCount(
        MineRadioListBean.CdoListBean.CdoApplyBean cdoApplyBean) {
        XHttpProxy.proxy(OtherApiServices.class)
            .getSubViewCount()
            .subscribeWith(new TipRequestSubscriber<SubViewCount>() {
                @Override protected void onSuccess(SubViewCount count) {
                    
                }
            });
    }


    private void buyVip() {
        LauncherHelper.from(context)
            .startActivity(
                ToByVipActivity.getIntent(context, UserProfile.getInstance().getUserId(),
                    UserProfile.getInstance().getAppToken()));
    }

}
