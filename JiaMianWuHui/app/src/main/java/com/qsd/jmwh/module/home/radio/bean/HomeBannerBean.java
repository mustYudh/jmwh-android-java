package com.qsd.jmwh.module.home.radio.bean;

import java.io.Serializable;
import java.util.List;

public class HomeBannerBean implements Serializable {


    /**
     * server_timestamp : 1568687383087
     * cdoList : [{"lBannerId":1,"sImg":"https://maskball.oss-cn-beijing.aliyuncs.com/0system/aaaaaaa.jpg","sInfo":"宣传图片","nType":0,"sTargetUrl":"","nLocation":0,"nSort":0,"nStatus":1,"dCreateTime":"2019-06-05 17:30:35","dUpdateTime":"2019-06-24 11:41:28"},{"lBannerId":2,"sImg":"https://maskball.oss-cn-beijing.aliyuncs.com/0system/7c9fc8d7-2717-4351-bf46-e779ba70e1ba_0.jpg","sInfo":"宣传图片2","nType":1,"sTargetUrl":"https://www.baidu.com/","nLocation":0,"nSort":0,"nStatus":1,"dCreateTime":"2019-06-05 17:32:31","dUpdateTime":"2019-06-24 11:41:30"},{"lBannerId":3,"sImg":"https://maskball.oss-cn-beijing.aliyuncs.com/0system/aaaaaaa.jpg","sInfo":"宣传图片3","nType":1,"sTargetUrl":"http://www.aliyun.com","nLocation":0,"nSort":0,"nStatus":1,"dCreateTime":"2019-06-05 17:32:31","dUpdateTime":"2019-06-24 11:41:31"}]
     * nCount : 3
     */

    public long server_timestamp;
    public int nCount;
    public List<CdoListBean> cdoList;

    public static class CdoListBean {
        /**
         * lBannerId : 1
         * sImg : https://maskball.oss-cn-beijing.aliyuncs.com/0system/aaaaaaa.jpg
         * sInfo : 宣传图片
         * nType : 0
         * sTargetUrl :
         * nLocation : 0
         * nSort : 0
         * nStatus : 1
         * dCreateTime : 2019-06-05 17:30:35
         * dUpdateTime : 2019-06-24 11:41:28
         */

        public String lBannerId;
        public String sImg;
        public String sInfo;
        public String nType;
        public String sTargetUrl;
        public String nLocation;
        public String nSort;
        public String nStatus;
        public String dCreateTime;
        public String dUpdateTime;
    }
}
