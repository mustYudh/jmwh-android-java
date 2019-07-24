package com.qsd.jmwh.module.home;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class HomeActivity extends BaseActivity implements HomeViewer, TencentLocationListener {

  private View messageHin;
  @PresenterLifeCycle private HomePresenter mPresenter = new HomePresenter(this);
  private PressHandle pressHandle = new PressHandle(this);

  private BottomNavigationView navigationView;
  private RxCountDown looperTime = new RxCountDown();
  private final static int LOCATION_UPLOAD_TIMER = 10;
  private MsgServiceObserve mService;
  private Observer<List<RecentContact>> mMessageObserver;

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
    TencentLocationRequest request = TencentLocationRequest.create()
        .setInterval(1000 * 5 * 60)
        .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
    TencentLocationManager locationManager = TencentLocationManager.getInstance(getActivity());
    int error = locationManager.requestLocationUpdates(request, this);
    Log.e("======>", "定位的code:" + error);
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
    getUnReadMessageCont();
    mService = NIMClient.getService(MsgServiceObserve.class);
    mMessageObserver = (Observer<List<RecentContact>>) contacts -> {
      Log.e("======>", "注册成功");
      getUnReadMessageCont();
    };
    mService.observeRecentContact(mMessageObserver, true);
  }

  void getUnReadMessageCont() {
    int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();
    Log.e("======>", "注册成功" + unreadNum);
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

  @Override public void onLocationChanged(TencentLocation location, int i, String s) {
    if (i == 0) {
      double longitude = location.getLongitude();
      double latitude = location.getLatitude();
      Log.e("======>dingwei", "longitude:"
          + longitude
          + ",latitude:"
          + latitude
          + ",address:"
          + location.getAddress()
          + location.getName()
          + "===="
          + location.getCity());
      mPresenter.modifyLngAndLat(location);
    }
  }

  @Override public void onStatusUpdate(String s, int i, String s1) {

  }
}
