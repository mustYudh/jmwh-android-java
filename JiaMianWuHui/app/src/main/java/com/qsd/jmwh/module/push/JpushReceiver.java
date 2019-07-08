package com.qsd.jmwh.module.push;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import com.google.gson.Gson;
import com.qsd.jmwh.R;

/**
 * @author changwei
 * @date 2017/8/2
 */
public class JpushReceiver extends BroadcastReceiver {

  public static final int PUSH_NOTIFY_ID = 1001;
  public static final String PUSH_PAGE = "push_page";

  private static final String TAG = "JpushReceiver";
  private static final String DATA = "message_type";

  @Override public void onReceive(Context context, Intent intent) {
    if (intent == null || intent.getExtras() == null) {
      return;
    }
    Bundle bundle = intent.getExtras();
    if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
      String clientId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
      Log.e(TAG, "onReceive: " + clientId);

    } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
      receivingNotification(context, JPushInterface.ACTION_MESSAGE_RECEIVED, bundle);
    } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
      receivingNotification(context, JPushInterface.ACTION_MESSAGE_RECEIVED, bundle);
    } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
      openNotification(context, bundle);
    } else {

    }
  }

  private void receivingNotification(Context context, String messageType, Bundle bundle) {
    bundle.putString(DATA, messageType);
    if (messageType.equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
      String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
      String message = bundle.getString(JPushInterface.EXTRA_ALERT);
      NotificationBean bean = new NotificationBean();
      bean.title = title;
      bean.message = message;
      showNotification(context, bean);
    } else {
      String data = bundle.getString(JPushInterface.EXTRA_MESSAGE);
      if (TextUtils.isEmpty(data)) {
        return;
      }
      NotificationBean bean;
      try {
        bean = new Gson().fromJson(data, NotificationBean.class);
        showNotification(context, bean);
      } catch (Exception e) {
        showNotification(context, new NotificationBean());
      }
    }
  }

  private void openNotification(Context context, Bundle bundle) {
  }

  /**
   * 显示通知
   */
  public void showNotification(Context context, NotificationBean bean) {
    if (bean == null || TextUtils.isEmpty(bean.message)) {
      return;
    }
    NotificationCompat.Builder mBuilder =
        new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_launcher)
            .setLargeIcon(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentTitle(bean.title)
            .setContentText(bean.message)
            .setTicker(bean.title);
    Bundle bundle = new Bundle();
    //bundle.putString(DialogActivity.FROM, PUSH_PAGE);
    bundle.putSerializable(PUSH_PAGE, bean);
    //Intent intent = DialogActivity.getIntent(context, bundle);
    Intent intent = new 
    PendingIntent pendingIntent = PendingIntent.getActivity(context, PUSH_NOTIFY_ID, intent,
        PendingIntent.FLAG_UPDATE_CURRENT);
    mBuilder.setContentIntent(pendingIntent);
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    if (notificationManager != null) {
      notificationManager.notify(PUSH_NOTIFY_ID, mBuilder.build());
    }
  }

  /**
   * 首页
   */
  private Intent goHomeIntent(Context context) {
    Intent intent;
    //是本应用但是不是SplashActivity
    if (HomePageActivity.exist) {
      intent = new Intent(context, HomePageActivity.class);
    } else {
      intent = new Intent(context, SplashActivity.class);
    }
    return intent;
  }
}
