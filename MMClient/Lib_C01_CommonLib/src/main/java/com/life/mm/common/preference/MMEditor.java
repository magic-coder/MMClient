package com.life.mm.common.preference;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.life.mm.common.log.MMLogManager;

public class MMEditor {

	SharedPreferences mySP;
	Editor editor;

	public MMEditor(SharedPreferences mySP) {
		this.mySP = mySP;
		editor = mySP.edit();
	}

	public Editor putBoolean(String arg0, boolean arg1) {
		try {
			return editor.putString(arg0, String.valueOf(arg1));
		} catch (Exception e) {
			MMLogManager.logE(e.toString());
			return editor.putString(arg0, String.valueOf(arg1));
		}
	}

	public Editor putFloat(String arg0, float arg1) {
		try {
			return editor.putString(arg0, String.valueOf(arg1));
		} catch (Exception e) {
			MMLogManager.logE(e.toString());
			return editor.putString(arg0, String.valueOf(arg1));
		}
	}

	public Editor putInt(String arg0, int arg1) {
		try {
			return editor.putString(arg0, String.valueOf(arg1));
		} catch (Exception e) {
			MMLogManager.logE(e.toString());
			return editor.putString(arg0, String.valueOf(arg1));
		}
	}

	public Editor putLong(String arg0, long arg1) {
		try {
			return editor.putString(arg0, String.valueOf(arg1));
		} catch (Exception e) {
			MMLogManager.logE(e.toString());
			return editor.putString(arg0, String.valueOf(arg1));
		}
	}

	public Editor putString(String arg0, String arg1) {
		if (arg1 == null || arg1.length() <= 0) {
			return editor.putString(arg0, arg1);
		} else {
			try {
				return editor.putString(arg0, String.valueOf(arg1));
			} catch (Exception e) {
				MMLogManager.logE(e.toString());
				return editor.putString(arg0, arg1);
			}
		}
	}

	public boolean commit() {
		return editor.commit();
	}

	public Editor clear() {
		return editor.clear();
	}
	
	public Editor remove(String key) {
		return editor.remove(key);
	}

}
