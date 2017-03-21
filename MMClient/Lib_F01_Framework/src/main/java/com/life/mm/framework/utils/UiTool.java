package com.life.mm.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.SystemClock;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.life.mm.common.log.MMLogManager;
import com.life.mm.framework.skin.CcbSkinColorTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UiTool {
	/**
	 * 银行图标
	 */
	public static final Map<String, String> BANK_NAME = new HashMap<String, String>() {
		{
			put("102100099996", "中国工商银行");//中国工商银行
			put("103100000026", "中国农业银行");//中国农业银行
			put("104100000004", "中国银行");//中国银行
			put("301290000007", "交通银行");//交通银行
			put("302100011000", "中信银行");//中信银行
			put("303100000006", "中国光大银行");//中国光大银行
			put("304100040000", "华夏银行");//华夏银行
			put("305100000013", "中国民生银行");//中国民生银行
			put("306581000003", "广发银行");//广发银行
			put("307584007998", "平安银行");//平安银行
			put("308584000013", "招商银行");//招商银行
			put("309391000011", "兴业银行");//兴业银行
			put("310290000013", "上海浦东发展银行");//上海浦东发展银行
			//put("313290000017", "城市商业银行");// 城市商业银行
			put("403100000004", "中国邮政储蓄银行");//中国邮政储蓄银行
			put("313301099999", "江苏银行股份有限公司");//江苏银行股份有限公司
			put("313222080002", "大连银行");//大连银行
			put("313332082914", "宁波银行");//宁波银行
			put("322290000011", "上海农商银行");//上海农商银行
			put("325290000012", "上海银行");//上海银行
		}
	};

	/**
	 * 根据银行号获取对应的图片
	 *
	 * @param bankNo
	 * @return
	 */
	public static String getBankNameFromBankCD(Context context, String bankNo) {
		String bankName = BANK_NAME.get(bankNo);
		if (TextUtils.isEmpty(bankName)) {
			bankName = "末识别银行";
		}
		return bankName;
	}
	/**
	 * 格式化金额
	 * 
	 * @author lh
	 * @param s
	 * @return
	 */
	public static String formatMoney(String s) {
		if (s == null || s.length() < 1) {
			return "";
		}
		NumberFormat formater = null;
		s = s.replaceAll(",", "");
		double num = Double.parseDouble(s);
		formater = new DecimalFormat("#,###.00");
		String returnstr = formater.format(num);
		if (returnstr.startsWith(".")) {
			return "0" + returnstr;
		} else if (returnstr.startsWith("-.")) {
			return "-0" + returnstr.substring(1, returnstr.length());
		} else {
			return formater.format(num);
		}
	}

	/**
	 * 格式化金额,只对整数部分格式,小数部分保持原样
	 * 
	 * @author lh
	 * @param s
	 * @return
	 */
	public static String formatMoneyWithOutDecimalPoint(String s) {
		if (s == null || s.length() < 1) {
			return "";
		}
		NumberFormat formater = null;
		s = s.replaceAll(",", "");
		String[] numArr = s.split("\\.");

		double num = Double.parseDouble(numArr[0]);
		formater = new DecimalFormat("#,###");
		String returnstr = formater.format(num);
		if (returnstr.startsWith(".")) {
			return "0" + returnstr;
		} else if (returnstr.startsWith("-.")) {
			return "-0" + returnstr.substring(1, returnstr.length());
		} else {
			if (numArr.length == 2)
				return formater.format(num) + "." + numArr[1];
			else
				return formater.format(num);
		}
	}

	/**
	 * 金额格式化数字
	 * 
	 * @author lh
	 * @param s
	 * @return
	 */
	public static String formatNumberAmount(String s) {
		if (s == null || s.length() < 1) {
			return "";
		}
		NumberFormat formater = null;
		s = s.replaceAll(",", "");
		double num = Double.valueOf(s);
		formater = new DecimalFormat("#.0");
		String returnstr = formater.format(num);
		if (returnstr.startsWith(".")) {
			return "0" + returnstr;
		} else if (returnstr.startsWith("-.")) {
			return "-0" + returnstr.substring(1, returnstr.length());
		} else {
			return formater.format(num);
		}
	}

	/**
	 * 根据指定的格式格式化金额
	 * 
	 * @param s
	 * @param format
	 *            e.g.:#,###.00
	 * @return
	 */
	public static String formatMoney(String s, String format) {
		if (s == null || s.length() < 1) {
			return "";
		}
		NumberFormat formater = null;
		s = s.replaceAll(",", "");
		double num = Double.parseDouble(s);
		formater = new DecimalFormat(format);
		String returnstr = formater.format(num);
		if (returnstr.startsWith(".")) {
			return "0" + returnstr;
		} else if (returnstr.startsWith("-.")) {
			return "-0" + returnstr.substring(1, returnstr.length());
		} else {
			return formater.format(num);
		}
	}

	/**
	 * 金额格式化数字
	 * 
	 * @author lh
	 * @param s
	 * @return
	 */
	public static String formatNumber(String s) {
		if (s == null || s.length() < 1) {
			return "";
		}
		NumberFormat formater = null;
		s = s.replaceAll(",", "");
		double num = Double.valueOf(s);
		formater = new DecimalFormat("#.00");
		String returnstr = formater.format(num);
		if (returnstr.startsWith(".")) {
			return "0" + returnstr;
		} else if (returnstr.startsWith("-.")) {
			return "-0" + returnstr.substring(1, returnstr.length());
		} else {
			return formater.format(num);
		}
	}

	// /**
	// * 判断String是否由0-9组成
	// * @param str
	// * @return
	// */
	// public static boolean isPositiveInteger(String str){
	// Pattern pattern = Pattern.compile("[0-9]*");
	// return pattern.matcher(str).matches();
	// }
	//
	// /**
	// * @param srcName
	// * 文件名
	// * @return 返回drawable资源
	// */
	// public static Drawable getDrawableResId(String srcName) {
	// String defType = "drawable";
	// String defPkgName = CcbApplication.getInstance().getPackageName();
	// Resources res = CcbApplication.getInstance().getResources();
	//
	// int resId = res.getIdentifier(srcName, defType, defPkgName);
	// if (resId == 0) {
	// LogManager.logE("未找到资源:" + srcName);
	// return null;
	// }
	// Drawable d = res.getDrawable(resId);
	// return d;
	// }

	/**
	 * 手机号账号434格式格式化
	 * 
	 * @param s
	 * @return String
	 */

	public static String format434(String s) {

		return (s != null && s.length() >= 8) ? s.substring(0, 4) + "***" + s.substring(s.length() - 4, s.length()) : s;

	}

	/**
	 *  资产负债详情，贷款账户格式化
	 *
	 * @param s
	 * @return
     */
	public static String format433(String s) {

		return (s != null && s.length() >= 8) ? s.substring(0, 4) + "***" + s.substring(s.length() - 3, s.length()) : s;

	}

	public static String formatPhone(String s) {
		if (s != null && s.contains("-") && s.length() >= 8) {
			int index = s.indexOf("-");
			return s.substring(0, index + 1) + "***" + s.substring(s.length() - 4, s.length());
		} else {
			return format434(s);
		}
	}

	/**
	 * 4.0版手机号格式化规则 (更新：又变成434了)
	 * 小于8位直接返回；前4+“***”+后段（位数除以4余几，展示最后几位。余0，展示最后4位）
	 */
	public static String formatPhoneNewRule4_0(String oriStr) {
		return format434(oriStr);
	}

	/**
	 * 4.0版账号格式化规则 (更新：又变成434了)
	 * 小于8位直接返回；前4+“***”+后段（位数除以4余几，展示最后几位。余0，展示最后4位）
	 */
	public static String formatAccountNewRule4_0(String oriStr) {
		return format434(oriStr);
	}

	/**
	 * 4.0版证件号码格式化规则 (更新：又变成434了)
	 * 小于8位直接返回；前4+“***”+后段（位数除以4余几，展示最后几位。余0，展示最后4位）
	 */
	public static String formatCertificateIdNewRule4_0(String oriStr) {
		return format434(oriStr);
	}

	/**
	 * 小于8位直接返回；前4+“***”+后段（位数除以4余几，展示最后几位。余0，展示最后4位）
     */
	private static String formatStrNewRule_First4AndLastMax4(String oriStr) {
		if (TextUtils.isEmpty(oriStr) || oriStr.length() < 8) {
			return oriStr;
		}

		// replace space
		oriStr = oriStr.replace(" ", "").replace("　", "");

		int oriLength = oriStr.length();
		if (8 == oriLength) {
			return oriStr.substring(0, 4) + "***" + oriStr.substring(oriLength - 1, oriLength);
		}

		int lastPartLength = oriLength % 4;
		if (0 == lastPartLength) {
			lastPartLength = 4;
		}
		return oriStr.substring(0, 4) + "***"
				+ oriStr.substring(oriLength - lastPartLength, oriLength);
	}


	/**
	 * 从左到右以指定的符号和指定的长度分隔字符串
	 * 
	 * @param str
	 *            需要分隔的字符串
	 * @param split
	 *            分隔符
	 * @param n
	 *            分隔长度,须为正整数，否则返回原字符串
	 * @return 分隔后的字符串
	 */
	public static String splitString(String str, char split, int n) {
		StringBuilder builder = new StringBuilder(str);
		if (n > 0) {
			for (int i = n; i < builder.length(); i += (n + 1)) {
				builder.insert(i, split);
			}
		}
		return builder.toString();
	}

	/*
	 * public static boolean judgeMoney(String str) { boolean flag = false; if
	 * (str != null || !str.equals("")) { if (str.startsWith("0") ||
	 * str.startsWith("-")) { flag = false; } else { flag = true; } } return
	 * flag; }
	 */

	/**
	 * 时间转换
	 * @return
	 */
	public static String formatData(String s) {
		if (s == null || s.length() < 1) {
			return "";
		} else {
			String m = s.replace("/", "");
			StringBuffer str = new StringBuffer(m);
			str.insert(4, "/");
			str.insert(7, "/");

			return str.toString();
		}
	}

	/**
	 * 时间转换 day/month/year
	 */
	public static String formatData2(String s) {
		if (s == null || s.length() < 1) {
			return "";
		} else {
			String day = s.substring(0, 2);
			String month = s.substring(2, 4);
			String year = s.substring(4);
			s = year + month + day;
			String m = s.replace("/", "");
			StringBuffer str = new StringBuffer(m);
			str.insert(4, "/");
			str.insert(7, "/");

			return str.toString();
		}
	}

	/**
	 * @param dateStr
	 * @param resourseFormatStr
	 * @param targetFormatStr
	 * @return
	 */
	public static String dateFormateTransfer(String dateStr, String resourseFormatStr, String targetFormatStr) {
		SimpleDateFormat resourceDateFormat = new SimpleDateFormat(resourseFormatStr);
		SimpleDateFormat targetDateFormat = new SimpleDateFormat(targetFormatStr);
		try {
			return targetDateFormat.format(resourceDateFormat.parse(dateStr));
		} catch (ParseException e) {
			MMLogManager.logE(e.toString());
		}
		return dateStr;
	}

	/**
	 * 判断应用是否安装
	 * 
	 * @param mContext
	 * @param packageName
	 *            应用的包名
	 * @return
	 */
	public static boolean checkApkExisted(Context mContext, String packageName) {
		if (packageName == null || "".equals(packageName)) {
			return false;
		}
		try {
//			ApplicationInfo appInfo = mContext.getPackageManager().getApplicationInfo(packageName,
//					PackageManager.GET_UNINSTALLED_PACKAGES);
			PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(packageName,
					PackageManager.GET_ACTIVITIES);
			return true;
		} catch (NameNotFoundException e) {
			MMLogManager.logE(e.toString());
			return false;
		}
	}

	/**
	 * 字符折行显示
	 * 
	 * @param src
	 * @param maxCharPerCol
	 *            每行最多字符个数
	 * @return
	 */
	public static String formatStringByMaxLength(String src, int maxCharPerCol) {
		if (src == null || src.length() <= maxCharPerCol) {
			return src;
		}

		int row = (src.length() + maxCharPerCol - 1) / maxCharPerCol;// 行数
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < row; i++) {
			int start = i * maxCharPerCol;// 起始
			int end = start + maxCharPerCol;// 结束
			end = (end > src.length()) ? src.length() : end;
			sb.append(src.substring(start, end));
			if (!(end == src.length())) {
				sb.append('\n');
			}
		}
		return sb.toString();
	}

	/**
	 * 字符折行显示
	 * 
	 * @param src
	 * @param maxCharPerCol
	 *            每行最多字符个数
	 * @return
	 */
	public static String formatStringByMaxLengthForFavor(String src, int maxCharPerCol) {
		if (src == null || src.length() <= maxCharPerCol) {
			return src;
		}

		int row = (src.length() + maxCharPerCol - 1) / maxCharPerCol;// 行数
		int len = src.length() / row + src.length() % row;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < row; i++) {
			int start = i * len;// 起始
			int end = start + len;// 结束
			end = (end > src.length()) ? src.length() : end;
			sb.append(src.substring(start, end));
			if (!(end == src.length())) {
				sb.append('\n');
			}
		}
		return sb.toString();
	}

	/**
	 * 是否存在问号
	 * 
	 * @param url
	 */
	public static boolean isWen(String url) {
		if (isObjectEmpty(url)) {
			return false;
		}
		return url.indexOf("?") != -1;
	}

	/**
	 * 对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isObjectEmpty(String obj) {
		return obj == null || "".equals(obj);
	}

	/****
	 * 关闭系统键盘
	 * 
	 * @param focusEt
	 */
	public static void hideSystemBoard(View focusEt) {
		if (null == focusEt)
			return;
		Context ctx = focusEt.getContext();
		if (android.os.Build.VERSION.SDK_INT <= 10) {
			InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(focusEt.getWindowToken(), 0);
		} else {
			InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(focusEt.getWindowToken(), 0);
			try {
				Class<EditText> cls = EditText.class;
				Method setSoftInputShownOnFocus;
				setSoftInputShownOnFocus = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
				setSoftInputShownOnFocus.setAccessible(true);
				setSoftInputShownOnFocus.invoke(focusEt, false);
			} catch (Exception e) {
				// e.printStackTrace();
			}
			try {
				Class<EditText> cls = EditText.class;
				Method setShowSoftInputOnFocus;
				setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
				setShowSoftInputOnFocus.setAccessible(true);
				setShowSoftInputOnFocus.invoke(focusEt, false);
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}

	// 从文件读取byte[]数据
	public static byte[] getFileBytesFromFileName(File file) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fin = null;

		try {
			fin = new FileInputStream(file);
			int b = 0;
			while ((b = fin.read()) != -1) {
				baos.write(b);
			}

		} catch (FileNotFoundException e) {
			MMLogManager.logE(e.toString());
		} catch (IOException e) {
			MMLogManager.logE(e.toString());
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}

	// 把byte[]数据存储文件
	public static void savaBytesToFile(byte[] bytes, File file) {

		BufferedOutputStream stream = null;
		try {
			FileOutputStream fos = new FileOutputStream(file);
			stream = new BufferedOutputStream(fos);
			stream.write(bytes);
			fos.close();
		} catch (Exception e) {
			MMLogManager.logE(e.toString());
		} finally {
			try {
				if (null != stream) {
					stream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 方法功能说明：通过json对象，获取String对象
	 * 
	 * @param jsonObject
	 * @param key
	 * @return
	 */
	public static String getStringByJSON(JSONObject jsonObject, String key) {
		try {
			return (jsonObject != null) ? (jsonObject.getString(key)) : "";
		} catch (JSONException e) {
			MMLogManager.logE(e.toString());
		}
		return "";
	}

	/**
	 * 获取字符串的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 * 
	 * @param s
	 *            需要得到长度的字符串
	 * @return int 得到的字符串长度
	 */
	public static int length(String s) {
		if (s == null)
			return 0;
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}

	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	/**
	 * 字符串首位和末位不变，中间用*号替代。
	 * 
	 * @param str
	 * @return
	 */
	public static String format1to1(String str) {
		if (str == null || "".equals(str)) {
			return "";
		}
		if (str.length() <= 2) {
			return str;
		}
		String start = str.substring(0, 1);
		String end = str.substring(str.length() - 1, str.length());
		String star = "";
		for (int i = 0; i < str.length() - 2; i++) {
			star += "*";
		}
		return start + star + end;
	}

	/**
	 * 时间转换 2015-11-18
	 **/
	public static String formatTime(String s) {
		if (s == null || s.length() < 1) {
			return "";
		} else {
			String m = s.replace(":", "");
			StringBuffer str = new StringBuffer(m);
			str.insert(2, ":");
			str.insert(5, ":");
			return str.toString();
		}
	}

	/**
	 * 根据键名获取Intent中Int类型的值
	 *
	 * @param activity
	 * @param key
	 * @return
	 */
	public static int getIntentExtrasIntValue(Activity activity, String key) {
		try {
			if (activity != null && activity.getIntent() != null && activity.getIntent().getExtras() != null) {
				return activity.getIntent().getExtras().getInt(key);
			} else {
				return -1;
			}
		} catch (Exception e) {
			MMLogManager.logE(e.toString());
			return -1;
		}
	}

	/**
	 * 根据键名获取Intent中Double类型的值
	 *
	 * @param activity
	 * @param key
	 * @return
	 */
	public static Double getIntentExtrasDoubletValue(Activity activity, String key) {
		try {
			if (activity != null && activity.getIntent() != null && activity.getIntent().getExtras() != null) {
				return activity.getIntent().getExtras().getDouble(key);
			} else {
				return 0.00;
			}
		} catch (Exception e) {
			MMLogManager.logE(e.toString());
			return 0.00;
		}
	}

	/**
	 * 根据键名获取Intent中String类型的值
	 *
	 * @param activity
	 * @param key
	 * @return
	 */
	public static String getIntentExtrasStringValue(Activity activity, String key) {
		try {
			if (activity != null && activity.getIntent() != null && activity.getIntent().getExtras() != null) {
				return activity.getIntent().getExtras().getString(key);
			} else {
				return "";
			}

		} catch (Exception e) {
			MMLogManager.logE(e.toString());
			return "";
		}
	}

	/**
	 * 根据键名获取Intent中boolean类型的值
	 *
	 * @param activity
	 * @param key
	 * @return
	 */
	public static boolean getInExtrasBooleanValue(Activity activity, String key) {
		try {
			if (activity != null && activity.getIntent() != null && activity.getIntent().getExtras() != null) {
				return activity.getIntent().getExtras().getBoolean(key);
			} else {
				return false;
			}
		} catch (Exception e) {
			MMLogManager.logE(e.toString());
			return false;
		}
	}

	/**
	 * 判断不能包含某些特殊字符包括空格
	 *
	 * @param str
	 * @return
	 */
	public static boolean isExtraChar(String str) {
		String temp_str = " ()%*-',$&|￥\\\".";
		char ch;
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (temp_str.indexOf(ch) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 动态设置按钮位置(放置页面底部)
	 * 
	 * @param view
	 *            按钮
	 * @param act
	 *            当前页面
	 */
	public static void setViewLocation2Bottom(final View view, final Activity act) {
		ViewTreeObserver vt = view.getViewTreeObserver();
		vt.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				ViewGroup contentView = (ViewGroup) act.getWindow().getDecorView().findViewById(android.R.id.content);
				int[] viewLocation = new int[2];
				view.getLocationOnScreen(viewLocation);
				int currentHeight = viewLocation[1] + view.getHeight();
				int dh = contentView.getHeight();
				if (dh - currentHeight <= 0)
					return;
				ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
				Class paramsClass = layoutParams.getClass();
				int top;
				try {
					Field topField = paramsClass.getDeclaredField("mTop");
					topField.setAccessible(true);
					top = (int) topField.get(layoutParams);
				} catch (Exception e) {
					MMLogManager.logE(String.format("Current LayoutParams %s has no field mTop",layoutParams.getClass().getName()));
					top = 0;
				}
				layoutParams.topMargin = top + dh - currentHeight;
				view.setLayoutParams(layoutParams);

			}
		});
	}

	/**
	 * 展开view动画
	 * @param v
	 * @param duration
     */
	public static void expandView(final View v, long duration) {
		if (v.isShown())
			return;
		v.measure(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		final int targetHeight = v.getMeasuredHeight();
		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		Animation expandAnimation = new Animation() {

			/**
			 *
			 * @see android.view.animation.Animation#applyTransformation(float,
			 *      android.view.animation.Transformation)
			 * @param interpolatedTime
			 * @param t
			 */

			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {

				// TODO Auto-generated method stub
				MMLogManager.logD("interpolatedTime====>>>" + interpolatedTime);
				v.getLayoutParams().height = interpolatedTime == 1 ? LayoutParams.WRAP_CONTENT
						: (int) (targetHeight * interpolatedTime);
				v.requestLayout();
			}

			/**
			 *
			 * @see android.view.animation.Animation#willChangeBounds()
			 * @return
			 */

			@Override
			public boolean willChangeBounds() {

				return Boolean.TRUE;

			}
		};
		expandAnimation.setDuration(duration);
		expandAnimation.setInterpolator(new LinearInterpolator());
		v.startAnimation(expandAnimation);
	}
	/**
	 * 收起view动画
	 * @param v
	 * @param duration
     */
	public static void collapseView(final View v, long duration) {
		if (!v.isShown())
			return;
		final int targetHeight = v.getMeasuredHeight();
		Animation collapseAnimation = new Animation() {

			/**
			 *
			 * @see android.view.animation.Animation#applyTransformation(float,
			 *      android.view.animation.Transformation)
			 * @param interpolatedTime
			 * @param t
			 */

			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {

				// TODO Auto-generated method stub
				if (1 == interpolatedTime) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().height = (int) (targetHeight - (targetHeight * interpolatedTime));
					v.requestLayout();
				}

			}

			/**
			 *
			 * @see android.view.animation.Animation#willChangeBounds()
			 * @return
			 */

			@Override
			public boolean willChangeBounds() {

				return true;

			}
		};
		collapseAnimation.setDuration(duration);
		collapseAnimation.setInterpolator(new LinearInterpolator());
		v.startAnimation(collapseAnimation);
	}

	/**
	 * 银行图标
	 */
	public static final Map<String, String> BANK_ICON = new HashMap<String, String>() {
		{
			put("102100099996", "bank_logo_102");//中国工商银行
			put("103100000026", "bank_logo_103");//中国农业银行
			put("104100000004", "bank_logo_104");//中国银行
			put("301290000007", "bank_logo_301");//交通银行
			put("302100011000", "bank_logo_302");//中信银行
			put("303100000006", "bank_logo_303");//中国光大银行
			put("304100040000", "bank_logo_304");//华夏银行
			put("305100000013", "bank_logo_305");//中国民生银行
			put("306581000003", "bank_logo_306");//广发银行
			put("307584007998", "bank_logo_307");//平安银行
			put("308584000013", "bank_logo_308");//招商银行
			put("309391000011", "bank_logo_309");//兴业银行
			put("310290000013", "bank_logo_310");//上海浦东发展银行
			//put("313290000017", "bank_logo_313");//城市商业银行
			put("403100000004", "bank_logo_403");//中国邮政储蓄银行
			put("313301099999", "bank_logo_3131");//江苏银行股份有限公司
			put("313222080002", "bank_logo_3132");//大连银行
			put("313332082914", "bank_logo_31333");//宁波银行
			put("322290000011", "bank_logo_322");//上海农商银行
			put("325290000012", "bank_logo_325");//上海银行

			put("102中国工商银行", "bank_logo_102");//中国工商银行
			put("103中国农业银行", "bank_logo_103");//中国农业银行
			put("104中国银行", "bank_logo_104");//中国银行
			put("301交通银行", "bank_logo_301");//交通银行
			put("302中信银行", "bank_logo_302");//中信银行
			put("303中国光大银行", "bank_logo_303");//中国光大银行
			put("304华夏银行", "bank_logo_304");//华夏银行
			put("305中国民生银行", "bank_logo_305");//中国民生银行
			put("306广发银行", "bank_logo_306");//广发银行
			put("307平安银行", "bank_logo_307");//平安银行
			put("308招商银行", "bank_logo_308");//招商银行
			put("309兴业银行", "bank_logo_309");//兴业银行
			put("310上海浦东发展银行", "bank_logo_310");//上海浦东发展银行
			put("325上海银行", "bank_logo_313");//上海银行
			put("403中国邮政储蓄银行", "bank_logo_403");//中国邮政储蓄银行
			put("313江苏银行", "bank_logo_3131");//江苏银行股份有限公司
		}
	};

	/**
	 * 根据银行号获取对应的图片
	 *
	 * @param bankNo
	 * @return
	 */
	public static Bitmap getBankIconBitmap(Context context, String bankNo, String bankName) {
		String iconName = BANK_ICON.get(bankNo);
		if (TextUtils.isEmpty(iconName)) {
			if (bankNo.length() >= 3 && !TextUtils.isEmpty(bankName)) {
				iconName = BANK_ICON.get(bankNo.substring(0, 3) + bankName.substring(0, bankName.indexOf("银行") + 2));
				if (TextUtils.isEmpty(iconName)) {
					iconName = "bank_logo_default";
				}

			} else {
				iconName = "bank_logo_default";
			}
		}
		return MMUtils.readBitMap(context, iconName);
	}

	/**
	 * 根据银行号获取对应的图片
	 *
	 * @param bankNo
	 * @return
	 */
	public static int getBankIconID(Context context, String bankNo) {
		String iconName = BANK_ICON.get(bankNo);
		if (TextUtils.isEmpty(iconName)) {
			iconName = "bank_logo_default";
		}
		return 	 context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
	}


	/**
	 * 银行图标
	 */
	public static final Map<String, String> BANK_WATER_BG = new HashMap<String, String>() {
		{
			put("102100099996", "bank_water_102");//中国工商银行
			put("103100000026", "bank_water_103");//中国农业银行
			put("104100000004", "bank_water_104");//中国银行
			put("301290000007", "bank_water_301");//交通银行
			put("302100011000", "bank_water_302");//中信银行
			put("303100000006", "bank_water_303");//中国光大银行
			put("304100040000", "bank_water_304");//华夏银行
			put("305100000013", "bank_water_305");//中国民生银行
			put("306581000003", "bank_water_306");//广发银行
			put("307584007998", "bank_water_307");//平安银行
			put("308584000013", "bank_water_308");//招商银行
			put("309391000011", "bank_water_309");//兴业银行
			put("310290000013", "bank_water_310");//上海浦东发展银行
			//put("313290000017", "bank_water_313");// 城市商业银行
			put("403100000004", "bank_water_403");//中国邮政储蓄银行
			put("313301099999", "bank_water_3133");//江苏银行股份有限公司
			put("313222080002", "bank_water_3132");//大连银行
			put("313332082914", "bank_water_31333");//宁波银行
			put("322290000011", "bank_water_322");//上海农商银行
			put("325290000012", "bank_water_325");//上海银行

		}
	};

	/**
	 * 根据银行号获取对应的图片
	 *
	 * @param bankNo
	 * @return
	 */
	public static int getBankWatterBgID(Context context, String bankNo) {
		String iconName = BANK_WATER_BG.get(bankNo);
		if (TextUtils.isEmpty(iconName)) {
			iconName = "bank_water_default_bg";
		}
		return 	 context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
	}

	/**
	 * 通过反射显示光标 只能是默认颜色
	 *
	 * @param currEt
	 */
	public static void setCursorColorAndVisible(EditText currEt) {
		// 设置显示光标
		currEt.setCursorVisible(true);
		changeCursorArrow(currEt,"mCursorDrawableRes","mCursorDrawable");
		changeCursorArrow(currEt,"mTextSelectHandleLeftRes","mSelectHandleLeft");
		changeCursorArrow(currEt,"mTextSelectHandleRightRes","mSelectHandleRight");
		changeCursorArrow(currEt,"mTextSelectHandleRes","mSelectHandleCenter");

	}
	//"mCursorDrawableRes"
	//"mEditor"
	//"mCursorDrawableRes"
	private static void changeCursorArrow(EditText currEt, String resName, String drawableName){
		try {
			//获取光标资源id
			Field cursorField = TextView.class.getDeclaredField(resName);
			cursorField.setAccessible(true);
			int mCursorDrawableRes = cursorField.getInt(currEt);

			// cursorField.set(currEt, 0);
			//获取TextView mEditor对象
			Field fEditor = TextView.class.getDeclaredField("mEditor");
			fEditor.setAccessible(true);
			Object editor = fEditor.get(currEt);
			Class<?> clazz = Class.forName("android.widget.Editor");
			//获取TextView 光标对象
			Field fCursorDrawable = clazz.getDeclaredField(drawableName);
			fCursorDrawable.setAccessible(true);
			//获取光标图片
			Drawable cursorDrawable;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				cursorDrawable = currEt.getContext().getDrawable(mCursorDrawableRes);
			}else{
				cursorDrawable = currEt.getContext().getResources().getDrawable(mCursorDrawableRes);
			}
			if (cursorDrawable == null) {
				return;
			}
			//改变图片颜色
			cursorDrawable = CcbSkinColorTool.getInstance().changeDrawableColor(cursorDrawable);
			if(fCursorDrawable.getType().isArray()) {
				Drawable[] drawables = new Drawable[]{cursorDrawable, cursorDrawable};
				fCursorDrawable.set(editor, drawables);
			}else{
				fCursorDrawable.set(editor,cursorDrawable);
			}
		} catch (Exception e) {
			MMLogManager.logW("drawable set failure"+e.toString());
		}
	}

	/**
	 * 判断是否是密码框
	 * @param currEditText
	 * @return
     */
	public static boolean isPasswordEditText(EditText currEditText){
		if(null==currEditText)
			return false;
		int inputType= currEditText.getInputType();
		return (inputType==(InputType.TYPE_CLASS_TEXT| InputType.TYPE_NUMBER_VARIATION_PASSWORD)||inputType==(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD)
				||inputType==(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)||inputType==(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD));
	}

	/**
	 * 设置所有的View状态
	 * @param rootView 根节点view
	 * @param enable 是否可点击
	 */
	public static void setAllChildEnable(View rootView, boolean enable) {
		rootView.setEnabled(enable);
		if (rootView instanceof ViewGroup) {
			ViewGroup viewGroup = (ViewGroup) rootView;
			for (int i = 0, childCount = viewGroup.getChildCount(); i < childCount; i++) {
				View child = viewGroup.getChildAt(i);
				setAllChildEnable(child, enable);
			}
		}
	}
	
	/**
	 * 计算适合指定文本框的字体大小
	 *
	 * @param tv
	 * @return
	 */
	public static float calculateFitTextSize(TextView tv) {
		float defaultTextSize = tv.getTextSize();
		String text = tv.getText().toString();
		Paint paint = new Paint();
		paint.set(tv.getPaint());
		paint.setTextSize(defaultTextSize);
		int targetWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight();
		if (paint.measureText(text) <= targetWidth) {
			return defaultTextSize;
		}
		float hi = defaultTextSize;
		float lo = 2;
		final float threshold = 0.5f;

		while (hi - lo > threshold) {
			float size = (hi + lo) / 2;
			paint.setTextSize(size);
			if (paint.measureText(text) > targetWidth) {
				hi = size;
			} else {
				lo = size;
			}
		}
		return lo;
	}

	public static void setRipple(View rippleView) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
			return;
		Drawable drawable =rippleView.getBackground();
		ColorStateList rippleColor= ColorStateList.valueOf(CcbSkinColorTool.getInstance().getEnableColor());
		if(null==drawable)
			return;
		if(drawable instanceof RippleDrawable){
			((RippleDrawable)drawable).setColor(rippleColor);

			return;
		}
		RippleDrawable rippleDrawable = new RippleDrawable(rippleColor, rippleView.getBackground(), null);
		rippleView.setBackground(rippleDrawable);
	}


	/**
	 * 自动弹出键盘
	 * @param et 需要弹出键盘的输入框
     */
	public static void autoShowKeyboard(final EditText et){
		et.post(new Runnable() {
			@Override
			public void run() {
				et.dispatchTouchEvent(getSimulationDownEvent(et));
				et.dispatchTouchEvent(getSimulationUpEvent(et));
			}
		});
	}

	/**
	 * 生成按下触摸时间
	 * @return {@link MotionEvent#ACTION_DOWN}
     */
	private static MotionEvent getSimulationDownEvent(EditText et) {
		long downTime = SystemClock.uptimeMillis();
		long eventTime = downTime + 100;

		float x = et.getWidth();
		float y = et.getHeight();
		return MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0);
	}

	/**
	 * 生成放开触摸事件
	 * @return {@link MotionEvent#ACTION_UP}
     */
	private static MotionEvent getSimulationUpEvent(EditText et) {
		long downTime = SystemClock.uptimeMillis();
		long eventTime = downTime + 100;

		float x = et.getWidth();
		float y = et.getHeight();
		return MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, 0);
	}

	/**
	 * 银行图标
	 */
	public static final Map<String, String> LOONG_PAY_BANK_ICON = new HashMap<String, String>() {
		{
			put("102100099996", "loong_pay_bank_logo_102");//中国工商银行
			put("103100000026", "loong_pay_bank_logo_103");//中国农业银行
			put("104100000004", "loong_pay_bank_logo_104");//中国银行
			put("301290000007", "loong_pay_bank_logo_301");//交通银行
			put("302100011000", "loong_pay_bank_logo_302");//中信银行
			put("303100000006", "loong_pay_bank_logo_303");//中国光大银行
			put("304100040000", "loong_pay_bank_logo_304");//华夏银行
			put("305100000013", "loong_pay_bank_logo_305");//中国民生银行
			put("306581000003", "loong_pay_bank_logo_306");//广发银行
			put("307584007998", "loong_pay_bank_logo_307");//平安银行
			put("308584000013", "loong_pay_bank_logo_308");//招商银行
			put("309391000011", "loong_pay_bank_logo_309");//兴业银行
			put("310290000013", "loong_pay_bank_logo_310");//上海浦东发展银行
			//put("313290000017", "bank_logo_313");//城市商业银行
			put("403100000004", "loong_pay_bank_logo_403");//中国邮政储蓄银行
			put("313301099999", "loong_pay_bank_logo_3133");//江苏银行股份有限公司
			put("313222080002", "loong_pay_bank_logo_3132");//大连银行
			put("313332082914", "loong_pay_bank_logo_31333");//宁波银行
			put("322290000011", "loong_pay_bank_logo_322");//上海农商银行
			put("325290000012", "loong_pay_bank_logo_325");//上海银行

		}
	};

	/**
	 * 根据银行号获取对应的图片
	 *
	 * @param bankNo
	 * @return
	 */
	public static Bitmap getBankIconBitmap(Context context, String bankNo) {
		String iconName = LOONG_PAY_BANK_ICON.get(bankNo);
		if (TextUtils.isEmpty(iconName)) {
			iconName = "loong_pay_bank_logo_default";
		}
		return MMUtils.readBitMap(context, iconName);
	}

	/**
	 * 根据银行号获取对应的图片
	 *
	 * @param bankNo
	 * @return
	 */
	public static int getBankIconIdentifier(Context context, String bankNo) {
		String iconName = LOONG_PAY_BANK_ICON.get(bankNo);
		if (TextUtils.isEmpty(iconName)) {
			iconName = "loong_pay_bank_logo_default";
		}
		return context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
	}

	/**
	 * 返回 20140707090909的格式
	 *
	 * @return
	 */
	public static String getYyyyMMddHHmmssFormat() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(System.currentTimeMillis()).replaceAll("[-:\\s]", "");
	}

	/**
	 * 截取账号的后4位
	 *
	 * @param accNo
	 * @return
	 */
	public static String getCardNo4(String accNo) {
		if (TextUtils.isEmpty(accNo)) {
			return "";
		}
		return (accNo.length() >= 4) ? (accNo.substring(accNo.length() - 4, accNo.length())) : accNo;
	}

	/**
	 * 姓名（第二位用星号代替）
	 *
	 *
	 * @param name
	 * @return
	 */
	public static String formatName(String name) {
		if (TextUtils.isEmpty(name))
			return "";
		if (name.length() == 1)
			return name;
		if (name.length() == 2)
			return name.substring(0, 1) + "*";
		if (name.length() > 2)
			return name.substring(0, 1) + "*" + name.substring(2, name.length());
		return "";
	}

	/**
	 * 根据身份证号获取当前身份证拥有者的年龄
	 * @param card_id
	 * @return
     */
	public static int getCurUserAgeByID(String card_id){
		int age=0;
		if (!TextUtils.isEmpty(card_id)&&card_id.length()==18){
			String user_born_year=card_id.substring(6,10);
			int born_year= parseStringToInt(user_born_year);
			int cur_year= Calendar.getInstance().get(Calendar.YEAR);
			age=cur_year-born_year;
		}
		return age;
	}

	public static int parseStringToInt(String num) {
		try {
			return Integer.parseInt(num);
		} catch (Exception e) {
			MMLogManager.logE(e.getMessage() + "");
			return 0;
		}
	}
}
