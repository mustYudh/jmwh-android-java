package com.yu.location.impl;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.yu.location.ILocation;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import org.json.JSONObject;


public class IpGetLocation {
  private static final String TAG = "IpGetLocation";

  private IpGetLocation() {
  }

  public static void getLocationByIP(final IpLocCallback ipLocCallback, final long id) {

    AsyncTask.execute(new Runnable() {
      @Override public void run() {

        try {
          JSONObject ipJson = new JSONObject(new IpGetLocation().requestGet(null));
          JSONObject content = ipJson.optJSONObject("content");
          JSONObject point = content == null ? null : content.optJSONObject("point");
          JSONObject addressDetail = content == null ? null : content.optJSONObject("address_detail");
          ILocation iLocation = null;

          if (point != null) {
            String lonStr = point.optString("x");
            String latStr = point.optString("y");

            if (!TextUtils.isEmpty(lonStr) && !TextUtils.isEmpty(latStr)) {
              iLocation = new ILocation();
              iLocation.setLatitude(Double.parseDouble(latStr));
              iLocation.setLongitude(Double.parseDouble(lonStr));
            }
          }

          if (addressDetail != null) {
            String city = addressDetail.getString("city");
            if (!TextUtils.isEmpty(city) ) {
              assert iLocation != null;
              iLocation.setCityName(city);
            }
          }

          final ILocation callbackILocation = iLocation;

          new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override public void run() {

              if (ipLocCallback != null) {
                ipLocCallback.getLoc(id, callbackILocation);
              }
            }
          });
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  private String requestGet( HashMap<String, String> paramsMap) {
    try {
      String baseUrl =
          "http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&coor=bd09ll";
      StringBuilder tempParams = new StringBuilder();
      int pos = 0;

      if (paramsMap != null) {
        for (String key : paramsMap.keySet()) {
          if (pos > 0) {
            tempParams.append("&");
          }
          tempParams.append(
              String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
          pos++;
        }
      }
      String requestUrl = baseUrl + tempParams.toString();
      // 新建一个URL对象
      URL url = new URL(requestUrl);
      // 打开一个HttpURLConnection连接
      HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
      // 设置连接主机超时时间
      urlConn.setConnectTimeout(5 * 1000);
      //设置从主机读取数据超时
      urlConn.setReadTimeout(5 * 1000);
      // 设置是否使用缓存  默认是true
      urlConn.setUseCaches(true);
      // 设置为Post请求
      urlConn.setRequestMethod("GET");
      //urlConn设置请求头信息
      //设置请求中的媒体类型信息。
      urlConn.setRequestProperty("Content-Type", "application/json");
      //设置客户端与服务连接类型
      urlConn.addRequestProperty("Connection", "Keep-Alive");
      // 开始连接
      urlConn.connect();

      String result = null;
      // 判断请求是否成功
      if (urlConn.getResponseCode() == 200) {
        // 获取返回的数据
        result = streamToString(urlConn.getInputStream());
        Log.e(TAG, "Get方式请求成功，result--->" + result);
      } else {
        Log.e(TAG, "Get方式请求失败");
      }
      // 关闭连接
      urlConn.disconnect();

      return result;
    } catch (Exception e) {
      Log.e(TAG, e.toString());
    }

    return null;
  }

  private String streamToString(InputStream is) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int len = 0;
      while ((len = is.read(buffer)) != -1) {
        baos.write(buffer, 0, len);
      }
      baos.close();
      is.close();
      byte[] byteArray = baos.toByteArray();
      return new String(byteArray);
    } catch (Exception e) {
      Log.e(TAG, e.toString());
      return null;
    }
  }
}
