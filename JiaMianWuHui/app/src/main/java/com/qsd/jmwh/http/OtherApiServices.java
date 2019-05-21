package com.qsd.jmwh.http;

import com.qsd.jmwh.module.home.user.bean.AccountBalance;
import com.qsd.jmwh.module.home.user.bean.EvaluationBean;
import com.qsd.jmwh.module.home.user.bean.GoodsInfoBean;
import com.qsd.jmwh.module.home.user.bean.MaskBallCoinBean;
import com.qsd.jmwh.module.home.user.bean.PayInfoBean;
import com.qsd.jmwh.module.home.user.bean.PrivacySettingStatusBean;
import com.qsd.jmwh.module.home.user.bean.PushSettingBean;
import com.qsd.jmwh.module.home.user.bean.WomenVideoBean;
import com.qsd.jmwh.module.register.bean.PayInfo;
import com.xuexiang.xhttp2.annotation.NetMethod;

import io.reactivex.Observable;

public interface OtherApiServices {

  @NetMethod(ParameterNames = { "lUserId" }, Url = "/UserEvaluateService/getUserValiateList")
  Observable<EvaluationBean> getEvaluation(int lUserId);

  @NetMethod(ParameterNames = {
      "lBuyOtherUserId", "nPayType", "nPayVal"
  }, Url = "/OrderService/getBuyGalleryPaySign") Observable<Object> buyGalleryPay(
      int lBuyOtherUserId, int nPayType, int nPayVal);

  @NetMethod(ParameterNames = {
      "lBuyImgId", "nPayVal", "nPayType"
  }, Url = "/OrderService/getBuyImgPaySign") Observable<Object> getBuyImgPaySign(int lBuyImgId,
      int nPayVal, int nPayType);

  @NetMethod(ParameterNames = {
      "lBuyOtherUserId", "nPayType", "nPayVal"
  }, Url = "/OrderService/getBuyContactPaySign") Observable<Object> getBuyContactPaySign(
      int lBuyOtherUserId, int nPayType, int nPayVal);

  @NetMethod(ParameterNames = { "lLoveUserId", "nType" }, Url = "/UserLoveService/delLoveUser")
  Observable<Object> toBlackList(int lLoveUserId, int nType);

  @NetMethod(ParameterNames = {
      "lUserId", "lBrowseInfoId", "nPayType", "nBrowseInfType"
  }, Url = "/MaskballService/addBrowsingHis") Observable<Object> addBrowsingHis(int lUserId,
      int lBrowseInfoId, int nPayType, int nBrowseInfType);

  @NetMethod(ParameterNames = { "nLat", "nLng" }, Url = "/MaskballService/destroyImgBrowsingHis")
  Observable<Object> destroyImgBrowsingHis(float nLat, float nLng);

  @NetMethod(Url = "/MaskballService/getUserCofig") Observable<PushSettingBean> getUserConfig();

  @NetMethod(ParameterNames = {
      "nSetting1", "nSetting2", "nSetting3", "nSetting4"
  }, Url = "/MaskBallService/setUserCofig") Observable<PushSettingBean> setUserCofig(int nSetting1,
      int nSetting2, int nSetting3, int nSetting4);

  @NetMethod(Url = "/MaskBallService/getUserPrivacy")
  Observable<PrivacySettingStatusBean> getUserPrivacy();

  @NetMethod(ParameterNames = {
      "nType", "nStatus", "dGalaryVal"
  }, Url = "/MaskBallService/setUserPrivacy") Observable<Object> setUserPrivacy(int nType,
      int nStatus, int dGalaryVal);

  @NetMethod(Url = "/OrderService/getPayInfo") Observable<PayInfoBean> getPayInfo();

  @NetMethod(ParameterNames = {
      "nAccountype", "pageindex"
  }, Url = "/AccountBalanceService/getAccountBalanceList")
  Observable<AccountBalance> getAccountBalance(int nAccountype, int pageindex);

  @NetMethod(ParameterNames = {
      "nPayFee"
  }, Url = "/AccountBalanceService/getMaskBallCoinConvertMoney")
  Observable<MaskBallCoinBean> getCoinConvertMoney(int nPayFee);

  @NetMethod(ParameterNames = { "nPayFee" }, Url = "/AccountBalanceService/withdrawMaskBallCoin")
  Observable<Object> withdrawMaskBallCoin(int nPayFee);

  @NetMethod(ParameterNames = {
      "sAliPayAccount", "sAliPayName"
  }, Url = "/AccountBalanceService/modifyAliPay") Observable<Object> modifyAliPay(
      String sAliPayAccount, String sAliPayName);

  @NetMethod(ParameterNames = { "nGoodsType" }, Url = "/GoodsService/getGoods")
  Observable<GoodsInfoBean> getGoods(int nGoodsType);

  @NetMethod(ParameterNames = { "lGoodsId", "nPayType" }, Url = "/OrderService/getBuyDatingPaySign")
  Observable<PayInfo> getBuyDatingPaySign(int lGoodsId, int nPayType);

  @NetMethod(ParameterNames = {
      "nFileType", "nFileFee", "lFileId"
  }, Url = "/MaskballService/modifyFile") Observable<PayInfo> modifyFile(int nFileType,
      int nFileFee, int lFileId);

  @NetMethod(Url = "/MaskballService/getWomenVedio") Observable<WomenVideoBean> getWomenVideo();

  @NetMethod(ParameterNames = { "sFileUrl", "sFileCoverUrl" }, Url = "/UserService/userAuthByVideo")
  Observable<Object> userAuthByVideo(String sFileUrl, String sFileCoverUrl);

  @NetMethod(ParameterNames = "lInitiatorId",Url = "/UserEvaluateService/updateValiate")
  Observable<Object> updateValiate(int sFileUrl, String sFileCoverUrl);
}
