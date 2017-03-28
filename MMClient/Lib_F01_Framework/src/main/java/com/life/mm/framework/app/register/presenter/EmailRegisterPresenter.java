package com.life.mm.framework.app.register.presenter;

import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.register.contract.EmailRegisterContract;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register.presenter <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/24 10:25. <P>
 * Function: <P>
 * Modified: <P>
 */

public class EmailRegisterPresenter extends EmailRegisterContract.Presenter {
    @Override
    public void sendEmailVerify(String emailAddress) {
        mModel.sendEmailVerify(emailAddress, new BaseCallBack() {
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
                mView.onSendEmailVerifySuccess();
            }

            @Override
            public void onError(int status, String message) {
                mView.onError(status, message);
            }
        });
    }
}
