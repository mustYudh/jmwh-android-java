package com.yu.location;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.yu.location.impl.IpGetLocation;
import com.yu.location.impl.IpLocCallback;

/***
 * 单点定位示例，用来展示基本的定位结果，配置在LocationService.java中
 * 默认配置也可以在LocationService中修改
 * 默认配置的内容自于开发者论坛中对开发者长期提出的疑问内容
 *
 * @author baidu
 *
 */
public class LocationActivity extends Activity {
    private TextView LocationResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // -----------demo view config ------------
        setContentView(R.layout.location);

        IpGetLocation.getLocationByIP(new IpLocCallback() {
            @Override public void getLoc(long id, ILocation iLocation) {
                ((TextView) findViewById(R.id.textView1)).setText("" + iLocation);
            }
        },-1);
    }




    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
