package com.qsd.jmwh.module.home.radio.bean;

import java.io.Serializable;
import java.util.List;

public class GetDatingUserVipBean implements Serializable {


    public long server_timestamp;
    public int nSex;
    public int nAuthType;
    public boolean bVIP;
    public int nCount;
    public List<CdoListBean> cdoList;

    public static class CdoListBean {
        /**
         * lGoodsId : 5
         * sGoodsName : 非VIP用户购买广播-男
         * nGoodsRealFee : 10
         * nGoodsSaleFee : 10
         * sGoodsHeadPic :
         * bRecommend : false
         * nGoodsType : 3
         * sGoodsAttribute :
         */

        public int lGoodsId;
        public String sGoodsName;
        public int nGoodsRealFee;
        public int nGoodsSaleFee;
        public String sGoodsHeadPic;
        public boolean bRecommend;
        public int nGoodsType;
        public String sGoodsAttribute;
    }
}
