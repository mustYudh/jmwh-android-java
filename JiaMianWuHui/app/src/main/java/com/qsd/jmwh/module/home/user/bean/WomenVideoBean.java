package com.qsd.jmwh.module.home.user.bean;

import java.io.Serializable;

/**
 * @author yudneghao
 * @date 2019-05-17
 */
public class WomenVideoBean implements Serializable {

  /**
   * server_timestamp : 1558080965639
   * nCheckStatus : 3
   * bVip : true
   * nSubViewVideoCount : 999
   * lFileId : 1
   * sFileUrl : https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg
   * sFileCoverUrl :
   */

  public long server_timestamp;
  public int nCheckStatus = -1;
  public boolean bVip;
  public int nSubViewVideoCount;
  public int lFileId;
  public String sFileUrl;
  public String sFileCoverUrl;

}
