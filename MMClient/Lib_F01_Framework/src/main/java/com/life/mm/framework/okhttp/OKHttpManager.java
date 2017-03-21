package com.life.mm.framework.okhttp;

import com.life.mm.framework.app.MMApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.okhttp <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/8 14:20. <P>
 * Function: <P>
 * Modified: <P>
 */

public class OKHttpManager {

    private static OKHttpManager instance = new OKHttpManager();
    private OkHttpClient okHttpClient = null;
    private static final long defaultTimeOut = 10 * 1000;


    public static OKHttpManager getInstance() {
        return instance;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private OKHttpManager() {
        init();
    }

    private void init() {
        File cacheDir = new File(MMApplication.getInstance().getExternalCacheDir() + "/okhttp");
        long cacheSize = 10 * 1024 * 1024;//10MB
        Cache cache = new Cache(cacheDir, cacheSize);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(defaultTimeOut, TimeUnit.MILLISECONDS)
                .writeTimeout(defaultTimeOut, TimeUnit.MILLISECONDS)
                .readTimeout(defaultTimeOut, TimeUnit.MILLISECONDS)
                .cache(cache)
                .addInterceptor(interceptor)
                .cookieJar(new MMCookieJar())
                .build();
    }
}
