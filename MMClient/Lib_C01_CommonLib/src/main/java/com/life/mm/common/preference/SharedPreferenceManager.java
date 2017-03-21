package com.life.mm.common.preference;

import android.content.Context;

import com.life.mm.common.config.GlobalConfig;

/**
 * ProjectName:MMClient <P>
 * ClassName: <P>
 * Created by zfang on 2017/3/2 17:32. <P>
 * Function: <P>
 * Modified: <P>
 */

public class SharedPreferenceManager {

    private static SharedPreferenceManager instance = null;
    private MMSharedPreferences sharedPreferences = null;

    public static SharedPreferenceManager getInstance(Context context) {
        if (null == instance) {
            synchronized (SharedPreferenceManager.class) {
                if (null == instance) {
                    instance = new SharedPreferenceManager(context);
                }
            }
        }

        return instance;
    }


    private SharedPreferenceManager(Context context) {
        sharedPreferences = new MMSharedPreferences(context, GlobalConfig.globalFileName, Context.MODE_PRIVATE);
    }

    public MMSharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
