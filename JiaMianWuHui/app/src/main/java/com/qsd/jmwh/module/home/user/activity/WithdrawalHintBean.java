package com.qsd.jmwh.module.home.user.activity;

import java.io.Serializable;

/**
 * @author yudneghao
 * @date 2019-06-28
 */
public class WithdrawalHintBean implements Serializable {

  /**
   * server_timestamp : 1561714169918
   * sText1 : 假面币可用于相关打赏服务，你可以通过充值或礼物等方式获得假面币，还可以兑换成现金，兑换比例(13:1)
   * sText2 : 提现的假面币必须是1300的整数倍，最低提现金额100元
   */

  public long server_timestamp;
  public String sText1;
  public String sText2;


}
