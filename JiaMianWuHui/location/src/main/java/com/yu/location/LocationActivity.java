package com.yu.location;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/***
 * 单点定位示例，用来展示基本的定位结果，配置在LocationService.java中
 * 默认配置也可以在LocationService中修改
 * 默认配置的内容自于开发者论坛中对开发者长期提出的疑问内容
 *
 * @author baidu
 *
 */
public class LocationActivity extends Activity {
    private LocationUtils locationUtils;
    private TextView LocationResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // -----------demo view config ------------
        setContentView(R.layout.location);
        locationUtils = new LocationUtils();
        LocationResult =  findViewById(R.id.textView1);
    }




    @Override
    protected void onStop() {
        locationUtils.stop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationUtils.start(this);
        locationUtils.setCallBack(new LocationUtils.ResultCallBack() {
            @Override
            public void setText(String text) {
                LocationResult.setText(text);
            }
        });
    }


}
