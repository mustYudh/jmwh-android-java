package com.qsd.jmwh.module.home.park.bean;

import java.io.Serializable;
import java.util.List;

public class HomePersonListBean implements Serializable {


    public long server_timestamp;
    public int nRecordCount;
    public int nNotExistGalaryCount;
    public List<CdoListBean> cdoList;

    public static class CdoListBean {
        /**
         * lUserId : 2
         * sNickName : sNickName
         * sUserHeadPic :
         * sDateRange : sDateRange
         * sAge : sAge
         * sJob : sJob
         * sDatePro : sDatePro
         * nGalaryCapacity : 1
         * nAuthType : 0
         * nOffLineMin : 41577
         * nInfoSet : 0
         * nOnLine : 1
         * bHiddenRang : false
         * bVip : false
         * bHiddenQQandWX : true
         * distance_um : 7703007
         * blove : false
         */

        public int lUserId;
        public int nSex;
        public String sNickName;
        public String sUserHeadPic;
        public String sDateRange;
        public String sCity;
        public String sAge;
        public String sJob;
        public String sDatePro;
        public int nGalaryCapacity;
        public int nAuthType;
        public long nOffLineMin;
        public int nInfoSet;
        public int nOnLine;
        public boolean bHiddenRang;
        public boolean bVip;
        public boolean bHiddenQQandWX;
        public int distance_um;
        public boolean blove;
    }
}
