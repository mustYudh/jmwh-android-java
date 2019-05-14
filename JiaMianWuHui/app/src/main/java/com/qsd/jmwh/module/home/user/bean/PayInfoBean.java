package com.qsd.jmwh.module.home.user.bean;

import java.io.Serializable;
import java.util.List;

public class PayInfoBean implements Serializable {

    /**
     * server_timestamp : 1557766612896
     * nPayTypeList : ["1","2","3"]
     * cdoData : {"nMoney":389.98,"nMaskBallCoin":700}
     */

    public long server_timestamp;
    public CdoDataBean cdoData;
    public List<String> nPayTypeList;


    public static class CdoDataBean implements Serializable{
        /**
         * nMoney : 389.98
         * nMaskBallCoin : 700
         */

        public double nMoney;
        public int nMaskBallCoin;


    }
}
