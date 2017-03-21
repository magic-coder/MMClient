package com.life.mm.framework.app.login.presenter;

import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.login.contract.LoginContract;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.login.presenter <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/14 16:29. <P>
 * Function: <P>
 * Modified: <P>
 */

public class LoginPresenter extends LoginContract.Presenter {
    @Override
    public void doLogin(String userName, String pwd) {
        mModel.doLogin(userName, pwd, new BaseCallBack() {
            @Override
            public void onBegin() {
                mView.onBegin();
            }

            @Override
            public void doInBackground() {
                mView.doInBackground();
            }

            @Override
            public void onFinish() {
                mView.onFinish();
            }

            @Override
            public void onSuccess() {
                mView.onLogin();
            }

            @Override
            public void onError(int status, String message) {
                mView.onError(status, message);
            }
        });
    }
}
