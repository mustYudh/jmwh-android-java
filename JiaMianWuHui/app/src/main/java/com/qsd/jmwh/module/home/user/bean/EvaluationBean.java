package com.qsd.jmwh.module.home.user.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class EvaluationBean implements Serializable {

    /**
     * server_timestamp : 1557560362600
     * cdoList : [{"sName":"友好","nValue":"0"},{"sName":"有趣","nValue":"0"},{"sName":"爽快","nValue":"0"},{"sName":"耐心","nValue":"0"},{"sName":"高冷","nValue":"0"},{"sName":"暴脾气","nValue":"0"}]
     */

    public long server_timestamp;
    public ArrayList<CdoListBean> cdoList;




    public static class CdoListBean {
        /**
         * sName : 友好
         * nValue : 0
         */

        public String sName;
        public String nValue;
        public boolean selected;

    }
}
