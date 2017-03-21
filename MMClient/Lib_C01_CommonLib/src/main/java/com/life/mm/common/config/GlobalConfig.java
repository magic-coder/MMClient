package com.life.mm.common.config;

/**
 * ProjectName:MMClient <P>
 * ClassName: <P>
 * Created by zfang on 2017/3/3 10:04. <P>
 * Function: <P>
 * Modified: <P>
 */

public class GlobalConfig {

    public static final boolean isDebug = true;
    //sharedPreference Config
    public static String globalFileName = "MMClientPreferenceFile";

    //LeanCloud
    public static String leanCloudAppKey = "7vHMWVtM1cbe05BlTy22cgSh";
    public static String leanCloudAppId = "8iCJi1O7XGVeUHDsQhgEtId6-gzGzoHsz";

    //Code
    public static final int CODE_BASE = 999;
    public static final int REQUEST_CODE_SELECT_PHOTOS = CODE_BASE + 1;
}
