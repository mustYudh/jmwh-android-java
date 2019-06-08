package com.qsd.jmwh.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.denghao.control.TabItem;
import com.denghao.control.TabView;
import com.denghao.control.view.BottomNavigationView;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.ParkFragment;
import com.qsd.jmwh.module.home.presenter.HomePresenter;
import com.qsd.jmwh.module.home.presenter.HomeViewer;
import com.qsd.jmwh.module.home.radio.RadioFragment;
import com.qsd.jmwh.module.home.user.UserFragment;
import com.qsd.jmwh.utils.PressHandle;
import com.qsd.jmwh.utils.countdown.RxCountDown;
import com.qsd.jmwh.utils.countdown.RxCountDownAdapter;
import com.yu.common.mvp.PresenterLifeCycle;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements HomeViewer {

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
    NIMClient.getService(AuthService.class)
        .login(new com.netease.nimlib.sdk.auth.LoginInfo(UserProfile.getInstance().getSimUserId(),
            UserProfile.getInstance().getSimToken()));

  }

  @Override protected void loadData() {
    setTitle("首页");
    mPresenter.modifyLngAndLat();
    navigationView = findViewById(R.id.bottom_navigation_view);
    List<TabItem> items = new ArrayList<>();
    items.add(new TabView(createTabView("假面舞会", R.drawable.tab_01, 0), new ParkFragment()));
    items.add(new TabView(createTabView("约会电台", R.drawable.tab_03, 1), new RadioFragment()));
    items.add(
        new TabView(createTabView("消息中心", R.drawable.tab_02, 2), new RecentContactsFragment()));
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
    UserProfile.getInstance().setHomeCityName("");
    if (looperTime != null) {
      looperTime.stop();
    }
    mService.observeRecentContact(mMessageObserver, false);
  }
}
