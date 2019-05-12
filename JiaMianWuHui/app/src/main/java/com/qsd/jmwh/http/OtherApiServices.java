package com.qsd.jmwh.http;

import com.qsd.jmwh.module.home.user.bean.EvaluationBean;
import com.xuexiang.xhttp2.annotation.NetMethod;

import io.reactivex.Observable;


public interface OtherApiServices {

    @NetMethod(ParameterNames = {"lUserId"},Url = "/UserEvaluateService/getUserValiateList")
    Observable<EvaluationBean> getEvaluation(int lUserId);


    @NetMethod(ParameterNames = {"lBuyOtherUserId","nPayType","nPayVal"},Url = "/OrderService/getBuyGalleryPaySign")
    Observable<Object> buyGalleryPay(int lBuyOtherUserId,int nPayType,int nPayVal);


    @NetMethod(ParameterNames = {"lBuyImgId","nPayVal","nPayType"},Url = "/OrderService/getBuyImgPaySign")
    Observable<Object> getBuyImgPaySign(int lBuyImgId,int nPayVal,int nPayType);



    @NetMethod(ParameterNames = {"lBuyOtherUserId","nPayType","nPayVal"},Url = "/OrderService/getBuyContactPaySign")
    Observable<Object> getBuyContactPaySign(int lBuyOtherUserId,int nPayType,int nPayVal);


    @NetMethod(ParameterNames = {"lLoveUserId","nType"}, Url = "/UserLoveService/delLoveUser")
    Observable<Object> toBlackList(int lLoveUserId,int nType);


    @NetMethod(ParameterNames = {"lUserId","lBrowseInfoId","nPayType","nBrowseInfType"},Url = "/MaskballService/addBrowsingHis")
    Observable<Object> addBrowsingHis(int lUserId,int lBrowseInfoId,int nPayType,int nBrowseInfType);

    @NetMethod(ParameterNames = {"nLat","nLng"},Url = "/MaskballService/destroyImgBrowsingHis")
    Observable<Object> destroyImgBrowsingHis(float nLat,float nLng);
}
