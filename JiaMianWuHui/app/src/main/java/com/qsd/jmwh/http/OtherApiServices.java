package com.qsd.jmwh.http;

import com.qsd.jmwh.module.home.user.bean.EvaluationBean;
import com.xuexiang.xhttp2.annotation.NetMethod;

import io.reactivex.Observable;


public interface OtherApiServices {

    @NetMethod(ParameterNames = {"lUserId"},Url = "/UserEvaluateService/getUserValiateList")
    Observable<EvaluationBean> getEvaluation(int lUserId);


    @NetMethod(ParameterNames = {"lBuyOtherUserId","nPayType","nPayVal"},Url = "/OrderService/getBuyGalleryPaySign")
    Observable<Object> buyGalleryPay(int lBuyOtherUserId,int nPayType,int nPayVal);
}
