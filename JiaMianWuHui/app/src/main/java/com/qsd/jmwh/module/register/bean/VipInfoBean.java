package com.qsd.jmwh.module.register.bean;

import java.io.Serializable;
import java.util.List;

public class VipInfoBean implements Serializable {


    /**
     * server_timestamp : 1555513922299
     * cdoList : [{"lGoodsId":1,"sGoodsName":"半个月","nGoodsRealFee":236,"nGoodsSaleFee":118,"sGoodsHeadPic":"","bRecommend":false,"nGoodsType":1,"sGoodsAttribute":"0.5"},{"lGoodsId":2,"sGoodsName":"一个月","nGoodsRealFee":178,"nGoodsSaleFee":178,"sGoodsHeadPic":"","bRecommend":false,"nGoodsType":1,"sGoodsAttribute":"1"},{"lGoodsId":3,"sGoodsName":"三个月","nGoodsRealFee":129,"nGoodsSaleFee":388,"sGoodsHeadPic":"","bRecommend":true,"nGoodsType":1,"sGoodsAttribute":"3"},{"lGoodsId":4,"sGoodsName":"六个月","nGoodsRealFee":92,"nGoodsSaleFee":548,"sGoodsHeadPic":"","bRecommend":false,"nGoodsType":1,"sGoodsAttribute":"6"}]
     * nCount : 4
     * nPayTypeList : ["1","2","3","4"]
     * nMoney : 740.0
     * nMaskBallCoin : 1000
     * sVIPPrivilegeList : ["1. 每天不限次数查看用户","2. 每天10次免费机会查看付费相册或社交账户","3. 查看阅后即焚照片的时间从2秒提升到6秒","4. 免费发布约会广播"]
     */

    public int nCount;
    public String nMoney;
    public String nMaskBallCoin;
    public List<CdoListBean> cdoList;
    public List<String> nPayTypeList;
    public List<String> sVIPPrivilegeList;



    public static class CdoListBean {
        /**
         * lGoodsId : 1
         * sGoodsName : 半个月
         * nGoodsRealFee : 236
         * nGoodsSaleFee : 118
         * sGoodsHeadPic :
         * bRecommend : false
         * nGoodsType : 1
         * sGoodsAttribute : 0.5
         */

        public int lGoodsId;
        public String sGoodsName;
        public double nGoodsRealFee;
        public double nGoodsSaleFee;
        public String sGoodsHeadPic;
        public boolean bRecommend;
        public int nGoodsType;
        public String sGoodsAttribute;
        public boolean selected;





    }
}
