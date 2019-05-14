package com.qsd.jmwh.module.home.user.bean;

import java.io.Serializable;

public class PrivacySettingStatusBean implements Serializable {

    /**
     * server_timestamp : 1557645936989
     * cdoData : {"bHiddenRang":true,"bHiddenQQandWX":true,"nInfoSet":0}
     */

    public CdoDataBean cdoData;


    public static class CdoDataBean {
        /**
         * bHiddenRang : true
         * bHiddenQQandWX : true
         * nInfoSet : 0
         */

        public boolean bHiddenRang;
        public boolean bHiddenQQandWX;
        public int nInfoSet;


    }
}
