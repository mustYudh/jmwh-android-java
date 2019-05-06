package com.yu.location;

public interface LocationListener {
  /**
   * 请求定位结果，ILocation为空表示定位失败
   */
  void onReceive(ILocation location);
}