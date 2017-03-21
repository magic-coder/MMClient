package com.life.mm.common.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.life.mm.common.log.MMLogManager;

import java.util.Map;

public class MMSharedPreferences {

	private SharedPreferences mySP;

	public MMSharedPreferences(Context context, String file, int mode) {
		mySP = context.getSharedPreferences(file, mode);
	}

	public boolean contains(String arg0) {
		return null != mySP && mySP.contains(arg0);
	}

	public MMEditor edit() {
		if (mySP == null) {
			return null;
		} else {
			return new MMEditor(mySP);
		}
	}

	public Map<String, ?> getAll() {
		return mySP == null ? null : mySP.getAll();
	}

	public boolean getBoolean(String key, boolean defValue) {
		if (mySP == null)
			return false;
		String result = mySP.getString(key, String.valueOf(defValue));
		if (result == null) {
			return false;
		} else if (result.equals(defValue)) {
			return defValue;
		} else {
			// 解密
			try {
				return Boolean.parseBoolean(result);
			} catch (Exception e) {
				MMLogManager.logE(e.toString());
				return defValue;
			}
		}
	}

	public float getFloat(String key, float defValue) {
		if (mySP == null)
			return 0;
		String result = mySP.getString(key, String.valueOf(defValue));
		if (result == null) {
			return 0;
		} else if (result.equals(defValue)) {
			return defValue;
		} else {
			// 解密
			try {
				return Float.parseFloat(result);
			} catch (Exception e) {
				MMLogManager.logE(e.toString());
				return defValue;
			}
		}
	}

	public int getInt(String key, int defValue) {
		if (mySP == null)
			return 0;
		String result = mySP.getString(key, String.valueOf(defValue));
		if (result == null) {
			return 0;
		} else if (result.equals(defValue)) {
			return defValue;
		} else {
			// 解密
			try {
				return Integer.parseInt(result);
			} catch (Exception e) {
				MMLogManager.logE(e.toString());
				return defValue;
			}
		}
	}

	public long getLong(String key, long defValue) {
		if (mySP == null)
			return 0;
		String result = mySP.getString(key, String.valueOf(defValue));
		if (result == null) {
			return 0;
		} else if (result.equals(defValue)) {
			return defValue;
		} else {
			// 解密
			try {
				return Long.parseLong(result);
			} catch (Exception e) {
				MMLogManager.logE(e.toString());
				return defValue;
			}
		}
	}

	public String getString(String key, String defValue) {
		if (mySP == null)
			return null;
		String result = mySP.getString(key, defValue);
		if (result == null || result.length() <= 0) {
			return result;
		} else if (result.equals(defValue)) {
			return defValue;
		} else {
			// 解密
			try {
				return result;
			} catch (Exception e) {
				MMLogManager.logE(e.toString());
				return defValue;
			}
		}
	}

	public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
		mySP.registerOnSharedPreferenceChangeListener(listener);
	}

	public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
		mySP.unregisterOnSharedPreferenceChangeListener(listener);
	}

}
