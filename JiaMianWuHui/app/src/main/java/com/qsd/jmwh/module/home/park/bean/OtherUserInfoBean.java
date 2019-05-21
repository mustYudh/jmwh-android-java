package com.qsd.jmwh.module.home.park.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class OtherUserInfoBean implements Serializable {

  /**
   * server_timestamp : 1556958215187
   * nSubViewUserCount : 998
   * bOpenImg : false
   * bVIP : true
   * cdoUserData : {"lUserId":1,"sNickName":"测试女号修改后","sUserHeadPic":"http://maskball.oss-cn-beijing.aliyuncs.com/1/head_cbc044a5-2c51-4e4b-bc1c-5f07eb132516.jpg","sCity":"杭州市","sDateRange":"上海市","sAge":"19岁","sJob":"无","sDatePro":"旅游;吃吃喝喝;去唱歌;看电影","QQ":"9767634","WX":"5767634","nSex":0,"sBust":"33A","sHeight":"180CM","sWeight":"60KG","sIntroduce":"人哈哈啊哈哈","dGalaryVal":20,"nAuthType":2,"nOffLineMin":61784,"nOnLine":1,"distance_um":15130672,"sAuthInfo":"你通过了VIP特权用户的身份安全审核"}
   * cdoFileListData : [{"lFileId":2,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":2,"nFileFee":10,"bView":1},{"lFileId":3,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":2,"nFileFee":3,"bView":1},{"lFileId":4,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":0,"nFileFee":0,"bView":1},{"lFileId":5,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":0,"nFileFee":0,"bView":1},{"lFileId":6,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":0,"nFileFee":0,"bView":1},{"lFileId":12,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":0,"nFileFee":0,"bView":1},{"lFileId":13,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":0,"nFileFee":0,"bView":1},{"lFileId":14,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":0,"nFileFee":0,"bView":1},{"lFileId":15,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":0,"nFileFee":0,"bView":1},{"lFileId":16,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":0,"nFileFee":0,"bView":1},{"lFileId":24,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":0,"nFileFee":0,"bView":1},{"lFileId":39,"sFileUrl":"http://maskball.oss-cn-beijing.aliyuncs.com/1/head_503d0144-3a1c-453a-a1f5-fcbf3dbf9ba5.jpg","nFileType":1,"nFileFee":0,"bView":1},{"lFileId":42,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/1/gallery_9116022D-2D6D-4296-BB98-BF3D31308625.jpg","nFileType":1,"nFileFee":0,"bView":1},{"lFileId":44,"sFileUrl":"http://maskball.oss-cn-beijing.aliyuncs.com/1/head_eb576f8d-3a75-4f50-aa4d-3f19e7087699.jpg","nFileType":0,"nFileFee":0,"bView":1}]
   * bExistDating : false
   */

  public long server_timestamp;
  public int nSubViewUserCount;
  public boolean bOpenImg;
  public boolean bVIP;
  public CdoUserDataBean cdoUserData;
  public boolean bExistDating;
  public ArrayList<CdoFileListDataBean> cdoFileListData;

  public static class CdoUserDataBean {
    /**
     * lUserId : 1
     * sNickName : 测试女号修改后
     * sUserHeadPic : http://maskball.oss-cn-beijing.aliyuncs.com/1/head_cbc044a5-2c51-4e4b-bc1c-5f07eb132516.jpg
     * sCity : 杭州市
     * sDateRange : 上海市
     * sAge : 19岁
     * sJob : 无
     * sDatePro : 旅游;吃吃喝喝;去唱歌;看电影
     * QQ : 9767634
     * WX : 5767634
     * nSex : 0
     * sBust : 33A
     * sHeight : 180CM
     * sWeight : 60KG
     * sIntroduce : 人哈哈啊哈哈
     * dGalaryVal : 20
     * nAuthType : 2
     * nOffLineMin : 61784
     * nOnLine : 1
     * distance_um : 15130672
     * sAuthInfo : 你通过了VIP特权用户的身份安全审核
     */

    public int lUserId;
    public String sNickName;
    public String sUserHeadPic;
    public String sCity;
    public String sDateRange;
    public String sAge;
    public String sJob;
    public String sDatePro;
    public String QQ;
    public String WX;
    public int nSex;
    public String sBust;
    public String sHeight;
    public String sWeight;
    public String sIntroduce;
    public int dGalaryVal;
    public int nAuthType;
    public int nOffLineMin;
    public int nOnLine;
    public int distance_um;
    public String sAuthInfo;
  }

  public static class CdoFileListDataBean implements Serializable {
    /**
     * lFileId : 2
     * sFileUrl : https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg
     * nFileType : 2
     * nFileFee : 10
     * bView : 1
     */

    public int lFileId;
    public String sFileUrl;
    public String sFileCoverUrl;
    public int nFileType;
    public int nFileFee;
    public int bView;
    public int nStatus;
  }
}
