package com.qsd.jmwh.module.home.radio.bean;

import java.io.Serializable;
import java.util.List;

public class GetRadioConfigListBean implements Serializable {


    public long server_timestamp;
    public int nRecordCount;
    public List<CdoListBean> cdoList;

    public static class CdoListBean {
        /**
         * lId : 1
         * sDatingConfigName : 看电影
         * sIconUrl : https://maskball.oss-cn-beijing.aliyuncs.com/icon/3.png
         */

        public int lId;
        public String sDatingConfigName;
        public String sIconUrl;
    }
}
