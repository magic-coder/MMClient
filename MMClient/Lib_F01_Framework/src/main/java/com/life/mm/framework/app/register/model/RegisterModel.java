package com.life.mm.framework.app.register.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.register.contract.RegisterContract;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.register.model <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/15 11:01. <P>
 * Function: <P>
 * Modified: <P>
 */

public class RegisterModel implements RegisterContract.Model {
    @Override
    public void register(String userName, String pwd, final BaseCallBack callBack) {
        callBack.onBegin();
        AVUser avUser = new AVUser();
        avUser.setUsername(userName);
        avUser.setPassword(pwd);
        avUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
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
