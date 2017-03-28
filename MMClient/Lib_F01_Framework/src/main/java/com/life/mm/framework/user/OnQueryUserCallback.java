package com.life.mm.framework.user;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.user <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/27 11:31. <P>
 * Function: <P>
 * Modified: <P>
 */

public interface OnQueryUserCallback<T> {
    void onGetUser(T user);
}
