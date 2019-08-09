package com.qsd.jmwh.module.home.user.bean;

import java.io.Serializable;
import java.util.List;

public class MineLikeBean implements Serializable {

    /**
     * server_timestamp : 1557150962737
     * cdoList : [{"lUserId":18,"sNickName":"大帅比","sUserHeadPic":"sUserHeadPic","sDateRange":"北京市","sAge":"18","sJob":"网络游戏","nOnLine":0,"dOffLineTime":"2019-03-22 18:39:51","distance_um":10891022}]
     * nRecordCount : 1
     */

    public long server_timestamp;
    public int nRecordCount;
    public List<CdoListBean> cdoList;

    public static class CdoListBean {
        /**
         * lUserId : 18
         * sNickName : 大帅比
         * sUserHeadPic : sUserHeadPic
         * sDateRange : 北京市
         * sAge : 18
         * sJob : 网络游戏
         * nOnLine : 0
         * dOffLineTime : 2019-03-22 18:39:51
         * distance_um : 10891022
         */

        public int lUserId;
        public String sNickName;
        public String sUserHeadPic;
        public String sDateRange;
        public String sAge;
        public String sJob;
        public int nOnLine;
        public String dOffLineTime;
        public int distance_um;
        public int nAuthType;
        public int nGalaryCapacity;
        public boolean blove;
        public boolean is_black;
        public long nOffLineMin;
        public int nSex;
        public boolean bVip;
    }
}
