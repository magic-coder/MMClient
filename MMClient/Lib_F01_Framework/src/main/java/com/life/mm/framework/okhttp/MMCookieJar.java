package com.life.mm.framework.okhttp;

import com.life.mm.common.log.MMLogManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.okhttp <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/9 12:58. <P>
 * Function: <P>
 * Modified: <P>
 */

class MMCookieJar implements CookieJar {

    private String TAG = MMCookieJar.class.getSimpleName();
    private List<Cookie> cookieList;
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        MMLogManager.logD(TAG + ", cookies = " + cookies);
        this.cookieList = cookies;
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if(cookieList != null){
            return cookieList;
        }
        return new ArrayList<>();
    }
}
