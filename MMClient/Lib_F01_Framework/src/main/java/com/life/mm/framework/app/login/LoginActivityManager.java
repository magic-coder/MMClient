package com.life.mm.framework.app.login;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.login <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/16 21:48. <P>
 * Function: <P>
 * Modified: <P>
 */

public class LoginActivityManager {

    private String TAG = LoginActivityManager.class.getSimpleName();

    private static LoginActivityManager instance = null;

    public static LoginActivityManager getInstance() {
        if (null == instance) {
            synchronized (LoginActivityManager.class) {
                if (null == instance) {
                    instance = new LoginActivityManager();
                }
            }
        }
        return instance;
    }


}
