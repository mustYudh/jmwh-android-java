package com.qsd.jmwh.module.splash.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.params.PostParams;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.module.login.bean.LoginInfo;
import com.qsd.jmwh.module.register.EditRegisterCodeActivity;
import com.qsd.jmwh.module.register.EditUserDataActivity;
import com.qsd.jmwh.module.register.SelectGenderActivity;
import com.qsd.jmwh.utils.RxSchedulerUtils;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.model.ApiResult;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

public class SplashPresenter extends BaseViewPresenter<SplashViewer> {
    public SplashPresenter(SplashViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void auth(String uuid, String media) {
        XHttp.custom(ApiServices.class)
                .authLogin(PostParams.createParams()
                        .put("sAuthUUID", uuid)
                        .put("sLoginMode", media)
                        .creatBody())
                .compose(RxSchedulerUtils.<ApiResult<LoginInfo>>_io_main_o())
                .subscribeWith(new NoTipRequestSubscriber<ApiResult<LoginInfo>>() {
                    @Override
                    protected void onSuccess(ApiResult<LoginInfo> result) {
                        int code = result.getCode();
                        LoginInfo loginInfo = result.getData();
                        switch (code) {
                            case 0:
                                assert getViewer() != null;
                                getViewer().authLoginSuccess(result.getData());
                                break;
                            case 3:
                                getLauncherHelper().startActivity(SelectGenderActivity.getIntent(getActivity(), loginInfo.lUserId, loginInfo.token));
                                break;
                            case 4:
                                getLaunchHelper().startActivity(EditRegisterCodeActivity.getIntent(getActivity(), loginInfo.token, loginInfo.lUserId,loginInfo.nSex,1));
                                break;
                            case 5:
                                getLauncherHelper().startActivity(EditUserDataActivity.getIntent(getActivity(), loginInfo.token, loginInfo.lUserId,loginInfo.nSex));
                                break;
                            default:
                                ToastUtils.show(result.getMsg());
                                break;
                        }
                    }
                });
    }
}
