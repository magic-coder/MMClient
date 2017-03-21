package com.life.mm.framework.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.life.mm.common.log.MMLogManager;
import com.life.mm.framework.R;
import com.life.mm.framework.skin.CcbSkinColorTool;
import com.life.mm.framework.skin.ICcbGeneralIcon;
import com.life.mm.framework.skin.ICcbGeneralSkin;
import com.life.mm.framework.ui.view.MMOnClickListener;

import java.lang.reflect.Field;

import static com.life.mm.common.log.MMLogManager.logE;

/**
 * 文本框
 *
 * @author Genty
 */
public class CcbTextView extends TextView implements ICcbGeneralSkin,ICcbGeneralIcon {

	private boolean isGeneralSkin = false;

	private boolean  isGeneralIcon = false;

	private String mGeneralIconName = "";

	private int [] drawablesResId = new int[4];

	/**
	 * 是否需要右对齐
	 */
	private boolean alignRight = false;

	/**
	 * 是否需要左对齐
	 */
	private boolean alignLeft = false;
	/**
	 * 是否根据主题切换字体大小
//	 */
	private boolean isChangeTextSize;
	//主题切换字体标记
	private boolean fromChangeTextSize;
	private boolean fromChangeColor;
	//其他主题字体大小和颜色
	private  final  int  DEFAULT_TEXTSIZE=  (int) getResources().getDimension(R.dimen.x38);
	private final int DEFAULT_COLOR= Color.BLACK;
	public CcbTextView(Context context) {
		super(context);
		initAttrs(context, null);
	}

