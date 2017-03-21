package com.life.mm.framework.app.register.presenter;

import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.register.contract.RegisterContract;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.register.presenter <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/15 11:04. <P>
 * Function: <P>
 * Modified: <P>
 */

public class RegisterPresenter extends RegisterContract.Presenter {
    @Override
    public void register(String userName, String pwd) {
        mModel.register(userName, pwd, new BaseCallBack() {
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
                mView.onRegister();
            }

            @Override
            public void onError(int status, String message) {
                mView.onError(status, message);
            }
        });
    }
}
