package com.life.mm.common.config;

import android.util.SparseArray;

/**
 * ProjectName:MMClient <P>
 * ClassName: <P>
 * Created by zfang on 2017/3/3 10:04. <P>
 * Function: <P>
 * Modified: <P>
 */

public class GlobalConfig {

    public static final boolean isDebug = true;
    public static SparseArray<String> genderArray = new SparseArray<>(2);
    static {
        genderArray.put(0, "男");
        genderArray.put(1, "女");
    }
    //sharedPreference Config
    public static String globalFileName = "MMClientPreferenceFile";

    //LeanCloud
    public static String leanCloudAppKey = "7vHMWVtM1cbe05BlTy22cgSh";
    public static String leanCloudAppId = "8iCJi1O7XGVeUHDsQhgEtId6-gzGzoHsz";

    //高德相关code
    public static final int amapCodeOk = 1000;
    //Code
    public static final int CODE_BASE = 999;
    public static final int REQUEST_CODE_SELECT_PHOTOS = CODE_BASE + 1;

    //key
    public static final String source = "Source";//来源，表示数据从哪个页面过来
    public static final String email = "Email";
    public static final String phoneNumber = "PhoneNumber";
}
