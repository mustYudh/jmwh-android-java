package com.qsd.jmwh.module.home;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.denghao.control.TabItem;
import com.denghao.control.TabView;
import com.denghao.control.view.BottomNavigationView;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.message.MessageFragment;
import com.qsd.jmwh.module.home.park.ParkFragment;
import com.qsd.jmwh.module.home.presenter.HomePresenter;
import com.qsd.jmwh.module.home.presenter.HomeViewer;
import com.qsd.jmwh.module.home.radio.RadioFragment;
import com.qsd.jmwh.module.home.user.UserFragment;
import com.qsd.jmwh.utils.ActivityManager;
import com.qsd.jmwh.utils.PressHandle;
import com.qsd.jmwh.utils.countdown.RxCountDown;
import com.qsd.jmwh.utils.countdown.RxCountDownAdapter;
import com.qsd.jmwh.utils.gps.MyLocationListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class HomeActivity extends BaseActivity implements HomeViewer {

  private View messageHin;
  @PresenterLifeCycle private HomePresenter mPresenter = new HomePresenter(this);
  private PressHandle pressHandle = new PressHandle(this);

  private BottomNavigationView navigationView;
  private RxCountDown looperTime = new RxCountDown();
  private final static int LOCATION_UPLOAD_TIMER = 10;
  private MsgServiceObserve mService;
  private Observer<List<RecentContact>> mMessageObserver;
  public LocationClient mLocationClient = null;
  private MyLocationListener myListener = new MyLocationListener();
  LocationClientOption option = new LocationClientOption();

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.home_activity);
  }

  Observer<StatusCode> userStatusObserver = (Observer<StatusCode>) code -> {
    if (code == StatusCode.KICK_BY_OTHER_CLIENT) {
      ToastUtils.show("当前账号在其他设备上登录,请重新登录!");
      ActivityManager.getInstance().reLogin();
    }
  };

  private void registerObservers(boolean register) {
    NIMClient.getService(AuthServiceObserver.class).observeOtherClients(clientsObserver, register);
    NIMClient.getService(AuthServiceObserver.class)
        .observeOnlineStatus(userStatusObserver, register);
  }

  Observer<List<OnlineClient>> clientsObserver = (Observer<List<OnlineClient>>) onlineClients -> {

  };

  @Override protected void loadData() {
    setTitle("首页");
    initLocation();
    String[] permiss = {
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,
    };
    if (Build.VERSION.SDK_INT >= 23) {
      final RxPermissions rxPermissions = new RxPermissions(this);
      rxPermissions.request(permiss).subscribe(granted -> {
      });
    }
    registerObservers(true);
    EventBus.getDefault().post(true);
    mPresenter.modifyLngAndLat();
    navigationView = findViewById(R.id.bottom_navigation_view);
    List<TabItem> items = new ArrayList<>();
    items.add(new TabView(createTabView("首页", R.drawable.tab_02, 0), new ParkFragment()));
    items.add(new TabView(createTabView("约会广场", R.drawable.tab_01, 1), new RadioFragment()));
    items.add(new TabView(createTabView("消息中心", R.drawable.tab_03, 2), new MessageFragment()));
    items.add(new TabView(createTabView("个人中心", R.drawable.tab_04, 3), new UserFragment()));
    navigationView.initControl(this).setPagerView(items, 0);
    navigationView.getNavgation().setTabControlHeight(60);
    navigationView.getControl().setOnTabClickListener((position, view) -> {

    });
    looperTime.setCountDownTimeListener(new RxCountDownAdapter() {
      @Override public void onStart() {
        super.onStart();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        looperTime.restart(LOCATION_UPLOAD_TIMER, true);
      }

      @Override public void onComplete() {
        super.onComplete();
        looperTime.restart(LOCATION_UPLOAD_TIMER, true);
      }
    });
    looperTime.start(LOCATION_UPLOAD_TIMER);

    mService = NIMClient.getService(MsgServiceObserve.class);
    mMessageObserver = (Observer<List<RecentContact>>) contacts -> {
      getUnReadMessageCont();
    };
    mService.observeRecentContact(mMessageObserver, true);
  }

  private void initLocation() {
    mLocationClient = new LocationClient(getApplicationContext());
    //声明LocationClient类
    mLocationClient.registerLocationListener(myListener);


    option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
    //可选，设置定位模式，默认高精度
    //LocationMode.Hight_Accuracy：高精度；
    //LocationMode. Battery_Saving：低功耗；
    //LocationMode. Device_Sensors：仅使用设备；

    option.setCoorType("bd09ll");
    //可选，设置返回经纬度坐标类型，默认GCJ02
    //GCJ02：国测局坐标；
    //BD09ll：百度经纬度坐标；
    //BD09：百度墨卡托坐标；
    //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

    option.setScanSpan(1000 * 60 * 5);
    //可选，设置发起定位请求的间隔，int类型，单位ms
    //如果设置为0，则代表单次定位，即仅定位一次，默认为0
    //如果设置非0，需设置1000ms以上才有效

    option.setOpenGps(true);
    //可选，设置是否使用gps，默认false
    //使用高精度和仅用设备两种定位模式的，参数必须设置为true

    option.setLocationNotify(true);
    //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

    option.setIgnoreKillProcess(false);
    //可选，定位SDK内部是一个service，并放到了独立进程。
    //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

    option.SetIgnoreCacheException(false);
    //可选，设置是否收集Crash信息，默认收集，即参数为false

    option.setWifiCacheTimeOut(5*60*1000);
    //可选，V7.2版本新增能力
    //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

    option.setEnableSimulateGps(false);
    //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

    mLocationClient.setLocOption(option);
    //mLocationClient为第二步初始化过的LocationClient对象
    //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
    //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    mLocationClient.start();
  }

  void getUnReadMessageCont() {
    int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();
    if (unreadNum > 0) {
      messageHin.setVisibility(View.VISIBLE);
    } else {
      messageHin.setVisibility(View.GONE);
    }
  }

  public View createTabView(String name, int drawable, int position) {
    View view =
        LayoutInflater.from(this).inflate(R.layout.home_table_layout, navigationView, false);
    ImageView imageView = view.findViewById(R.id.tab_icon);
    TextView tabName = view.findViewById(R.id.tab_name);
    imageView.setImageResource(drawable);
    tabName.setText(name);
    if (position == 2) {
      messageHin = view.findViewById(R.id.message_hint);
    }
    return view;
  }

  @Override public void onBackPressed() {
    if (!pressHandle.handlePress(KeyEvent.KEYCODE_BACK)) {
      super.onBackPressed();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    registerObservers(false);
    UserProfile.getInstance().setHomeCityName("");
    if (looperTime != null) {
      looperTime.stop();
    }
    mService.observeRecentContact(mMessageObserver, false);
  }
}
