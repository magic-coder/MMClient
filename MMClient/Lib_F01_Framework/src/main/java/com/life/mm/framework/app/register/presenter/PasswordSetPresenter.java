package com.life.mm.framework.app.register.presenter;

import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.register.contract.PasswordSetContract;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register.presenter <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/23 16:47. <P>
 * Function: <P>
 * Modified: <P>
 */

public class PasswordSetPresenter extends PasswordSetContract.Presenter {
    @Override
    public void register(String source, String userName, String pwd) {
        mModel.register(source, userName, pwd, new BaseCallBack() {
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
                mView.onRegisterSuccess();
            }

            @Override
            public void onError(int status, String message) {
                mView.onError(status, message);
            }
        });
    }
}
