package com.qsd.jmwh.module.home.radio.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.view.CircleImageView;
import com.qsd.jmwh.view.NoSlidingGridView;

import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.SwitchView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

public class ReleaseAppointmentActivity extends BaseBarActivity {

    private NoSlidingGridView gv_photo;
    private List<String> allLocationSelectedPicture = new ArrayList<>();
    private GridAdapter adapter;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_release_appointment);
    }

    @Override
    protected void loadData() {
        String activity_title = getIntent().getStringExtra("activity_title");
        setTitle(activity_title);
        LinearLayout ll_qiwang = bindView(R.id.ll_qiwang);
        LinearLayout ll_city = bindView(R.id.ll_city);
        LinearLayout ll_time = bindView(R.id.ll_time);
        TextView tv_sure = bindView(R.id.tv_sure);
        TextView tv_qiwang = bindView(R.id.tv_qiwang);
        TextView tv_city = bindView(R.id.tv_city);
        TextView tv_time = bindView(R.id.tv_time);
        EditText et_buchong = bindView(R.id.et_buchong);
        SwitchView sw_one = bindView(R.id.sw_one);
        SwitchView sw_two = bindView(R.id.sw_two);


        gv_photo = bindView(R.id.gv_photo);
        adapter = new GridAdapter();
        gv_photo.setAdapter(adapter);


    }


    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (allLocationSelectedPicture.size() == 3) {
                return allLocationSelectedPicture.size();
            }
            return allLocationSelectedPicture.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.gv_item, null);
                holder = new ViewHolder();
                holder.ivDemo = (ImageView) convertView.findViewById(R.id.iv_picture_item);
                holder.ivDel = (CircleImageView) convertView.findViewById(R.id.iv_del);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == allLocationSelectedPicture.size()) {
                if (position == 3) {
                    holder.ivDemo.setVisibility(View.GONE);
                }

                holder.ivDemo.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pic_add));
                holder.ivDel.setVisibility(View.GONE);
                holder.ivDemo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPhoto();
                    }
                });
            } else {
                Glide.with(getActivity()).load(allLocationSelectedPicture.get(position) + "").into(holder.ivDemo);
                holder.ivDel.setVisibility(View.VISIBLE);
            }
            holder.ivDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    allLocationSelectedPicture.remove(position);
                    if (adapter != null) {
                        gv_photo.setAdapter(adapter);
                    }
                }
            });
            return convertView;
        }
    }


    public class ViewHolder {
        public ImageView ivDemo;
        public CircleImageView ivDel;
    }


    private void addPhoto() {
        RxGalleryFinalApi instance = RxGalleryFinalApi.getInstance(getActivity());
        instance.openSingGalleryRadioImgDefault(
                new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                        String imgUrl = imageRadioResultEvent.getResult().getOriginalPath();
                        if (!TextUtils.isEmpty(imgUrl)) {
                            allLocationSelectedPicture.add(imgUrl);
                            if (adapter != null) {
                                gv_photo.setAdapter(adapter);
                            }
                        }
                    }
                });
    }
}
