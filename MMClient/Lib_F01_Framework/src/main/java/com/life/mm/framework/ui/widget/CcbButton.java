package com.life.mm.framework.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.life.mm.common.log.MMLogManager;
import com.life.mm.framework.R;
import com.life.mm.framework.skin.CcbSkinColorTool;
import com.life.mm.framework.skin.ICcbGeneralIcon;
import com.life.mm.framework.skin.ICcbGeneralSkin;
import com.life.mm.framework.ui.view.MMOnClickListener;
import com.life.mm.framework.ui.view.mmOnItemClickListener;

import java.lang.reflect.Field;

import static com.life.mm.common.log.MMLogManager.logE;


/**
 * 按钮
 *
 * @author Genty
 */
public class CcbButton extends AppCompatButton implements ICcbGeneralSkin, ICcbGeneralIcon {

	private static final String TAG = CcbButton.class.getSimpleName();
	
	private boolean isGeneralSkin = false;
	private boolean isGeneralIcon = false;
	private String mGeneralIconName = "";
	private int[] drawablesResId = new int[4];

	public CcbButton(Context context) {
		this(context, null);
	}

	public CcbButton(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.borderlessButtonStyle);
	}

	public CcbButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initAttrs(context, attrs);
		onSkinChange();

	}

	private void initAttrs(Context context, AttributeSet attrs) {

		setGravity(Gravity.CENTER);
		if (null == attrs)
			return;
		try {
			Class androidStyleableClass = Class.forName("com.android.internal.R$styleable");
			Field androidTextViewField = androidStyleableClass.getDeclaredField("TextView");
			androidTextViewField.setAccessible(true);
			int[] androidTextViewAttrs = (int[]) androidTextViewField.get(null);
			TypedArray androidTypedArray = context.obtainStyledAttributes(attrs, androidTextViewAttrs);
			String[] drawableFieldNames = new String[]{"TextView_drawableLeft", "TextView_drawableTop", "TextView_drawableRight", "TextView_drawableBottom"};
			for (int i = 0, count = drawableFieldNames.length; i < count; i++) {
				Field field = androidStyleableClass.getDeclaredField(drawableFieldNames[i]);
				field.setAccessible(true);
				drawablesResId[i] = androidTypedArray.getResourceId((Integer) field.get(null), 0);
			}
			//获取背景id
			Field androidViewField = androidStyleableClass.getDeclaredField("View");
			androidViewField.setAccessible(true);
			int[] androidViewAttrs = (int[]) androidViewField.get(null);
			TypedArray androidTypedArray_View = context.obtainStyledAttributes(attrs, androidViewAttrs);
			Field backgroundField = androidStyleableClass.getDeclaredField("View_background");
			backgroundField.setAccessible(true);
			int backgroundId = androidTypedArray_View.getResourceId((Integer) backgroundField.get(null), 0);
			if (0 != backgroundId) {
				CcbSkinColorTool.getInstance().setBackgroundResourceId(this, backgroundId);
			}
			androidTypedArray_View.recycle();
			androidTypedArray.recycle();
		} catch (Exception e) {
			MMLogManager.logE(e.toString());
		}
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Ccb_Custom);
		isGeneralSkin = a.getBoolean(R.styleable.Ccb_Custom_generalSkin, false);
		isGeneralIcon = a.getBoolean(R.styleable.Ccb_Custom_generalIcon, false);
		mGeneralIconName = a.getString(R.styleable.Ccb_Custom_generalIconName);
		onIconChange();
		a.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	public void setGeneralSkin(boolean generalSkin) {
		if (isGeneralSkin == generalSkin) {
			return;
		}
		if (isGeneralSkin && !generalSkin) {
			// 原来换肤，后面又不想换肤
			clearSkinChange();
			isGeneralSkin = generalSkin;
			return;
		}
		isGeneralSkin = generalSkin;
		if (isGeneralSkin) {
			onSkinChange();
		}
		onIconChange();
	}

	private OnClickListener mOnClickListener;

	@Override
	public void setOnClickListener(OnClickListener l) {

		setOnClickListener(l, -1, true);
	}

	public void setOnClickListener(OnClickListener l, long controlClickTime, boolean collect) {
		if (l instanceof mmOnItemClickListener) {
			super.setOnClickListener(l);
			return;
		}
		// 添加默认公共监听
		this.mOnClickListener = l;
		super.setOnClickListener(getDefaultCcbOnClickListener(controlClickTime, collect));

	}

	private MMOnClickListener getDefaultCcbOnClickListener(long controlClickTime, boolean collect) {

		return -1 == controlClickTime ? new MMOnClickListener() {
			@Override
			public void mmOnClick(View view) {

				if (null == mOnClickListener)
					return;
				mOnClickListener.onClick(view);
			}
		} : new MMOnClickListener(controlClickTime) {
			@Override
			public void mmOnClick(View view) {

				if (null == mOnClickListener)
					return;
				mOnClickListener.onClick(view);
			}
		};
	}

	@Override
	public void onSkinChange() {
		if (isInEditMode()) {
			return;
		}
		if (!isGeneralSkin)
			return;
		// 获取通用点击状态
		ColorStateList colorStateList = CcbSkinColorTool.getInstance().getDefaultColorStateList();
		// 换肤
		setSupportBackgroundTintList(colorStateList);
		// 换字体颜色
		setTextColor(colorStateList);
	}

	@Override
	public void setTextColor(int color) {
		if (!isGeneralSkin()) {
			super.setTextColor(color);
		} else {
			ColorStateList colorStateList = CcbSkinColorTool.getInstance().getDefaultColorStateList();
			super.setTextColor(colorStateList);
		}
	}

	@Override
	public void setTextColor(ColorStateList colors) {
		if (!isGeneralSkin()) {
			super.setTextColor(colors);
		} else {
			if (isInEditMode()) {
				return;
			}
			ColorStateList colorStateList = CcbSkinColorTool.getInstance().getDefaultColorStateList();
			super.setTextColor(colorStateList);
		}
	}

	private void clearSkinChange() {
		setSupportBackgroundTintToNullInSupportLib22();
		setSupportBackgroundTintToNullInSupportLib24();
	}

	private void setSupportBackgroundTintToNullInSupportLib22() {
		try {
			Field mBackgroundTint = AppCompatButton.class.getDeclaredField("mBackgroundTint");
			mBackgroundTint.setAccessible(true);
			mBackgroundTint.set(this, null);
		} catch (NoSuchFieldException e) {
			logE(e.getMessage());
		} catch (IllegalAccessException e) {
			logE(e.getMessage());
		}
	}

	private void setSupportBackgroundTintToNullInSupportLib24() {
		try {
			Field mBackgroundTintHelper = AppCompatButton.class.getDeclaredField("mBackgroundTintHelper");
			mBackgroundTintHelper.setAccessible(true);
			Object objectHelper = mBackgroundTintHelper.get(this);// object AppCompatBackgroundHelper

			Class<?> helperClazz = Class.forName("android.support.v7.widget.AppCompatBackgroundHelper");
			Field mBackgroundTint = helperClazz.getDeclaredField("mBackgroundTint");
			mBackgroundTint.setAccessible(true);
			mBackgroundTint.set(objectHelper, null);

		} catch (NoSuchFieldException e) {
			logE(e.getMessage());
		} catch (IllegalAccessException e) {
			logE(e.getMessage());
		} catch (IllegalArgumentException e) {
			logE(e.getMessage());
		} catch (ClassNotFoundException e) {
			logE(e.getMessage());
		}
	}

	public void setGeneralIconName(String mGeneralIconName) {
		this.mGeneralIconName = mGeneralIconName;
		onIconChange();
	}

	@Override
	public void setBackgroundDrawable(final Drawable background) {
		if (!isGeneralIcon && TextUtils.isEmpty(mGeneralIconName)) {
			super.setBackgroundDrawable(background);
		} else {
			post(new Runnable() {
				@Override
				public void run() {
					setGeneralBackground(background);
				}
			});
		}

	}

	@Override
	public boolean isGeneralSkin() {
		return isGeneralSkin;
	}

	@Override
	public void onIconChange() {
		if (!isGeneralIcon) return;
		setGeneralBackground(getBackground());
		setCompoundDrawablesWithIntrinsicBounds(drawablesResId[0], drawablesResId[1], drawablesResId[2], drawablesResId[3]);

	}

	/**
	 * 设置background
	 */
	private void setGeneralBackground(Drawable background) {
		Drawable suffixDrawable = CcbSkinColorTool.getInstance().getSuffixDrawable(this, mGeneralIconName);
		int res = CcbSkinColorTool.getInstance().getSuffixDrawableId(this, mGeneralIconName);
		if (null == suffixDrawable) {
			super.setBackgroundDrawable(background);
		} else {
			super.setBackgroundDrawable(suffixDrawable);
		}
		if (0 != res) {
			CcbSkinColorTool.getInstance().setBackgroundResourceId(this, res);
		}
	}

	@Override
	public boolean isGeneralIcon() {
		return isGeneralIcon;
	}

	@Override
	public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
		if (!isGeneralIcon) {
			super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		} else {
			Drawable leftDrawable = (0 == drawablesResId[0]) ? getCompoundDrawables()[0] : CcbSkinColorTool.getInstance().getSuffixDrawable(this, getResources().getResourceEntryName(drawablesResId[0]));
			leftDrawable = null == leftDrawable ? getCompoundDrawables()[0] : leftDrawable;

			Drawable topDrawable = (0 == drawablesResId[1]) ? getCompoundDrawables()[1] : CcbSkinColorTool.getInstance().getSuffixDrawable(this, getResources().getResourceEntryName(drawablesResId[1]));
			topDrawable = null == topDrawable ? getCompoundDrawables()[1] : topDrawable;

			Drawable rightDrawable = (0 == drawablesResId[2]) ? getCompoundDrawables()[2] : CcbSkinColorTool.getInstance().getSuffixDrawable(this, getResources().getResourceEntryName(drawablesResId[2]));
			rightDrawable = null == rightDrawable ? getCompoundDrawables()[2] : rightDrawable;

			Drawable bottomDrawable = (0 == drawablesResId[3]) ? getCompoundDrawables()[3] : CcbSkinColorTool.getInstance().getSuffixDrawable(this, getResources().getResourceEntryName(drawablesResId[3]));
			bottomDrawable = null == bottomDrawable ? getCompoundDrawables()[3] : bottomDrawable;

			super.setCompoundDrawablesRelativeWithIntrinsicBounds(leftDrawable, topDrawable, rightDrawable, bottomDrawable);
		}
	}
}
