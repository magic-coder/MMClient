package com.life.mm.framework.app.register.contract;

import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.base.model.BaseModel;
import com.life.mm.framework.app.base.presenter.BasePresenter;
import com.life.mm.framework.app.base.view.BaseView;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register.contract <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/23 15:51. <P>
 * Function: <P>
 * Modified: <P>
 */

public interface PhoneRegisterContract {
    interface View extends BaseView {
        void onGetVerifyCode();
        void onVerifySuccess();
    }

    interface Model extends BaseModel {
        /**
         * 请求发送手机验证码
         * @param phoneNumber       相应手机号
         * @param callBack          回调对象
         */
        void getVerifyCode(String phoneNumber, final BaseCallBack callBack);

        /**
         * 请求校验验证码并注册
         * @param phoneNumber   要校验的手机号
         * @param smsCode       要校验的验证码
         * @param callBack      相应回调对象
         */
        void verifyCode(String phoneNumber, String smsCode, final BaseCallBack callBack);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getVerifyCode(String phoneNumber);
        public abstract void verifyCode(String phoneNumber, String smsCode);
    }
}
