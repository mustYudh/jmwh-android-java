package com.qsd.jmwh.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import java.util.List;

/**
 * @author chenwei
 * @date 2017/10/30
 */
public class AppLifeCycle implements Application.ActivityLifecycleCallbacks {

  private boolean inBackground = true;

  @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

  }

  @Override public void onActivityStarted(Activity activity) {

  }

  @Override public void onActivityResumed(Activity activity) {
    if (inBackground && activity != null) {

    }
    inBackground = false;
    if (activity != null) {

    }
  }

  @Override public void onActivityPaused(Activity activity) {

  }

  @Override public void onActivityStopped(Activity activity) {
    if (activity != null && isApplicationInBackground(activity.getApplicationContext())) {
      inBackground = true;
    } else {
      inBackground = false;
    }
    if (activity != null) {

    }
  }

  @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

  }

  @Override public void onActivityDestroyed(Activity activity) {
    
  }

  /**
   * 判断当前应用程序处于前台还是后台
   */
  public static boolean isApplicationInBackground(final Context context) {
    android.app.ActivityManager am = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningTaskInfo> tasks = am != null ? am.getRunningTasks(1) : null;
    if (tasks != null && !tasks.isEmpty()) {
      ComponentName topActivity = tasks.get(0).topActivity;
      if (!topActivity.getPackageName().equals(context.getPackageName())
          && !topActivity.getPackageName().startsWith("com.android.")) {
        return true;
      }
    }
    return false;
  }
}
