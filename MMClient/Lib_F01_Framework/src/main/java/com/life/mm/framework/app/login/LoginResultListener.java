package com.life.mm.framework.app.login;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.login <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/16 21:52. <P>
 * Function: <P>
 * Modified: <P>
 */

public abstract class LoginResultListener {
    protected abstract void onSuccess();
    protected void onCancel() {}
    protected void onFailure() {}
}
