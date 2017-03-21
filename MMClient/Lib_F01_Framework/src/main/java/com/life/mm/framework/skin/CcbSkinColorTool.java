package com.life.mm.framework.skin;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.life.mm.common.log.MMLogManager;
import com.life.mm.common.preference.MMEditor;
import com.life.mm.common.preference.MMSharedPreferences;
import com.life.mm.framework.R;
import com.life.mm.framework.app.MMApplication;
import com.life.mm.framework.ui.widget.CcbEditText;
import com.life.mm.framework.ui.widget.CcbTextView;
import com.life.mm.framework.utils.MMUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.life.mm.common.log.MMLogManager.logE;


/**
 *
 */
public class CcbSkinColorTool {

    private static CcbSkinColorTool instance = null;

    private static final String GENERAL_SKIN_KEY = "general_skin";
    private static final String GENERAL_SKIN_COLOR_KEY = "general_skin_color";
    private static final String GENERAL_SKIN_COLOR_PROGRESS_KEY = "general_skin_progress";
    private static final String GENERAL_SKIN_COLOR_SUB_PROGRESS_KEY = "general_skin_sub_progress";

    private static MMSharedPreferences sp;

    public synchronized static CcbSkinColorTool getInstance() {
        if (null == instance) {
            instance = new CcbSkinColorTool();
            sp = new MMSharedPreferences(MMApplication.getInstance(), GENERAL_SKIN_KEY, Context.MODE_PRIVATE);
        }
        return instance;
    }

//    private int mSkinColor = Color.parseColor("#0066B3");
    //应业务组要求 默认颜色改为浅蓝
    private int mSkinColor = Color.parseColor("#09b6f2");

    /**
     * 主颜色进度
     */
    private int mainProgress = -1;
    /**
     * 次要颜色进度
     */
    private int mSubProgress = -1;

    /**
     * 获取主要颜色条进度
     */
    public int getMainProgress() {
        return -1 == mainProgress ? sp.getInt(GENERAL_SKIN_COLOR_PROGRESS_KEY, -1) : mainProgress;
    }

    /**
     * 获取饱和度颜色条进度
     */
    public int getSubProgress() {
        return -1 == mSubProgress ? sp.getInt(GENERAL_SKIN_COLOR_SUB_PROGRESS_KEY, -1) : mSubProgress;
    }

    /**
     * 设置主要颜色条进度
     *
     * @param progress 进度
     */
    private void setMainProgress(int progress) {
        this.mainProgress = progress;
    }

    /**
     * 设置饱和颜色条进度
     *
     * @param progress 进度
     */
    private void setSubProgress(int progress) {
        this.mSubProgress = progress;
    }

    /**
     * 获取当前换肤颜色
     */
    public int getSkinColor() {
        mSkinColor = sp.getInt(GENERAL_SKIN_COLOR_KEY, mSkinColor);
        return mSkinColor;
    }

    /**
     * @param alpha 0-1.0
     * @return return color with alpha*255
     */
    public int getSkinColorWithAlpha(float alpha) {
        int red = Color.red(mSkinColor);
        int green = Color.green(mSkinColor);
        int blue = Color.blue(mSkinColor);
        return Color.argb((int) (alpha * 255), red, green, blue);
    }

    /**
     * 保存当前选中换肤的颜色
     *
     * @param color        换肤颜色
     * @param mainProgress 主要进度
     * @param subProgress  饱和度进度
     */
    public void saveThemeColor(int color, int mainProgress, int subProgress) {
        setSkinColor(color);
        MMEditor mbsEditor = sp.edit();

        if (-1 != mainProgress) {
            setMainProgress(mainProgress);
            mbsEditor.putInt(GENERAL_SKIN_COLOR_PROGRESS_KEY, mainProgress);
        }
        if (-1 != subProgress) {
            setSubProgress(subProgress);
            mbsEditor.putInt(GENERAL_SKIN_COLOR_SUB_PROGRESS_KEY, subProgress);
        }
        mbsEditor.putInt(GENERAL_SKIN_COLOR_KEY, color);
        mbsEditor.commit();
    }

    /**
     * 设置换肤颜色
     *
     * @param skinColor 换肤颜色
     */
    public void setSkinColor(int skinColor) {
        this.mSkinColor = skinColor;
    }

    /**
     * 换肤主要实现
     *
     * @param act 当前界面
     */
    public void changeSkin(Activity act) {
        if(null == act)
            return;
        View rootView = act.getWindow().getDecorView();
        changeSkin(rootView);
    }

