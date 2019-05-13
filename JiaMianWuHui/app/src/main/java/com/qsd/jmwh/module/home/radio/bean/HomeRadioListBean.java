package com.qsd.jmwh.module.home.radio.bean;

import java.io.Serializable;
import java.util.List;

public class HomeRadioListBean implements Serializable {


    public long server_timestamp;
    public boolean bVIP;
    public int nRecordCount;
    public List<CdoListBean> cdoList;

    public static class CdoListBean {
        /**
         * lDatingId : 7
         * lUserId : 8
         * sDatingTitle : 一起去玩啊
         * sDatingHope :
         * sDatingRange : 北京
         * sDatingTime : 2019-03-04
         * sDatingTimeExt : 中午
         * sContent : 玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊玩啊
         * sImg : ["https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg","https://maskball.oss-cn-beijing.aliyuncs.com/headpic/222.jpg"]
         * nSex : 0
         * nLikeCount : 1000
         * nApplyCount : 889
         * nCommentCount : 111
         * bCommentType : true
         * bHiddenType : false
         * dCreateTime : 2019-04-11 16:58:44
         * nRecommendType : 0
         * nStatus : 0
         * distance_um : 6137884
         * cdoUserData : {"lUserId":8,"sNickName":"测试名字","sUserHeadPic":"https://maskball.oss-cn-beijing.aliyuncs.com/headpic/2.jpg","nAuthType":0,"bVIP":false}
         * bLove : 0
         * bApply : 1
         */

        public int lDatingId;
        public int lUserId;
        public String sDatingTitle;
        public String sDatingHope;
        public String sDatingRange;
        public String sDatingTime;
        public String sDatingTimeExt;
        public String sContent;
        public int nSex;
        public int nLikeCount;
        public int nApplyCount;
        public int nCommentCount;
        public boolean bCommentType;
        public boolean bHiddenType;
        public String dCreateTime;
        public int nRecommendType;
        public int nStatus;
        public int distance_um;
        public CdoUserDataBean cdoUserData;
        public int bLove;
        public int bApply;
        public List<String> sImg;

        public static class CdoUserDataBean {
            /**
             * lUserId : 8
             * sNickName : 测试名字
             * sUserHeadPic : https://maskball.oss-cn-beijing.aliyuncs.com/headpic/2.jpg
             * nAuthType : 0
             * bVIP : false
             */

            public int lUserId;
            public String sNickName;
            public String sUserHeadPic;
            public int nAuthType;
            public boolean bVIP;
        }
    }
}
