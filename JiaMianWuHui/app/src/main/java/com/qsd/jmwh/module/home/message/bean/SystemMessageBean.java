package com.qsd.jmwh.module.home.message.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author yudneghao
 * @date 2019-06-24
 */
public class SystemMessageBean implements Serializable {

  /**
   * server_timestamp : 1561306215514
   * cdoList : [{"lMsgId":178,"sContent":"感谢您的举报，我们会对该用户进行跟踪监控核实，并给予相应的惩罚！","nStatus":1,"nBizId":0,"dCreateTime":"2019-06-12
   * 16:54:10"},{"lMsgId":179,"sContent":"感谢您的举报，我们会对该用户进行跟踪监控核实，并给予相应的惩罚！","nStatus":1,"nBizId":0,"dCreateTime":"2019-06-12
   * 16:56:12"},{"lMsgId":180,"sContent":"感谢您的举报，我们会对该用户进行跟踪监控核实，并给予相应的惩罚！","nStatus":1,"nBizId":0,"dCreateTime":"2019-06-12
   * 17:31:48"},{"lMsgId":181,"sContent":"感谢您的举报，我们会对该用户进行跟踪监控核实，并给予相应的惩罚！","nStatus":1,"nBizId":0,"dCreateTime":"2019-06-12
   * 17:31:52"},{"lMsgId":182,"sContent":"感谢您的举报，我们会对该用户进行跟踪监控核实，并给予相应的惩罚！","nStatus":1,"nBizId":0,"dCreateTime":"2019-06-12
   * 18:03:50"},{"lMsgId":183,"sContent":"感谢您的举报，我们会对该用户进行跟踪监控核实，并给予相应的惩罚！","nStatus":1,"nBizId":0,"dCreateTime":"2019-06-12
   * 18:23:20"},{"lMsgId":184,"sContent":"有人点赞了您的广播请点击查看","nStatus":1,"nBizId":82,"dCreateTime":"2019-06-13
   * 11:32:24","lUserId":12,"sNickName":"大黄蜂。","sUserHeadPic":"http://maskball.oss-cn-beijing.aliyuncs.com/12/head_3d72de22-6ddd-4242-a2b1-6335af7db4d3.jpg"},{"lMsgId":185,"sContent":"有人点赞了您的广播请点击查看","nStatus":1,"nBizId":82,"dCreateTime":"2019-06-13
   * 11:33:33","lUserId":12,"sNickName":"大黄蜂。","sUserHeadPic":"http://maskball.oss-cn-beijing.aliyuncs.com/12/head_3d72de22-6ddd-4242-a2b1-6335af7db4d3.jpg"},{"lMsgId":186,"sContent":"有人点赞了您的广播请点击查看","nStatus":1,"nBizId":80,"dCreateTime":"2019-06-13
   * 13:50:04","lUserId":20,"sNickName":"你","sUserHeadPic":"https://maskball.oss-cn-beijing.aliyuncs.com/20/gallery_FDF20EF1-3ABD-4295-ADDB-DB8E29F7D09A.jpg"},{"lMsgId":187,"sContent":"有人点赞了您的广播请点击查看","nStatus":1,"nBizId":80,"dCreateTime":"2019-06-13
   * 13:50:05","lUserId":20,"sNickName":"你","sUserHeadPic":"https://maskball.oss-cn-beijing.aliyuncs.com/20/gallery_FDF20EF1-3ABD-4295-ADDB-DB8E29F7D09A.jpg"}]
   * nCount : 0
   * nRecordCount : 10
   */

  public long server_timestamp;
  public int nCount;
  public int nRecordCount;
  public List<CdoListBean> cdoList;

  public static class CdoListBean implements Serializable {
    /**
     * lMsgId : 178
     * sContent : 感谢您的举报，我们会对该用户进行跟踪监控核实，并给予相应的惩罚！
     * nStatus : 1
     * nBizId : 0
     * dCreateTime : 2019-06-12 16:54:10
     * lUserId : 12
     * sNickName : 大黄蜂。
     * sUserHeadPic : http://maskball.oss-cn-beijing.aliyuncs.com/12/head_3d72de22-6ddd-4242-a2b1-6335af7db4d3.jpg
     */

    public int lMsgId;
    public String sContent;
    public int nStatus;
    public int nBizId;
    public String dCreateTime;
    public int lUserId;
    public String sNickName;
    public String sUserHeadPic;
  }
}
