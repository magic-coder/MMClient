package com.life.mm.framework.app.register.presenter;

import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.register.contract.PhoneRegisterContract;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register.presenter <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/23 16:01. <P>
 * Function: <P>
 * Modified: <P>
 */

public class PhoneRegisterPresenter extends PhoneRegisterContract.Presenter {
    @Override
    public void getVerifyCode(String phoneNumber) {
        mModel.getVerifyCode(phoneNumber, new BaseCallBack() {
            @Override
            public void onBegin() {
                mView.onBegin();
            }

            @Override
            public void doInBackground() {

            }

            @Override
            public void onFinish() {
                mView.onFinish();
            }

            @Override
            public void onSuccess() {
                mView.onGetVerifyCode();
            }

            @Override
            public void onError(int status, String message) {
                mView.onError(status, message);
            }
        });
    }

    @Override
    public void verifyCode(String phoneNumber, String smsCode) {
        mModel.verifyCode(phoneNumber, smsCode, new BaseCallBack() {
            @Override
            public void onBegin() {
                mView.onBegin();
            }

            @Override
            public void doInBackground() {

            }

            @Override
            public void onFinish() {
                mView.onFinish();
            }

            @Override
            public void onSuccess() {
                mView.onVerifySuccess();
            }

            @Override
            public void onError(int status, String message) {
                mView.onError(status, message);
            }
        });
    }
}
