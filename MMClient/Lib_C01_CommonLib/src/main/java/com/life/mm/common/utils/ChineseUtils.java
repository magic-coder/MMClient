package com.life.mm.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * 汉语拼音工具类
 *
 * @author Genty
 */
public class ChineseUtils {

    private static final String UNIT = "万仟佰拾亿仟佰拾万仟佰拾元角分";

    private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";

    private static final double MAX_VALUE = 9999999999999.99D;

    private ChineseUtils() {
    }

    /**
     * unicode编码范围： 汉字：[0x4E00,0x9FA5]（或十进制[19968,40869]）
     * 数字：[0x0030,0x0039]（或十进制[48, 57]） 小写字母：[0x0061,0x007A]（或十进制[97, 122]）
     * 大写字母：[0x0041,0x005A]（或十进制[65, 90]）
     */

    /**
     * 返回汉字的拼音
     *
     * @param str
     *            汉字(吴嘉)
     * @return 拼音 (wujia)
     */
    public static String getPinYin(String str) {
        // 拼音输出格式
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        // 全部小写
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 设置声调格式:不要声调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // 设置特殊拼音ü的显示格式:用V (比如：绿)
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        StringBuilder pinyin = new StringBuilder();
        try {
            char[] chars = str.toCharArray();
            for (char c : chars) {
                // 返回数组, 是因为可能是多音字, 比如"干"
                String[] array = PinyinHelper.toHanyuPinyinStringArray(c,
                        format);
                if (array != null) {// 代表是汉字
                    pinyin.append(array[0]);
                } else {// 代表不是汉字, 其他字符
                    pinyin.append(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pinyin.toString();
    }

    /**
     * 是否为汉字
     */
    public static boolean isCNChar(char c) {
        return Character.toString(c).matches("[\\u4E00-\\u9FA5]+");
    }

    /**
     * 是否为大写字母
     */
    public static boolean isBigCapital(String capital) {
        return capital.matches("[\\u0041-\\u005A]+");
    }

    /**
     * 是否为汉字字符串(只要包含了一个汉字)
     */
    public static boolean isCNStr(String str) {
        for (char c : str.toCharArray()) {
            if (isCNChar(c)) {// 如果有一个为汉字
                return true;
            }
        }
        // 如果没有一个汉字，全英文字符串
        return false;
    }

    /**
     * 返回汉字拼音的声母
     *
     * @param str
     *            汉字(吴佳)
     * @return 汉字的头部字母 (wj)
     */
    public static String getPinYinHead(String str) {
        StringBuilder head = new StringBuilder();
        for (char c : str.toCharArray()) {
            // 得到拼音字符串
            // "干" ---->>> {"gan1", "gan4"}
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);

            if (pinyinArray != null) { // 代表是汉字
                head.append(pinyinArray[0].charAt(0));
            } else { // 代表不是汉字
                head.append(c);
            }
        }
        return head.toString();
    }

    /**
     * 获得第一个字母
     *
     * @param str
     *            干
     * @return g
     */
    public static String getHeadChar(String str) {
        return getPinYinHead(str).substring(0, 1).toUpperCase();
    }

    /**
     * 将字符串转移为ASCII码
     *
     * @param str
     *            字符串
     * @return ASCII码
     */
    public static String getCnASCII(String str) {
        StringBuffer sb = new StringBuffer();
        byte[] strByte = str.getBytes();
        for (int i = 0; i < strByte.length; i++) {
            sb.append(Integer.toHexString(strByte[i] & 0xff));
        }
        return sb.toString();
    }

    /**
     * 比较两个字符串的大小
     *
     * @param str1
     * @param str2
     * @return 1 代表 str1>str2; -1代表 str2>str1 ;
     */
    public static int compare(String str1, String str2) {// 忽略大小写进行比较
        return getPinYin(str1).compareToIgnoreCase(getPinYin(str2));
    }

    /**
     * 小写金额转大写金额
     *
     * @param amount
     * @return
     */
    public static String moneyToChinese(double amount) {
        if (amount < 0 || amount > MAX_VALUE)
            return "参数非法!";
        long l = Math.round(amount * 100);
        if (l == 0)
            return "零元整";
        String strValue = l + "";
        // i用来控制数
        int i = 0;
        // j用来控制单位
        int j = UNIT.length() - strValue.length();
        String rs = "";
        boolean isZero = false;
        for (; i < strValue.length(); i++, j++) {
            char ch = strValue.charAt(i);

            if (ch == '0') {
                isZero = true;
                if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万'
                        || UNIT.charAt(j) == '元') {
                    rs = rs + UNIT.charAt(j);
                    isZero = false;
                }
            } else {
                if (isZero) {
                    rs = rs + "零";
                    isZero = false;
                }
                rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
            }
        }

        if (!rs.endsWith("分")) {
            rs = rs + "整";
        }
        rs = rs.replaceAll("亿万", "亿");
        return rs;
    }

}
