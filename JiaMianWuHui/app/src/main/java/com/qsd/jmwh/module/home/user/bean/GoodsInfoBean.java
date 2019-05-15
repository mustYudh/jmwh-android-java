package com.qsd.jmwh.module.home.user.bean;

import java.io.Serializable;
import java.util.List;

public class GoodsInfoBean implements Serializable {

    /**
     * server_timestamp : 1557933065072
     * cdoList : [{"lGoodsId":10,"sGoodsName":"60个假面币","nGoodsRealFee":0,"nGoodsSaleFee":0.01,"sGoodsHeadPic":"","bRecommend":false,"nGoodsType":8,"sGoodsAttribute":"60"},{"lGoodsId":11,"sGoodsName":"120个假面币","nGoodsRealFee":0,"nGoodsSaleFee":0.01,"sGoodsHeadPic":"","bRecommend":false,"nGoodsType":8,"sGoodsAttribute":"120"},{"lGoodsId":12,"sGoodsName":"300个假面币","nGoodsRealFee":0,"nGoodsSaleFee":0.01,"sGoodsHeadPic":"","bRecommend":false,"nGoodsType":8,"sGoodsAttribute":"300"},{"lGoodsId":13,"sGoodsName":"730个假面币","nGoodsRealFee":730,"nGoodsSaleFee":0.01,"sGoodsHeadPic":"","bRecommend":false,"nGoodsType":8,"sGoodsAttribute":"730"},{"lGoodsId":14,"sGoodsName":"1160个假面币","nGoodsRealFee":1160,"nGoodsSaleFee":0.01,"sGoodsHeadPic":"","bRecommend":false,"nGoodsType":8,"sGoodsAttribute":"1160"},{"lGoodsId":15,"sGoodsName":"1700个假面币","nGoodsRealFee":1700,"nGoodsSaleFee":0.01,"sGoodsHeadPic":"","bRecommend":false,"nGoodsType":8,"sGoodsAttribute":"1700"}]
     * nCount : 6
     */

    public int nCount;
    public List<CdoListBean> cdoList;


    public static class CdoListBean implements Serializable {
        /**
         * lGoodsId : 10
         * sGoodsName : 60个假面币
         * nGoodsRealFee : 0
         * nGoodsSaleFee : 0.01
         * sGoodsHeadPic :
         * bRecommend : false
         * nGoodsType : 8
         * sGoodsAttribute : 60
         */

        public int lGoodsId;
        public String sGoodsName;
        public int nGoodsRealFee;
        public double nGoodsSaleFee;
        public String sGoodsHeadPic;
        public boolean bRecommend;
        public int nGoodsType;
        public String sGoodsAttribute;


    }
}
