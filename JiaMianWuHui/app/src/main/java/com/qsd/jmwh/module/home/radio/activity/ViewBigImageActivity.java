package com.qsd.jmwh.module.home.radio.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.qsd.jmwh.R;
import com.yu.common.toast.ToastUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * 用于查看大图
 *
 * @author jingbin
 */
public class ViewBigImageActivity extends FragmentActivity implements OnPageChangeListener, OnPhotoTapListener {

    // 接收传过来的uri地址
    List<String> imageuri;
    // 接收穿过来当前选择的图片的数量
    int code;
    // 用于判断是头像还是文章图片 1:头像 2：文章大图
    int selet;
    //判断是冲哪传过来的
    int type;
    //活动link;
    String link;
    //商品id
    String goods_id;
    // 用于管理图片的滑动
    ViewPager very_image_viewpager;
    // 当前页数
    private int page;

    /**
     * 显示当前图片的页数
     */
    TextView tv_count;
    TextView tv_num;
    /**
     * 用于判断是否是加载本地图片
     */
    private boolean isLocal;

    ViewPagerAdapter adapter;

    /**
     * 本应用图片的id
     */
    private int imageId;
    /**
     * 是否是本应用中的图片
     */
    private boolean isApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_big_image);
        getView();
    }


    /**
     * Glide 获得图片缓存路径
     */
    private String getImagePath(String imgUrl) {
        String path = null;
        FutureTarget<File> future = Glide.with(ViewBigImageActivity.this)
                .load(imgUrl)
                .downloadOnly(500, 500);
        try {
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return path;
    }


    /*
     * 接收控件
     */
    private void getView() {
        /************************* 接收传值 ***********************/
        Bundle bundle = getIntent().getExtras();
        code = bundle.getInt("code");
        selet = bundle.getInt("selet");
        isLocal = bundle.getBoolean("isLocal", false);
        imageuri = bundle.getStringArrayList("imageuri");
        /**是否是本应用中的图片*/
        isApp = bundle.getBoolean("isApp", false);
        /**本应用图片的id*/
        imageId = bundle.getInt("id", 0);
        type = bundle.getInt("type");
        goods_id = bundle.getString("goods_id");
        link = bundle.getString("AcLink");

        /************************* 接收控件 ***********************/
        tv_count = findViewById(R.id.tv_count);
        tv_num = findViewById(R.id.tv_num);
        very_image_viewpager = findViewById(R.id.very_image_viewpager);


        /**
         * 给viewpager设置适配器
         */
        if (isApp) {
            MyPageAdapter myPageAdapter = new MyPageAdapter();
            very_image_viewpager.setAdapter(myPageAdapter);
            very_image_viewpager.setEnabled(false);
        } else {
            adapter = new ViewPagerAdapter();
            very_image_viewpager.setAdapter(adapter);
            very_image_viewpager.setCurrentItem(code);
            page = code;
            very_image_viewpager.setOnPageChangeListener(this);
            very_image_viewpager.setEnabled(false);
            // 设定当前的页数和总页数
            if (selet == 2) {
                tv_count.setText((code + 1) + "");
                tv_num.setText(" / " + imageuri.size());
            }
        }
    }

    @Override
    public void onPhotoTap(ImageView imageView, float v, float v1) {
        finish();
    }

    /**
     * 本应用图片适配器
     */

    class MyPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.viewpager_very_image, container, false);
            PhotoView zoom_image_view = view.findViewById(R.id.zoom_image_view);
            ProgressBar spinner = view.findViewById(R.id.loading);
            spinner.setVisibility(View.GONE);
            if (imageId != 0) {

                zoom_image_view.setImageResource(imageId);
            }

            zoom_image_view.setOnPhotoTapListener(ViewBigImageActivity.this);
            container.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    /**
     * ViewPager的适配器
     *
     * @author guolin
     */
    class ViewPagerAdapter extends PagerAdapter {

        LayoutInflater inflater;

        ViewPagerAdapter() {
            inflater = getLayoutInflater();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = inflater.inflate(R.layout.viewpager_very_image, container, false);
            final PhotoView zoom_image_view = view.findViewById(R.id.zoom_image_view);
            final ProgressBar spinner = view.findViewById(R.id.loading);
            // 保存网络图片的路径
            String adapter_image_Entity = (String) getItem(position);
            String imageUrl;
            if (isLocal) {
                imageUrl = "file://" + adapter_image_Entity;
            } else {
                imageUrl = adapter_image_Entity;
            }

            spinner.setVisibility(View.VISIBLE);
            spinner.setClickable(false);
            Glide.with(ViewBigImageActivity.this).load(imageUrl).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    ToastUtils.show("资源加载异常");
                    spinner.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    spinner.setVisibility(View.GONE);
                    return false;
                }
            }).into(zoom_image_view);
            zoom_image_view.setOnPhotoTapListener(ViewBigImageActivity.this);
            container.addView(view, 0);
            return view;
        }

        @Override
        public int getCount() {
            if (imageuri == null || imageuri.size() == 0) {
                return 0;
            }
            return imageuri.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        Object getItem(int position) {
            return imageuri.get(position);
        }
    }

    /**
     * 下面是对Viewpager的监听
     */
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    /**
     * 本方法主要监听viewpager滑动的时候的操作
     */
    @Override
    public void onPageSelected(int arg0) {
        // 每当页数发生改变时重新设定一遍当前的页数和总页数
        tv_count.setText((arg0 + 1) + "");
        tv_num.setText(" / " + imageuri.size());
        page = arg0;
    }


}
