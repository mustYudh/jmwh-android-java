package com.qsd.jmwh.module.home.radio.bean;

import java.io.Serializable;
import java.util.List;

public class HomeRadioListBean implements Serializable {


    public long server_timestamp;
    public int nRecordCount;
    public List<CdoListBean> cdoList;

    public static class CdoListBean {
        /**
         * lDatingId : 9
         * lUserId : 8
         * sDatingTitle : sDatingTitle
         * sDatingRange : sDatingRange
         * sDatingTime : 2019-03-04
         * sDatingTimeExt : 中午
         * sContent : sContent
         * nSex : 1
         * nLikeCount : 0
         * nApplyCount : 0
         * bCommentType : true
         * bHiddenType : false
         * dCreateTime : 2019-04-16 18:15:14
         * nRecommendType : 0
         * distance_um : 6146976
         * cdoUserData : {"sNickName":"","sUserHeadPic":"","nAuthType":0}
         * sImg : 3
         */

        public int lDatingId;
        public int lUserId;
        public String sDatingTitle;
        public String sDatingRange;
        public String sDatingTime;
        public String sDatingTimeExt;
        public String sContent;
        public int nSex;
        public int nLikeCount;
        public int nApplyCount;
        public boolean bCommentType;
        public boolean bHiddenType;
        public String dCreateTime;
        public int nRecommendType;
        public int distance_um;
        public CdoUserDataBean cdoUserData;
        public String sImg;

        public static class CdoUserDataBean {
            /**
             * sNickName :
             * sUserHeadPic :
             * nAuthType : 0
             */

            public String sNickName;
            public String sUserHeadPic;
            public int nAuthType;
        }
    }
}
