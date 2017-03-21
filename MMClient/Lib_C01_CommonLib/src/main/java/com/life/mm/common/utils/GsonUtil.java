package com.life.mm.common.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.life.mm.common.preference.MMEditor;
import com.life.mm.common.preference.SharedPreferenceManager;

import java.lang.reflect.Type;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.common.utils <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/3 10:16. <P>
 * Function: <P>
 * Modified: <P>
 */

public class GsonUtil {

    /**
     * 对象转字符串
     * @param context 上下文
     * @param object  相应的要转成字符串的对象
     * @param key     要存储到sharedPreference的key
     */
    public static void saveToJson(Context context, Object object, String key) {
        try {
            Gson gson = new Gson();
            String value = gson.toJson(object);
            MMEditor editor = SharedPreferenceManager.getInstance(context).getSharedPreferences().edit();
            editor.putString(key, value);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字符串转对象
     * @param context      上下文对象
     * @param type         相应对象的类
     * @param key          要获取字符串的key
     * @param defaultValue 默认要转成对象的字符串
     * @return             返回相应的目标类对象
     */
    public static Type toObject(Context context, Type type, String key, String defaultValue) {
        String value = SharedPreferenceManager.getInstance(context).getSharedPreferences().getString(key, defaultValue);
        return toObject(type, value);
    }


    /**
     * 字符串转对象
     * @param type          相应对象的类
     * @param value 默认要转成对象的字符串
     * @return      返回相应的目标类对象
     */
    public static Type toObject(Type type, String value) {
        Class<?> objCls = null;
        try {
            Gson gson = new Gson();
            objCls = gson.fromJson(value, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return objCls;
    }
}
