package com.qsd.jmwh.module.login.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.qsd.jmwh.dialog.net.NetLoadingDialog;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.params.PostParams;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.login.bean.LoginInfo;
import com.qsd.jmwh.module.register.EditRegisterCodeActivity;
import com.qsd.jmwh.module.register.EditUserDataActivity;
import com.qsd.jmwh.module.register.SelectGenderActivity;
import com.qsd.jmwh.utils.MD5Utils;
import com.qsd.jmwh.utils.RxSchedulerUtils;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.model.ApiResult;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

public class LoginPresenter extends BaseViewPresenter<LoginViewer> {

    public LoginPresenter(LoginViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void login(String userName, String pwd) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.show("账号输入为空");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.show("密码输入为空");
            return;
        }
        NetLoadingDialog.showLoading(getActivity(),false);
        XHttp.custom(ApiServices.class)
                .login(PostParams.createParams()
                        .put("sLoginName", userName)
                        .put("sPwd",MD5Utils.string2MD5(pwd))
                        .creatBody())
                .compose(RxSchedulerUtils.<ApiResult<LoginInfo>>_io_main_o())
                .subscribeWith(new TipRequestSubscriber<ApiResult<LoginInfo>>() {
                    @Override
                    protected void onSuccess(ApiResult<LoginInfo> result) {
                        int code = result.getCode();
                        switch (code) {
                            case 0:
                                assert getViewer() != null;
                                getViewer().handleLoginResult(result.getData());
                                break;
                            case 3:
                                getLauncherHelper().startActivity(SelectGenderActivity.class);
                                NetLoadingDialog.dismissLoading();
                                break;
                            case 4:
                                getLaunchHelper().startActivity(EditRegisterCodeActivity.class);
                                NetLoadingDialog.dismissLoading();
                                break;
                            case 5:
                                getLauncherHelper().startActivity(EditUserDataActivity.class);
                                NetLoadingDialog.dismissLoading();
                                break;
                            default:
                                ToastUtils.show(result.getMsg());
                                NetLoadingDialog.dismissLoading();
                                break;
                        }
                    }

                    @Override protected void onError(ApiException apiException) {
                        NetLoadingDialog.dismissLoading();
                        super.onError(apiException);
                    }
                });

    }
}
