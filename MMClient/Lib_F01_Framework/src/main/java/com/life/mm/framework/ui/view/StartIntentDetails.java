package com.life.mm.framework.ui.view;

import android.os.Bundle;

import com.life.mm.framework.app.base.activity.BaseActivity;

/**
 * Created by wangjia on 2016/5/17.
 *  组合控件 模型
 */
public class StartIntentDetails {
    public static final int HIDEIMA=-1;
    private final String title;
    private final int descriptionId; //-1标识不显示图片
    private final Class<? extends BaseActivity> activityClass;
    private Boolean showLine=true; //是否显示下划线 ，默认显示
    private Bundle bundle=null;//跳转参数设置

    public StartIntentDetails(String titleId, int descriptionId, Class<? extends BaseActivity> activityClass) {
        super();
        this.title = titleId;
        this.descriptionId = descriptionId;
        this.activityClass = activityClass;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Boolean getShowLine() {
        return showLine;
    }

    public void setShowLine(Boolean showLine) {
        this.showLine = showLine;
    }

    public String getTitle() {
        return title;
    }

    public int getDescriptionId() {
        return descriptionId;
    }

    public Class<? extends BaseActivity> getActivityClass() {
        return activityClass;
    }
}
