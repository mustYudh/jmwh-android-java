package com.qsd.jmwh.module.home.user.bean;

import java.io.Serializable;

public class PushSettingBean implements Serializable {

    /**
     * server_timestamp : 1557641405216
     * cdoData : {"lUserId":1,"nSetting1":1,"nSetting2":1,"nSetting3":1,"nSetting4":1,"dCreateTime":"2019-05-05 16:38:22","dUpdateTime":"2019-05-05 16:41:18","nSex":0}
     * nCount : 1
     */

    public CdoDataBean cdoData;
    public int nCount;



    public static class CdoDataBean {
        /**
         * lUserId : 1
         * nSetting1 : 1
         * nSetting2 : 1
         * nSetting3 : 1
         * nSetting4 : 1
         * dCreateTime : 2019-05-05 16:38:22
         * dUpdateTime : 2019-05-05 16:41:18
         * nSex : 0
         */

        public int lUserId;
        public int nSetting1;
        public int nSetting2;
        public int nSetting3;
        public int nSetting4;
        public String dCreateTime;
        public String dUpdateTime;
        public int nSex;

    }
}
