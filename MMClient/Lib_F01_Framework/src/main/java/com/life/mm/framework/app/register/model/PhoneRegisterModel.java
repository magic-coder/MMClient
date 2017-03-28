package com.life.mm.framework.app.register.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.register.contract.PhoneRegisterContract;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register.model <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/23 16:00. <P>
 * Function: <P>
 * Modified: <P>
 */

public class PhoneRegisterModel implements PhoneRegisterContract.Model {
    @Override
    public void getVerifyCode(String phoneNumber, final BaseCallBack callBack) {
        callBack.onBegin();
        AVOSCloud.requestSMSCodeInBackground(phoneNumber, new RequestMobileCodeCallback() {
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

    @Override
    public void verifyCode(String phoneNumber, String smsCode, final BaseCallBack callBack) {
        callBack.onBegin();
        AVUser.signUpOrLoginByMobilePhoneInBackground(phoneNumber, smsCode, new LogInCallback<AVUser>() {
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
