package com.life.mm.framework.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.ui.widget <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/3 15:53. <P>
 * Function: <P>
 * Modified: <P>
 */

public class NoScrollViewPager extends ViewPager {

    private final String TAG = NoScrollViewPager.class.getSimpleName();
    private boolean isCanScroll = true;  //标记该viewpager是否可滑动

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    /**
     * getRawX(),触摸点相对于屏幕原点的x坐标
     * getX(),触摸点相对于其所在组件原点的x坐标
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isCanScroll && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onInterceptTouchEvent(ev);
    }
}