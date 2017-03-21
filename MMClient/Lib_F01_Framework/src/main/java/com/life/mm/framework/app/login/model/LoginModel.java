package com.life.mm.framework.app.login.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.login.contract.LoginContract;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.login.model <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/14 16:35. <P>
 * Function: <P>
 * Modified: <P>
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public void doLogin(String userName, String pwd, final BaseCallBack callBack) {
        callBack.onBegin();
        AVUser.logInInBackground(userName, pwd, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                callBack.onFinish();
                if (null == e) {
                    callBack.onSuccess();
                } else {
                    callBack.onError(e.getCode(), e.getMessage());
                }
            }
        });
    }
}
