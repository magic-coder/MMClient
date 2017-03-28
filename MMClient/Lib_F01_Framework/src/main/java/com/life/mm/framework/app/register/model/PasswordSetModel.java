package com.life.mm.framework.app.register.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;
import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.register.EmailRegisterActivity;
import com.life.mm.framework.app.register.PhoneRegisterActivity;
import com.life.mm.framework.app.register.contract.PasswordSetContract;
import com.life.mm.framework.user.CustomUser;
import com.life.mm.framework.user.UserManager;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register.model <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/23 16:44. <P>
 * Function: <P>
 * Modified: <P>
 */

public class PasswordSetModel implements PasswordSetContract.Model {
    @Override
    public void register(String source, String userName, String pwd, final BaseCallBack callBack) {
        callBack.onBegin();
        CustomUser customUser = new CustomUser();
        customUser.setUsername(userName);
        customUser.setPassword(pwd);
        if (PhoneRegisterActivity.class.getSimpleName().equals(source)) {
            customUser.setMobilePhoneNumber(userName);
        } else if (EmailRegisterActivity.class.getSimpleName().equals(source)) {
            customUser.setEmail(userName);
        }
        UserManager.getInstance().saveDevUser(customUser);
        customUser.signUpInBackground(new SignUpCallback() {
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