    public void changeSkin(View rootView) {
        iCcbSkins.clear();
        findSkinView(rootView);

        for (View skinView : iCcbSkins) {
            if (skinView instanceof ICcbGeneralSkin)
                ((ICcbGeneralSkin) skinView).onSkinChange();
            if (skinView instanceof ICcbGeneralIcon)
                ((ICcbGeneralIcon) skinView).onIconChange();
        }
    }
    /**
     * 换肤控件集合
     */
    private List<View> iCcbSkins = Collections.synchronizedList(new LinkedList<View>());

    /**
     * 获取当前界面需要换肤的控件集合
     */
    private void findSkinView(View v) {

        if ((v instanceof ICcbGeneralSkin && ((ICcbGeneralSkin) v).isGeneralSkin()) || (v instanceof ICcbGeneralIcon && ((ICcbGeneralIcon) v).isGeneralIcon())) {
            iCcbSkins.add(v);
        }
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            for (int i = 0, count = group.getChildCount(); i < count; i++) {
                View child = group.getChildAt(i);
                findSkinView(child);
            }
        }
    }

    /**
     * 换肤颜色不可点击状态偏移量
     */
    public static final float LIGHT_OFFSET = 0.372328f;

    /**
     * 换肤颜色选中状态偏移量
     */
    public static final float DARK_OFFSET = 0.611073f;

    /**
     * 获取点击状态颜色
     */
    public int getPressedColor(int targetColor) {
        int skinRed = Color.red(targetColor);
        int skinGreen = Color.green(targetColor);
        int skinBlue = Color.blue(targetColor);
        return Color.rgb((int) (CcbSkinColorTool.DARK_OFFSET * skinRed),
                (int) (CcbSkinColorTool.DARK_OFFSET * skinGreen), (int) (CcbSkinColorTool.DARK_OFFSET * skinBlue));
    }

    public int getPressedColor() {

        return getPressedColor(mSkinColor);
    }


    /**
     * 获取不可点击状态颜色
     */
    public int getEnableColor(int targetColor) {
        int skinRed = Color.red(targetColor);
        int skinGreen = Color.green(targetColor);
        int skinBlue = Color.blue(targetColor);

        int enableRed = (int) (255 * (1 - LIGHT_OFFSET));
        int enableGreen = (int) (255 * (1 - LIGHT_OFFSET));
        int enableBlue = (int) (255 * (1 - LIGHT_OFFSET));

        return Color.rgb((int) (CcbSkinColorTool.LIGHT_OFFSET * skinRed + enableRed),
                (int) (CcbSkinColorTool.LIGHT_OFFSET * skinGreen + enableGreen),
                (int) (CcbSkinColorTool.LIGHT_OFFSET * skinBlue + enableBlue));
    }

    public int getEnableColor() {
        return getEnableColor(mSkinColor);
    }

    /**
     * 获取默认颜色状态
     */
    public ColorStateList getDefaultColorStateList(int targetColor) {
        int[][] states = new int[][]{{android.R.attr.state_pressed}, {-android.R.attr.state_enabled}, {}};
        int pressedColor = CcbSkinColorTool.getInstance().getPressedColor(targetColor);
        int enableColor = CcbSkinColorTool.getInstance().getEnableColor(targetColor);
        int skinColor = targetColor;
        return new ColorStateList(states, new int[]{pressedColor, enableColor, skinColor});
    }

    public ColorStateList getDefaultColorStateList() {
        return getDefaultColorStateList(mSkinColor);
    }

    /**
     * 更改图片颜色 只有正常状态
     */
    public Drawable changeDrawableColor(Drawable drawable) {

        return changeDrawableColor(drawable, COLOR_NULL);
    }

    private static final int COLOR_NULL = Color.TRANSPARENT;

    /**
     * 更改图片颜色
     */
    public Drawable changeDrawableColor(Drawable drawable, int color) {

        if (null == drawable)
            return null;
        int targetColor = COLOR_NULL == color ? mSkinColor : color;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            DrawableCompat.setTint(drawable, targetColor);
            return drawable;
        }
        if (drawable instanceof ColorDrawable) {
            drawable = drawable.mutate();
            ((ColorDrawable) drawable).setColor(targetColor);
        } else {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, targetColor);
        }
        return drawable;
    }


    /**
     * 获取默认selector
     *
     * @return {@link StateListDrawable}
     */
    public Drawable getDefaultSelectorDrawable() {

        return getCustomSelectorDrawable(getSkinColor(), getPressedColor(), getEnableColor());
    }

    /**
     * 生成自定义selector
     *
     * @param normalColor  正常状态颜色
     * @param pressedColor 按下颜色
     * @return {@link StateListDrawable}
     */
    public Drawable getCustomSelectorDrawable(int normalColor, int pressedColor) {

        return getCustomSelectorDrawable(normalColor, pressedColor, 0);
    }

    /**
     * 生成自定义selector
     *
     * @param normalColor  正常状态颜色
     * @param pressedColor 按下颜色
     * @param enableColor  不可用颜色
     * @return {@link StateListDrawable}
     */
    public Drawable getCustomSelectorDrawable(int normalColor, int pressedColor, int enableColor) {

        StateListDrawable listDrawable = new StateListDrawable();
        if (0 != pressedColor) {
            listDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(pressedColor));
        }
        if (0 != enableColor) {
            listDrawable.addState(new int[]{-android.R.attr.state_enabled}, new ColorDrawable(enableColor));
        }
        if (0 != normalColor) {
            listDrawable.addState(new int[]{}, new ColorDrawable(normalColor));
        }
        return listDrawable;
    }

    /**
     * 生成自定义selector颜色
     *
     * @param normalColor  正常状态颜色
     * @param pressedColor 按下颜色
     * @return {@link ColorStateList}
     */
    public ColorStateList getCustomSelectorColor(int normalColor, int pressedColor) {
        int[][] states = new int[][]{{android.R.attr.state_pressed}, {}};
        return new ColorStateList(states, new int[]{pressedColor, normalColor});
    }

    /**
     * 生成自定义selector颜色
     *
     * @param normalColor  正常状态颜色
     * @param pressedColor 按下颜色
     * @param enableColor  不可用颜色
     * @return {@link ColorStateList}
     */
    public ColorStateList getCustomSelectorColor(int normalColor, int pressedColor, int enableColor) {
        int[][] states = new int[][]{{android.R.attr.state_pressed}, {-android.R.attr.state_enabled}, {}};
        return new ColorStateList(states, new int[]{pressedColor, enableColor, normalColor});
    }




    /**
     * 从Imageview获取图片资源id 背景或者src
     *
     * @param v 需要获取资源id的ImageView
     */
    public int getSrcResourceId(ImageView v) {
        int resourceId = 0;
        try {
            Field mResourceField = ImageView.class.getDeclaredField("mResource");
            mResourceField.setAccessible(true);
            resourceId = (int) mResourceField.get(v);
            if (0 == resourceId) {
                resourceId = getBackgroundResourceId(v);
            }
        } catch (Exception e) {
            MMLogManager.logE(e.toString());
        }
        return resourceId;
    }


    /**
     * 通过反射设置src id
     *
     * @param v   imageView
     * @param res 图片资源id
     */
    public void setSrcResourceId(ImageView v, int res) {
        try {
            Field mResourceField = ImageView.class.getDeclaredField("mResource");
            mResourceField.setAccessible(true);
            mResourceField.set(v, res);
        } catch (Exception e) {
            logE(e.toString());
        }
    }

    /**
     * 从View获取图片资源id 背景
     *
     * @param v 需要获取资源id的View
     */
    public int getBackgroundResourceId(View v) {
        int resourceId = 0;
        try {
            Field mBackgroundResourceField = View.class.getDeclaredField("mBackgroundResource");
            mBackgroundResourceField.setAccessible(true);
            resourceId = (int) mBackgroundResourceField.get(v);
        } catch (Exception e) {
            logE(e.toString());
        }
        return resourceId;
    }

    /**
     * 反射设置View background id
     *
     * @param v   View
     * @param res 图片资源id
     */
    public void setBackgroundResourceId(View v, int res) {
        try {
            Field mBackgroundResourceField = View.class.getDeclaredField("mBackgroundResource");
            mBackgroundResourceField.setAccessible(true);
            mBackgroundResourceField.set(v, res);
        } catch (Exception e) {
            logE(e.toString());
        }
    }

    /**
     * 通过名字获取drawable id
     *
     * @param context   上下文
     * @param imageName 图片名称 不含后缀
     */
    public int getDrawableId(Context context, String imageName) {
        return MMUtils.getResIdByName(context, imageName);
    }

    /**
     * 通过图片名获取当前主题拼接后的图片名称
     *
     * @param orgName 原始图片名称
     */
    public String getCurrentSuffixName(String orgName) {
        CcbGeneralIconSuffix suffix = CcbGeneralIconSuffix.getCurrentSuffix();

        if (!TextUtils.isEmpty(suffix.getSuffix()) && orgName.endsWith(suffix.getSuffix()))
            return orgName;
        for (CcbGeneralIconSuffix iconSuffix : CcbGeneralIconSuffix.values()) {
            if (!orgName.endsWith(iconSuffix.getSuffix()) || TextUtils.isEmpty(iconSuffix.getSuffix()))
                continue;
            int index = orgName.indexOf(iconSuffix.getSuffix());
            if (-1 == index)
                continue;
            orgName = orgName.substring(0, index);
        }

        return String.format("%s" + orgName + "%s", suffix.getPath(), suffix.getSuffix());
    }


    /**
     * 获取当前主题对应图片的id
     *
     * @param v       需要设置图标的view
     * @param orgName 原图名称 不包含后缀
     * @return 如果有返回资源id 没有返回0
     */
    public int getSuffixDrawableId(View v, String orgName) {

        //获取图片资源id
        int imageId = getBackgroundResourceId(v);
        //有资源id获取图片名字
        if (0 != imageId) {
            orgName = v.getResources().getResourceEntryName(imageId);
        }
        if (TextUtils.isEmpty(orgName)) {
            return 0;
        }
        //根据当前主题拼接对应的图片名称
        orgName = getCurrentSuffixName(orgName);
        //获取拼接后的图片资源id
        return getDrawableId(v.getContext(), orgName);
    }

    /**
     * 获取当前主题对应图片的id
     *
     * @param v       需要设置图标的view
     * @param orgName 原图名称 不包含后缀
     * @return 如果有返回资源id 没有返回0
     */
    public int getSuffixDrawableSrcId(ImageView v, String orgName) {

        //获取图片资源id
        int imageId = getSrcResourceId(v);
        //有资源id获取图片名字
        if (0 != imageId) {
            orgName = v.getResources().getResourceEntryName(imageId);
        }
        if (TextUtils.isEmpty(orgName)) {
            return 0;
        }
        //根据当前主题拼接对应的图片名称
        orgName = getCurrentSuffixName(orgName);
        //获取拼接后的图片资源id
        return getDrawableId(v.getContext(), orgName);
    }


    /**
     * 获取当前主题对应图片
     *
     * @param v       需要设置图标的view
     * @param orgName 原图名称 不包含后缀
     * @return 如果有返回图片 没有返回null
     */
    public Drawable getSuffixDrawable(View v, String orgName) {
        int resId = getSuffixDrawableId(v, orgName);
        if (0 != resId) {
            return new WeakReference<Drawable>(v.getResources().getDrawable(resId)).get();
        } else {
            if (TextUtils.isEmpty(orgName)) {
                //获取图片资源id
                int imageId = getBackgroundResourceId(v);
                //有资源id获取图片名字
                if (0 != imageId) {
                    orgName = v.getResources().getResourceEntryName(imageId);
                }
                if (TextUtils.isEmpty(orgName))
                    return null;
            }
            //根据当前主题拼接对应的图片名称
            orgName = getCurrentSuffixName(orgName);
            Bitmap bitmap = MMUtils.readBitmapWithLocalPath(v.getContext(), orgName + ".png");
            return null == bitmap ? null : new WeakReference<Drawable>(new BitmapDrawable(v.getResources(), MMUtils.readBitmapWithLocalPath(v.getContext(), orgName + ".png"))).get();
        }
    }
    /**
     * 获取imageview当前主题对应src图片
     *
     * @param v       需要设置图标的view
     * @param orgName 原图名称 不包含后缀
     * @return 如果有返回图片 没有返回null
     */
    public Drawable getSuffixSrcDrawable(ImageView v, String orgName) {
        int resId = getSuffixDrawableSrcId(v, orgName);
        if (0 != resId) {
            return new WeakReference<Drawable>(v.getResources().getDrawable(resId)).get();
        } else {
            if (TextUtils.isEmpty(orgName)) {
                //获取图片资源id
                int imageId =  getSrcResourceId( v);
                //有资源id获取图片名字
                if (0 != imageId) {
                    orgName = v.getResources().getResourceEntryName(imageId);
                }
                if (TextUtils.isEmpty(orgName))
                    return null;
            }
            //根据当前主题拼接对应的图片名称
            orgName = getCurrentSuffixName(orgName);
            Bitmap bitmap = MMUtils.readBitmapWithLocalPath(v.getContext(), orgName + ".png");
            return null == bitmap ? null : new WeakReference<Drawable>(new BitmapDrawable(v.getResources(), MMUtils.readBitmapWithLocalPath(v.getContext(), orgName + ".png"))).get();
        }
    }
    private static final String GENERAL_CHANGE_TEXT_SIZE_RATIO_KEY = "general_change_text_size_ratio";
    /**
     * 字体大小比例
     */
    private float mTextSizeRatio = 1;
    /**
     * 字体缩放比例范围 1 ± 0.5f
     */
    private float ratioLimits = 0.5f;

    /**
     * 改变字体大小
     */
    public void changeTextSize(Activity act) {
        View rootView = act.getWindow().getDecorView();
//        changeTextSize(rootView);
        changeCcbTextSize(rootView);
    }

    /**
     * 遍历所有需要换字体的view并改变大小
     */
    public void changeTextSize(View rootView) {
        iCcbTextSizes.clear();
        findChangeTextSizeView(rootView);
        for (View v : iCcbTextSizes) {
            changeViewTextSize(v);
        }
    }


    public boolean isBlueColor(){
        return mSkinColor == Color.parseColor("#09b6f2");
    }

    /**
     * 是否是深蓝色主题
     * @return
     */
    public boolean isLightBlueColor(){

        return mSkinColor == Color.parseColor("#0066B3");
    }

    /**
     * 获取所有textview
     */
    private void findChangeTextSizeView(View v) {
        if (v instanceof TextView) {
            iCcbTextSizes.add(v);
        }
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            for (int i = 0, count = group.getChildCount(); i < count; i++) {
                View child = group.getChildAt(i);
                findChangeTextSizeView(child);
            }
        }
    }

    /**
     * 改变view字体大小
     */
    public void changeViewTextSize(View v) {
        if (v instanceof TextView) {
            TextView textView = (TextView) v;
            float textSize = textView.getTextSize();
            Object obj = textView.getTag(R.id.change_text_size_key);
            float currentSize = null == obj ? 1f : (float) obj;
            if (currentSize == mTextSizeRatio)
                return;
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize / currentSize * mTextSizeRatio);
            if (v instanceof CcbEditText) {
                CcbEditText ccbEditText = (CcbEditText) v;
                float hintSize = ccbEditText.getHintSize();
                ccbEditText.setHintSize(hintSize / currentSize * mTextSizeRatio);
            }
            textView.setTag(R.id.change_text_size_key, mTextSizeRatio);
        }

    }

    /**
     * 换字体控件集合
     */
    private List<View> iCcbTextSizes = Collections.synchronizedList(new LinkedList<View>());

    public float getTextSizeRatio() {
        return mTextSizeRatio;
    }

    public void setTextSizeRatio(float v) {
        this.mTextSizeRatio = v;
    }

    /**
     * 保存字体大小比例
     */
    public void saveThemeTextSizeRatio(float textSizeRatio) {
        MMEditor mbsEditor = sp.edit();
        mbsEditor.putFloat(GENERAL_CHANGE_TEXT_SIZE_RATIO_KEY, textSizeRatio);
        mbsEditor.commit();
    }

    /**
     * 暂时设置为最小为正常字体的0.5倍，最大为1.5倍
     */
    public void setTextSizeProgressChanged(View viewGroup, int progress, SeekBar seekBar) {
        float diff = (((float) progress) / (float) seekBar.getMax()) - ratioLimits;
        setTextSizeRatio(1 + diff);
        changeTextSize(viewGroup);
    }

    public int getTextSizeProgress(SeekBar seekBar) {
        return (int) ((mTextSizeRatio - ratioLimits) * seekBar.getMax());
    }

    /**
     * 根据比例设置seekbar进度
     */
    public void setTextSizeSeekBarProgress(SeekBar seekBar) {
        seekBar.setProgress((int) ((mTextSizeRatio - ratioLimits) * seekBar.getMax()));
    }

    /**
     * 换字体控件集合
     */
    private List<CcbTextView> ccbTextSizeList = Collections.synchronizedList(new LinkedList<CcbTextView>());

    public void changeCcbTextSize(View rootView) {
        ccbTextSizeList.clear();
        findCcbTextView(rootView);
        for (CcbTextView v : ccbTextSizeList) {
            v.onTextSizeChange();
        }
    }

    private void findCcbTextView(View v) {
        if (v instanceof CcbTextView) {
            ccbTextSizeList.add((CcbTextView) v);
        }
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            for (int i = 0, count = group.getChildCount(); i < count; i++) {
                View child = group.getChildAt(i);
                findCcbTextView(child);
            }
        }
    }
}
