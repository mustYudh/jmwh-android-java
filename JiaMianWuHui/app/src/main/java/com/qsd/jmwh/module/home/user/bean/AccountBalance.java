package com.qsd.jmwh.module.home.user.bean;

import java.io.Serializable;
import java.util.List;

public class AccountBalance implements Serializable {


    public CdoUserAccountListBean cdoUserAccountList;
    public int nRecordCount;
    public List<CdoAccountBalanceListBean> cdoAccountBalanceList;







    public static class CdoUserAccountListBean implements Serializable {
        /**
         * nMoney : 0
         * nMaskBallCoin : 9600
         * nRealMaskBallCoin : 9600
         * sAliPayAccount :
         * sAliPayName :
         */

        public int nMoney;
        public int nMaskBallCoin;
        public int nRealMaskBallCoin;
        public String sAliPayAccount;
        public String sAliPayName;



    }

    public static class CdoAccountBalanceListBean implements Serializable {
        /**
         * sRemark : 购买红包图片
         * dCreateTime : 2019-04-19 20:49
         * nPayFee : 5
         * nABType : 3
         * nStatus : 0
         * sNickName : 还哈哈
         * sUserHeadPic : https://maskball.oss-cn-beijing.aliyuncs.com/18/head_CD10FE41-800F-4770-A580-2B7F9BDEFE1A.jpg
         * lUserId : 18
         */

        public String sRemark;
        public String dCreateTime;
        public int nPayFee;
        public int nABType;
        public int nStatus;
        public String sNickName;
        public String sUserHeadPic;
        public int lUserId;



    }
}
