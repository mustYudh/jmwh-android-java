package com.yu.location;


public interface ILocationClient {

  /**
   * 开始定位
   */
  void start(LocationListener locationListener);

  /**
   * @param useIpIfNull 获取不到的时候使用IP定位
   */
  void start(LocationListener locationListener, boolean useIpIfNull);

  /**
   * 定制定位
   */
  void stop();
}