	public CcbTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttrs(context, attrs);
		onSkinChange();
	}

	public CcbTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initAttrs(context, attrs);
		onSkinChange();
	}

	public boolean isAlignRight() {
		return alignRight;
	}

	public void setAlignRight(boolean alignRight) {
		this.alignRight = alignRight;
	}

	public boolean isAlignLeft() {
		return alignLeft;
	}

	public void setAlignLeft(boolean alignLeft) {
		this.alignLeft = alignLeft;
	}

	/**
	 * 初始化节点属性
	 *
	 * @param context
	 *            上下文
	 * @param attrs
	 *            节点属性
	 */
	private void initAttrs(Context context, AttributeSet attrs) {
		if (null == attrs)
			return;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Ccb_Custom);

		try {
			Class androidStyleableClass =  Class.forName("com.android.internal.R$styleable");
			Field androidTextViewField = androidStyleableClass.getDeclaredField("TextView");
			androidTextViewField.setAccessible(true);
			int [] androidTextViewAttrs = (int[]) androidTextViewField.get(null);
			TypedArray androidTypedArray = context.obtainStyledAttributes(attrs,androidTextViewAttrs);
			String[] drawableFieldNames = new String[]{"TextView_drawableLeft","TextView_drawableTop","TextView_drawableRight","TextView_drawableBottom"};
			for (int i = 0, count= drawableFieldNames.length;i<count;i++){
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
			logE(e.toString());
		}
		alignRight = a.getBoolean(R.styleable.Ccb_Custom_alignRight, false);
		alignLeft = a.getBoolean(R.styleable.Ccb_Custom_alignLeft, false);
		isGeneralSkin = a.getBoolean(R.styleable.Ccb_Custom_generalSkin, false);
		isGeneralIcon = a.getBoolean(R.styleable.Ccb_Custom_generalIcon,false);
		isChangeTextSize = a.getBoolean(R.styleable.Ccb_Custom_generalTextSizeChange,false);
		mGeneralIconName = a.getString(R.styleable.Ccb_Custom_generalIconName);
		onIconChange();
		setTag(R.id.bule_change_text_color_key,getTextColors());
		setTag(R.id.bule_change_text_size_key,getTextSize());
		onTextSizeChange();
		a.recycle();
	}

	private Shader mShader;

	public void setShader(Shader mShader) {
		this.mShader = mShader;
		invalidate();
	}

	@Override
	public void setTextSize(int unit, float size) {
		super.setTextSize(unit, size);
		if(!fromChangeTextSize){
			setTag(R.id.bule_change_text_size_key,getTextSize());
		}else{
			fromChangeTextSize=false;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(null==mShader)
			return;
		Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setShader(mShader);
		Rect rect = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
		canvas.drawRect(rect, mPaint);
	}

	public void setGeneralSkin(boolean generalSkin) {
		isGeneralSkin = generalSkin;
		if (isGeneralSkin) {
			onSkinChange();
		}
	}



	@Override
	public void onSkinChange() {
		if (isInEditMode()) {
			return;
		}
		if (!isGeneralSkin)
			return;
		setTextColor(CcbSkinColorTool.getInstance().getDefaultColorStateList());
	}

	@Override
	public boolean isGeneralSkin() {
		return isGeneralSkin;
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
	public void setGeneralIcon(boolean generalIcon) {
		isGeneralIcon = generalIcon;
		onIconChange();
	}

	public void setGeneralIconName(String mGeneralIconName) {
		this.mGeneralIconName = mGeneralIconName;
	}
	@Override
	public void onIconChange() {
		if(!isGeneralIcon)
			return;
			setCompoundDrawablesWithIntrinsicBounds(drawablesResId[0],drawablesResId[1],drawablesResId[2],drawablesResId[3]);
	     	setGeneralBackground(getBackground());
	}

	@Override
	public boolean isGeneralIcon() {
		return isGeneralIcon;
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
	/**
	 * 设置background
	 * @param background
	 */
	private void setGeneralBackground(Drawable background){
		Drawable suffixDrawable = CcbSkinColorTool.getInstance().getSuffixDrawable(this,mGeneralIconName);
		int res = CcbSkinColorTool.getInstance().getSuffixDrawableId(this,mGeneralIconName);
		if(null == suffixDrawable) {
			super.setBackgroundDrawable(background);
		}else {
			super.setBackgroundDrawable(suffixDrawable);
		}
		if(0 != res){
			CcbSkinColorTool.getInstance().setBackgroundResourceId(this,res);
		}
	}

	private OnClickListener mOnClickListener;

	@Override
	public void setOnClickListener(OnClickListener l) {

		setOnClickListener(l, -1, true);
	}

	public void setOnClickListener(OnClickListener l, long controlClickTime, boolean collect) {
		if (l instanceof MMOnClickListener) {
			super.setOnClickListener(l);
			return;
		}
		// 添加默认公共监听
		this.mOnClickListener = l;
		super.setOnClickListener(getDefaultmmOnClickListener(controlClickTime, collect));

	}

	private MMOnClickListener getDefaultmmOnClickListener(long controlClickTime, boolean collect) {

		return -1 == controlClickTime ? new MMOnClickListener() {
			@Override
			public void mmOnClick(View view) {

				if (null == mOnClickListener)
					return;
				mOnClickListener.onClick(view);
			}
		} : new MMOnClickListener(controlClickTime, collect) {
			@Override
			public void mmOnClick(View view) {

				if (null == mOnClickListener)
					return;
				mOnClickListener.onClick(view);
			}
		};
	}

	public void isChangeTextSize(boolean changeTextSize){
		this.isChangeTextSize=changeTextSize;
		onTextSizeChange();
	}

	public void onTextSizeChange() {
		if (!isChangeTextSize) {
			return;
		}
		if (CcbSkinColorTool.getInstance().getSkinColor() == Color.parseColor("#09b6f2")) {
			//使用浅蓝主题字体大小
			setTextSize(TypedValue.COMPLEX_UNIT_PX, (Float) getTag(R.id.bule_change_text_size_key));
			setTextColor((ColorStateList) getTag(R.id.bule_change_text_color_key));
			MMLogManager.logD("CcbTextView" + "浅蓝===字体大小" + getTag(R.id.bule_change_text_size_key));
		} else {
			//使用默认字体大小
			fromChangeColor = true;
			fromChangeTextSize = true;
			setTextSize(TypedValue.COMPLEX_UNIT_PX, DEFAULT_TEXTSIZE);
			ColorStateList colorStateList = ColorStateList.valueOf(DEFAULT_COLOR);
			setTextColor(colorStateList);
			MMLogManager.logD("CcbTextView" + "非浅蓝===字体大小" + DEFAULT_TEXTSIZE);

		}
	}

	@Override
	public void setTextColor(int color) {
		super.setTextColor(color);
		if(!fromChangeColor){
			ColorStateList colorStateList = ColorStateList.valueOf(color);
			setTag(R.id.bule_change_text_color_key,colorStateList);
		}else{
			fromChangeColor=false;
		}
	}
	@Override
	public void setTextColor(ColorStateList colors) {
		super.setTextColor(colors);
		if(!fromChangeColor){
			setTag(R.id.bule_change_text_color_key,colors);
		}else{
			fromChangeColor=false;
		}
	}
}
