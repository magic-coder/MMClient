package com.life.mm.framework.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.life.mm.common.log.MMLogManager;
import com.life.mm.framework.app.MMApplication;
import com.life.mm.framework.skin.CcbSkinColorTool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.life.mm.common.log.MMLogManager.logE;

public class MMUtils {



	private static final String KEY_MBS_TYPE = "mbsType";
	private static final String KEY_EBS_TYPE = "ebsType";
	private static final String KEY_CHINESE = "chinese";
	/**
	 * 是否WIFI连接
	 *
	 * @param c
	 */
	public static boolean isWIFI(Context c) {
		ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();// 获取网络的连接情况

		if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI)
			return true;
		return false;
	}

	/**
	 * 是否3G网络连接
	 *
	 * @param c
	 */
	public static boolean isMobile(Context c) {
		ConnectivityManager connectivityManager = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	/**
	 * 启动 app
	 *
	 * @param mContext
	 * @param pkgName
	 * @param bundle
	 */
	public static void start(Context mContext, String pkgName, Bundle bundle) {
		PackageInfo pi = null;
		try {
			pi = mContext.getPackageManager().getPackageInfo(pkgName, 0);
			Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
			resolveIntent.setPackage(pi.packageName);
			PackageManager pm = mContext.getPackageManager();
			List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);
			ResolveInfo ri = apps.iterator().next();
			if (ri != null) {
				String packageName = ri.activityInfo.packageName;
				String className = ri.activityInfo.name;
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				ComponentName cn = new ComponentName(packageName, className);
				intent.setComponent(cn);
				if (bundle != null) {
					intent.putExtras(bundle);
				}
				mContext.startActivity(intent);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印List,list的元素是String,String型的Map
	 *
	 * @param list
	 * @return
	 */
	public static String list2String(List<Map<String, String>> list) {
		final String indent = "    ";
		if (list == null) {
			return indent + "null list";
		}
		if (list.size() == 0) {
			return indent + "list size = 0";
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0, size = list.size(); i < size; i++) {
			if (i != 0) {
				sb.append("\n");
			}
			sb.append("++map[" + (i + 1) + "]++");
			Map<String, String> m = list.get(i);
			sb.append(map2String(m));
		}

		return sb.toString();
	}

	/**
	 * 打印String,String型的map
	 *
	 * @param map
	 * @return
	 */
	public static String map2String(Map<String, String> map) {
		final String indent = "    ";

		if (map == null) {
			return indent + "null map";
		}
		if (map.size() == 0) {
			return indent + "map size = 0";
		}
		StringBuilder sb = new StringBuilder();
		for (String s : map.keySet()) {
			sb.append(indent + s + " = " + map.get(s) + "\n");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 打印 string array
	 *
	 * @param array
	 * @return
	 */
	public static String array2String(String[] array) {
		final String indent = "    ";

		if (array == null) {
			return indent + "null array";
		}
		if (array.length == 0) {
			return indent + "array size = 0";
		}
		StringBuilder sb = new StringBuilder();
		for (String s : array) {
			sb.append(indent + s + "\n");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/***** 获取客户端技术版本号,如3.4.3.001 *****/
	private static CcbVersionInterface ccbVersionInterface = null;

	public static void setCcbVersionInterface(CcbVersionInterface ccbVersionInterface) {
		MMUtils.ccbVersionInterface = ccbVersionInterface;
	}

	public static String getCcbVersion() {
		if (null == MMUtils.ccbVersionInterface) {
			return null;
		}
		return MMUtils.ccbVersionInterface.getCcbVersion();
	}

	public interface CcbVersionInterface {
		String getCcbVersion();
	}

	/**
	 * 获取asset目录下文件
	 *
	 * @param fileName
	 *            文件全称,包含后缀名 例如a.png , x.9.png , yy.jpg
	 * @return inputStream
	 */
	public static InputStream asset_getFile(String fileName) {
		try {
			InputStream inStream = MMApplication.getInstance().getAssets().open(fileName);
			return inStream;
		} catch (IOException e) {
			logE("asset:" + fileName + ",no exist");
		}
		return null;
	}

	/**
	 * 将InputStream 转换为String
	 *
	 * @param is
	 * @param encoding
	 *            编码格式,可以为null,null表示适用utf-8
	 */
	public static String stream_2String(InputStream is, String encoding) throws IOException {
		if (is == null)
			return null;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		baos.close();
		String result = null;
		if (encoding == null) {
			encoding = "utf-8";
		}
		result = baos.toString(encoding);
		return result;
	}

	/**
	 * String => inputStream
	 *
	 * @param src
	 * @param charsetName
	 *            编码格式 可以为null,null表示适用utf-8
	 * @return
	 */
	public static InputStream string_2stream(String src, String charsetName) {
		try {
			if (null == charsetName) {
				charsetName = "utf-8";
			}
			byte[] bArray = src.getBytes(charsetName);
			InputStream is = new ByteArrayInputStream(bArray);
			return is;
		} catch (UnsupportedEncodingException e) {
			logE(e.toString());
		}
		return null;
	}

	/**
	 * 读取raw文件，返回流
	 *
	 * @return 返回string
	 */
	public static InputStream readRawFileAsStream(int rawResId) {
		Context appContext = MMApplication.getInstance();
		InputStream inStream = appContext.getResources().openRawResource(rawResId);
		return inStream;
	}

	/**
	 * 读取raw文件，返回String
	 *
	 * @param rawResId
	 * @return
	 * @throws IOException
	 */
	public static String readRawFileAsString(int rawResId) throws IOException {
		InputStream inStream = readRawFileAsStream(rawResId);
		return stream_2String(inStream, null);
	}

	/***
	 * 读取图片
	 *
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/***
	 * 读取图片
	 *
	 * @param context
	 * @param resId
	 * @param inSampleSize
	 *            采样比率
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId, int inSampleSize) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inSampleSize = inSampleSize;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}


	/**
	 * 读取图片
	 * @param context 上下文
	 * @param path 图片路径
     * @return
     */
	public static Bitmap readBitmapWithLocalPath(Context context, String path){
		try {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			// 获取资源图片
			InputStream bitmapStream = new FileInputStream(new File(path));
			return BitmapFactory.decodeStream(bitmapStream, null, opt);
		} catch (FileNotFoundException e) {
			return null;
		}
	}


	public static String defType = "drawable";
	public static String defTypeMipmap = "mipmap";

	/**
	 * 读取drawable下的图片
	 *
	 * @param context
	 * @param imageName
	 *            不包括图片后缀名
	 * @return
	 */
	public static Bitmap readBitMap(Context context, String imageName) {
		int resId = CcbSkinColorTool.getInstance().getDrawableId(context,imageName);
		if (0 == resId)
			return null;
		return readBitMap(context, resId);
	}

	/**
	 * 从Context中获取Activity
	 *
	 * @param mContext
	 * @return
	 */
	public static Activity getActFromContext(Context mContext) {
		if (mContext == null)
			return null;
		else if (mContext instanceof Activity)
			return (Activity) mContext;
		else if (mContext instanceof ContextWrapper)
			return getActFromContext(((ContextWrapper) mContext).getBaseContext());
		return null;
	}


	private static int currentLight;
	private static int currentMode;
	private static boolean isScreenHighLight = false;

	public static void setScreenHighLight(Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		try {
			isScreenHighLight = true;
			currentMode = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE);
			currentLight = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception e) {
			logE(e.toString());
		}

		if (Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC == currentMode) {
			Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
		}
		setScreenLight(context, 255);

	}


	public static void reSetScreenLight(Context context) {
		if (!isScreenHighLight) {
			return;
		}
		ContentResolver contentResolver = context.getContentResolver();
		Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, currentMode);
		Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, currentLight);
		isScreenHighLight = false;
	}

	private static void setScreenLight(Context context, int value) {
		ContentResolver contentResolver = context.getContentResolver();
		Window window = ((Activity) context).getWindow();
		WindowManager.LayoutParams layoutParams = window.getAttributes();
		float light = value / 255f;
		layoutParams.screenBrightness = light;
		window.setAttributes(layoutParams);
		Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, value);
	}

	/**
	 * 获取技术版本号
	 * @return
     */
	public static String getTecVersion(){
		return "";
	}

	/**
	 * 客户端程序版本号
     */
	public static String getCcbMbcVersionName(Context context){
		synchronized (MMUtils.class) {
			try {
				PackageManager packageManager = context.getPackageManager();
				PackageInfo packInfo = null;
				packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
				return packInfo.versionName;
			} catch (Exception e) {
				logE(e.toString());
			}
			return null;
		}
	}
	/**
	 *   客户端程序版本号CODE
	 */
	public static String getCcbMbcVersionCode(Context context){
		synchronized (MMUtils.class) {
			try {
				PackageManager packageManager = context.getPackageManager();
				PackageInfo packInfo = null;
				packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
				return String.valueOf(packInfo.versionCode);
			} catch (Exception e) {
				logE(e.toString());
			}
			return null;
		}
	}


	/**
	 * 检查是否有权限
	 * @param permission 权限名称 例如 {@link android.Manifest.permission#CALL_PHONE}
	 * @return 如果权限开启返回true 如果没有返回false
     */
	public static boolean hasAppPermission(String permission) {
		if (TextUtils.isEmpty(permission))
			return true;
		if (getTargetSdkVersion() < Build.VERSION_CODES.M) {
			return PERMISSION_GRANTED == PermissionChecker.checkSelfPermission(MMApplication.getInstance(), permission);
		} else {
			return PERMISSION_GRANTED == ContextCompat.checkSelfPermission(MMApplication.getInstance(), permission);
		}
	}

	/**
	 * 获取target版本信息
	 * @return
     */
	public static int getTargetSdkVersion() {
		int version = 0;
		try {
			PackageInfo packageInfo = MMApplication.getInstance().getPackageManager()
					.getPackageInfo(MMApplication.getInstance().getPackageName(), 0);
			version = packageInfo.applicationInfo.targetSdkVersion;
		} catch (Exception e) {
			MMLogManager.logE("==============get package info error============");
		}
		return version;
	}
	public static void printCallStack() {
		Map<Thread, StackTraceElement[]> stes = Thread.getAllStackTraces();
		if (null == stes || stes.isEmpty())
			return;
		StackTraceElement[] ste = stes.get(Thread.currentThread());
		if (null == ste || 0 == ste.length)
			return;
		for (StackTraceElement s : ste) {
			MMLogManager.logD("STACK PRINT" + "======call stack======" + s.toString());
		}
	}
	public static int getResIdByName(Context context, String drawableName) {
		int resId = context.getResources().getIdentifier(drawableName, defType, context.getPackageName());
		if (0 == resId) {
			resId = context.getResources().getIdentifier(drawableName, defTypeMipmap, context.getPackageName());
		}
		return resId;
	}

	public static boolean isAvaliableList(List<?> list) {
		return null != list && list.size() > 0;
	}
}
