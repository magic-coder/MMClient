package com.life.mm.framework.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.life.mm.common.config.GlobalConfig;
import com.life.mm.common.log.MMLogManager;
import com.life.mm.common.preference.SharedPreferenceManager;
import com.life.mm.framework.map.LocationManager;
import com.life.mm.framework.okhttp.OKHttpManager;
import com.life.mm.framework.user.ContactInfo;
import com.life.mm.framework.user.CustomUser;

import okhttp3.OkHttpClient;

/**
 * ProjectName:MM <P>
 * ClassName: <P>
 * Created by zfang on 2017/2/28 15:48. <P>
 * Function: <P>
 * Modified: <P>
 */

public class MMApplication extends Application {

    private static String TAG = MMApplication.class.getSimpleName();
    private static MMApplication application;

    private SharedPreferenceManager sharedPreferenceManager = null;
    private LocationManager locationManager = null;
    private AppManager appManager = null;
    private OkHttpClient okHttpClient = null;
    private CustomUser customUser = null;
    private boolean userShouldRefresh = false;

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public SharedPreferenceManager getSharedPreferenceManager() {
        return sharedPreferenceManager;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public AppManager getAppManager() {
        return appManager;
    }

    public CustomUser getCustomUser() {
        if (null == customUser || userShouldRefresh) {
            customUser = AVUser.getCurrentUser(CustomUser.class);
        }
        return customUser;
    }


    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }

    public void setUserShouldRefresh(boolean userShouldRefresh) {
        this.userShouldRefresh = userShouldRefresh;
    }

    public boolean isLogin() {
        return null != getCustomUser();
    }

    /** Multidex
     * extends android.support.multidex.MultiDexApplication 或添加 attachBaseContext 方法
     * 这里采取的是添加 attachBaseContext 方法
     * */
    protected void attachBaseContext(Context paramContext) {
        super.attachBaseContext(paramContext);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MMLogManager.logD(TAG + ", onCreate------start>");
        application = this;
        init();
        MMLogManager.logD(TAG + ", onCreate>------------end");
    }



    public static MMApplication getInstance() {
        return application;
    }

    private void init() {
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        locationManager = LocationManager.getInstance(this);//初始化定位模块
        appManager = AppManager.getAppManager();
        registerActivityLifecycleCallbacks(appManager);//初始化app activity管理栈
        okHttpClient = OKHttpManager.getInstance().getOkHttpClient();

        AVUser.alwaysUseSubUserClass(CustomUser.class);
        AVObject.registerSubclass(ContactInfo.class);//支持子类化操作
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, GlobalConfig.leanCloudAppId, GlobalConfig.leanCloudAppKey);
        AVOSCloud.setDebugLogEnabled(GlobalConfig.isDebug);
    }
}
