package com.life.mm.framework.app.register;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/17 10:30. <P>
 * Function: <P>
 * Modified: <P>
 */

public abstract class RegisterResultListener {

    protected abstract void onSuccess();
    protected void onCancel() {}
    protected void onError() {}
}
