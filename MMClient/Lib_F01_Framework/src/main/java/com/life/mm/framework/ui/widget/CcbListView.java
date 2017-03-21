package com.life.mm.framework.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EdgeEffect;
import android.widget.ListView;

import com.life.mm.common.log.MMLogManager;
import com.life.mm.framework.R;
import com.life.mm.framework.skin.CcbSkinColorTool;
import com.life.mm.framework.skin.ICcbGeneralSkin;
import com.life.mm.framework.ui.view.mmOnItemClickListener;

import java.lang.reflect.Field;

/**
 * Created by wangjia on 2016/5/4.
 */
public class CcbListView extends ListView implements ICcbGeneralSkin {

    protected boolean isGeneralSkin = true;

    public CcbListView(Context context) {
        super(context);
        initAttrs(context,null);
    }

    public CcbListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
    }

    public CcbListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context,attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        if (null == attrs)
            return;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Ccb_Custom);
        isGeneralSkin = a.getBoolean(R.styleable.Ccb_Custom_generalSkin, true);
        a.recycle();
    }

    @Override
    public void onSkinChange() {
        if (!isGeneralSkin)
            return;
        setEdgeGlowColor();
    }

    public void setGeneralSkin(boolean generalSkin) {
        isGeneralSkin = generalSkin;
        if(isGeneralSkin)
            onSkinChange();
    }

    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private  void setEdgeGlowColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            EdgeEffect edgeEffectTop = new EdgeEffect(getContext());
            edgeEffectTop.setColor(CcbSkinColorTool.getInstance().getSkinColor());
            try {
                for (String fieldName :new String[]{"mEdgeGlowTop","mEdgeGlowBottom"}){

                    Field field= AbsListView.class.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(this,edgeEffectTop);
                }
            } catch (final Exception ignored) {
                MMLogManager.logE(ignored.toString());
            }
        }

    }

    private OnItemClickListener mOnClickListener;

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        setOnItemClickListener(listener, -1);
    }

    public void setOnItemClickListener(OnItemClickListener listener, long controlClickTime) {
        if (listener instanceof mmOnItemClickListener) {
            super.setOnItemClickListener(listener);
            return;
        }
        mOnClickListener = listener;
        super.setOnItemClickListener(getDefaultCcbOnItemClickListener(controlClickTime));
    }

    private mmOnItemClickListener getDefaultCcbOnItemClickListener(long controlClickTime) {
        return -1 == controlClickTime ? new mmOnItemClickListener() {
            @Override
            public void mmOnItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null == mOnClickListener)
                    return;
                mOnClickListener.onItemClick(parent, view, position, id);
            }
        } : new mmOnItemClickListener(controlClickTime) {
            @Override
            public void mmOnItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null == mOnClickListener)
                    return;
                mOnClickListener.onItemClick(parent, view, position, id);
            }
        };
    }
}
