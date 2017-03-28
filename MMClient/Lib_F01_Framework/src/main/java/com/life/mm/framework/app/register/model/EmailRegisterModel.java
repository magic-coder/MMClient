package com.life.mm.framework.app.register.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestEmailVerifyCallback;
import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.register.contract.EmailRegisterContract;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register.model <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/24 10:23. <P>
 * Function: <P>
 * Modified: <P>
 */

public class EmailRegisterModel implements EmailRegisterContract.Model {
    @Override
    public void sendEmailVerify(String emailAddress, final BaseCallBack callBack) {
        callBack.onBegin();

        AVUser.requestEmailVerifyInBackground(emailAddress, new RequestEmailVerifyCallback() {
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
