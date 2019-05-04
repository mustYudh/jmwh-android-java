package com.qsd.jmwh.module.home.user.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class UserCenterInfo implements Serializable {

    /**
     * server_timestamp : 1555951643197
     * cdoUser : {"lUserId":18,"sNickName":"测试专属账户-男","sUserHeadPic":"sUserHeadPic","sAge":"18","sDateRange":"北京市","sJob":"学生","sCity":"北京","sDatePro":"看电影/吃吃喝喝","bVIP":true,"nAuthType":"2","sAuthInfo":"你通过了VIP特权用户的身份安全审核","sIntroduce":"这个人很懒，什么也没留下"}
     * cdoWalletData : {"nMoney":390,"nMaskBallCoin":1000}
     * cdoimgList : [{"lFileId":18,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":2,"nFileFee":10,"nAttribute":0},{"lFileId":19,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":1,"nFileFee":0,"nAttribute":0},{"lFileId":20,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":1,"nFileFee":0,"nAttribute":0},{"lFileId":21,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":0,"nFileFee":0,"nAttribute":0},{"lFileId":22,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":0,"nFileFee":0,"nAttribute":0},{"lFileId":23,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","nFileType":0,"nFileFee":0,"nAttribute":0},{"lFileId":25,"sFileUrl":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/auth_uuid.mp4","nFileType":0,"nFileFee":0,"nAttribute":1}]
     * nDestroyImgCount : 0
     * nViewCount : 1
     */

    public long server_timestamp;
    public CdoUserBean cdoUser;
    public CdoWalletDataBean cdoWalletData;
    public int nDestroyImgCount;
    public int nViewCount;
    public ArrayList<CdoimgListBean> cdoimgList;


    public static class CdoUserBean {
        /**
         * lUserId : 18
         * sNickName : 测试专属账户-男
         * sUserHeadPic : sUserHeadPic
         * sAge : 18
         * sDateRange : 北京市
         * sJob : 学生
         * sCity : 北京
         * sDatePro : 看电影/吃吃喝喝
         * bVIP : true
         * nAuthType : 2
         * sAuthInfo : 你通过了VIP特权用户的身份安全审核
         * sIntroduce : 这个人很懒，什么也没留下
         */
        public String dVIPInvalidTime;
        public int lUserId;
        public String sNickName;
        public String sUserHeadPic;
        public String sAge;
        public String sDateRange;
        public String sJob;
        public String sCity;
        public String sDatePro;
        public boolean bVIP;
        public String nAuthType;
        public String sAuthInfo;
        public String sIntroduce;
        public String sMobile;

    }

    public static class CdoWalletDataBean {
        /**
         * nMoney : 390
         * nMaskBallCoin : 1000
         */

        public int nMoney;
        public int nMaskBallCoin;


    }

    public static class CdoimgListBean {
        /**
         * lFileId : 18
         * sFileUrl : https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg
         * nFileType : 2
         * nFileFee : 10
         * nAttribute : 0
         */

        public int lFileId;
        public String sFileUrl;
        public int nFileType;
        public int nFileFee;
        public int nAttribute;
        public boolean last;

        @Override
        public String toString() {
            return "CdoimgListBean{" +
                    "lFileId=" + lFileId +
                    ", sFileUrl='" + sFileUrl + '\'' +
                    ", nFileType=" + nFileType +
                    ", nFileFee=" + nFileFee +
                    ", nAttribute=" + nAttribute +
                    ", last=" + last +
                    '}';
        }
    }
}
