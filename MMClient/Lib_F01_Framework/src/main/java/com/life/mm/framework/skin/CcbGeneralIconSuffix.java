package com.life.mm.framework.skin;

import android.graphics.Color;

/**
 * Project: CCBClient_V4.00<br/>
 * Package: com.ccb.framework.ui.skin<br/>
 * ClassName: CcbGeneralSuffix<br/>
 * Description: 动态主题图标后缀枚举 <br/>
 * Date: 2016/11/7 15:44 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 */

public enum CcbGeneralIconSuffix  {
    /**
     * 主题分类
     */
    BLUE(Color.parseColor("#09b6f2"), "", "_blue"),
    LIGHT_BLUE(Color.parseColor("#0066b3"), "", ""),
    //TODO: 2016/11/25   暂时取消colorful主题,以后业务需要再开放
    COLORFUL(0, "", "");

    CcbGeneralIconSuffix(int color, String path, String suffix) {
        this.color = color;
        this.path = path;
        this.suffix = suffix;
    }

    /**
     * 对应颜色
     */
    private int color;
    /**
     * 图片路径
     */
    private String path;
    /**
     * 图片后缀
     */
    private String suffix;

    public int getColor() {
        return color;
    }

    public String getPath() {
        return path;
    }

    public String getSuffix() {
        return suffix;
    }


    public static CcbGeneralIconSuffix getCurrentSuffix() {

        return getDrawableSuffix(CcbSkinColorTool.getInstance().getSkinColor());
    }

    /**
     * 获取当前颜色对应的后缀
     *
     * @param color 当前换肤颜色
     * @return {@link CcbGeneralIconSuffix}
     */
    public static CcbGeneralIconSuffix getDrawableSuffix(int color) {
        CcbGeneralIconSuffix currentSuffix = COLORFUL;
        for (CcbGeneralIconSuffix suffix : values()) {
            if (color != suffix.getColor())
                continue;
            currentSuffix = suffix;
            break;
        }
        return currentSuffix;
    }
}
