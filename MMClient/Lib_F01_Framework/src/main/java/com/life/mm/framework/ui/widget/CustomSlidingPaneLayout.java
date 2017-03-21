package com.life.mm.framework.ui.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.life.mm.common.log.MMLogManager;

import java.lang.reflect.Field;

/**
 * ProjectName:MM <P>
 * ClassName: <P>
 * Created by zfang on 2017/3/1 09:21. <P>
 * Function: <P>
 * Modified: <P>
 */

public class CustomSlidingPaneLayout extends SlidingPaneLayout {

    private float mInitialMotionX;
    private float mInitialMotionY;
    private float mEdgeSlop;

    public CustomSlidingPaneLayout(Context context) {
        this(context, null);
    }

    public CustomSlidingPaneLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ViewConfiguration config = ViewConfiguration.get(context);
        mEdgeSlop = config.getScaledEdgeSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionX = ev.getX();
                mInitialMotionY = ev.getY();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final float x = ev.getX();
                final float y = ev.getY();
                /**
                 * The user should always be able to "close" the pane, so we only
                 * check for child scrollability if the pane is currently closed.
                 *
                 */
                if ((mInitialMotionX > mEdgeSlop && !isOpen()
                        && canScroll(this, false, Math.round(x - mInitialMotionX), Math.round(x), Math.round(y)))||!mCanSlide) {

                    /**
                     * How do we set super.mIsUnableToDrag = true?, send the parent a cancel event
                     *
                     */
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
                    return super.onInterceptTouchEvent(cancelEvent);
                }
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean mCanSlide = false;


    public boolean isSlideable() {
        return mCanSlide;
    }

    public void setCanSlide(boolean mCanSlide) {
        this.mCanSlide = mCanSlide;
        setSuperCanSlide(mCanSlide);
    }


    public void setSuperCanSlide(boolean mCanSlide){
        try {
            Field mCanSlideField = SlidingPaneLayout.class.getDeclaredField("mCanSlide");
            mCanSlideField.setAccessible(true);
            mCanSlideField.set(this,mCanSlide);
        } catch (Exception e) {
            MMLogManager.logE(e.toString());
        }
    }
}
