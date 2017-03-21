package com.life.mm.framework.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.EditText;

import com.life.mm.framework.R;
import com.life.mm.framework.skin.CcbSkinColorTool;
import com.life.mm.framework.skin.ICcbGeneralIcon;
import com.life.mm.framework.skin.ICcbGeneralSkin;
import com.life.mm.framework.utils.UiTool;

import java.lang.reflect.Field;

import static com.life.mm.common.log.MMLogManager.logE;


/**
 * 输入框
 */
public class CcbEditText extends EditText implements ICcbGeneralSkin,ICcbGeneralIcon {

    /**
     * 是否需要左对齐
     */
    private boolean alignLeft = false;

    private float mHintSize;

    private float mTextSize;
    private Context mContext;
    /**
     * 是否需要字符分割
     */
    private boolean mTextSplit;

    private static final String DEFAULT_CHAR = " ";
    /**
     * 字符分割符号 默认为" "
     */
    private String mTextSplitChar;

    /**
     * 字符分割位数
     */
    private int mTextSplitDigit;

    /**
     * 是否是安全密码
     */
    private boolean isSafePassword = false;
    /**
     * 是否显示删除按钮
     */
    private boolean isShowClearDrawable = true;
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 安全密码替换符号
     */
    private String safePasswordChar;

    public static final String DEFAULT_SAFE_PASSWORD_TEXT = "•";// 安全密码输入框替换符
    private int [] drawablesResId = new int[4];
    private boolean isGeneralIcon=false;
    private String mGeneralIconName="";

    public CcbEditText(Context context) {
        super(context);
        initAttrs(context, null);


    }

    public CcbEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        onSkinChange();
    }

    public CcbEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        onSkinChange();
    }

    public boolean isAlignLeft() {
        return alignLeft;
    }

    public void setAlignLeft(boolean alignLeft) {
        this.alignLeft = alignLeft;
    }

    public float getHintSize() {
        return mHintSize;
    }

    public void setHintSize(float mHintSize) {
        this.mHintSize = mHintSize;
//        requestLayout();
        changeHintSize=true;
        checkTextLen();
    }

    /**
     * 初始化节点属性
     *
     * @param context 上下文
     * @param attrs   节点属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        mContext = context;
        if (null == attrs)
            return;
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
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Ccb_Custom);
        alignLeft = a.getBoolean(R.styleable.Ccb_Custom_alignLeft, false);
        mHintSize = a.getDimension(R.styleable.Ccb_Custom_hintSize, -1);
        isGeneralSkin = a.getBoolean(R.styleable.Ccb_Custom_generalSkin, true);
        mTextSplit = a.getBoolean(R.styleable.Ccb_Custom_textSplit, false);
        mTextSplitChar = a.getString(R.styleable.Ccb_Custom_textSplitChar);
        isSafePassword = a.getBoolean(R.styleable.Ccb_Custom_safePassword, false);
        safePasswordChar = a.getString(R.styleable.Ccb_Custom_safePasswordChar);
        isShowClearDrawable = a.getBoolean(R.styleable.Ccb_Custom_isShowClearDrawable, true);
        isGeneralIcon = a.getBoolean(R.styleable.Ccb_Custom_generalIcon,false);
        mGeneralIconName = a.getString(R.styleable.Ccb_Custom_generalIconName);
        if (TextUtils.isEmpty(safePasswordChar)) {
            safePasswordChar = DEFAULT_SAFE_PASSWORD_TEXT;
        }
        if (TextUtils.isEmpty(mTextSplitChar)) {
            mTextSplitChar = DEFAULT_CHAR;
        }
        mTextSplitDigit = a.getInt(R.styleable.Ccb_Custom_textSplitDigit, 4);
//        com.android.internal.R.styleable.TextView_textSize:
        // mTextSize = a.getDimension(R.styleable.Ccb_Custom_textSize,
        // getResources().getDimension(R.dimen.ccb_txt_size_normal));
        onIconChange();
        a.recycle();
        init(context);
    }

    public boolean isSafePassword() {
        return isSafePassword;
    }

    public void setSafePassword(boolean safePassword) {
        isSafePassword = safePassword;
    }

    public String getSafePasswordChar() {
        return safePasswordChar;
    }

    public void setSafePasswordChar(String safePasswordChar) {
        this.safePasswordChar = safePasswordChar;
    }

    private void init(Context context) {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            if (isShowClearDrawable) {
//                setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight() + (int) context.getResources().getDimension(R.dimen.x43), getPaddingBottom());
                mClearDrawable = getResources().getDrawable(R.drawable.edittext_clearn);
                mClearDrawable.setBounds(0, 0, (int) context.getResources().getDimension(R.dimen.x43),
                        (int) context.getResources().getDimension(R.dimen.x43));
                setClearIconVisible(false);
            }
        } else {
            mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
            setClearIconVisible(!isShowClearDrawable);
        }
    }

    public void isShowClearDrawable(boolean isShowClearDrawable) {
        if (!isShowClearDrawable) {
            setClearIconVisible(false);
        }
        this.isShowClearDrawable = isShowClearDrawable;
        init(mContext);
    }

    /**
     * 设置启用字符分割
     */
    public void setTextSplit(boolean mTextSplit) {
        this.mTextSplit = mTextSplit;
    }

    /**
     * 设置字符分隔符
     */
    public void setTextSplitChar(String mTextSplitChar) {
        this.mTextSplitChar = mTextSplitChar;
    }

    /**
     * 设置分割位数
     */
    public void setTextSplitDigit(int mTextSplitDigit) {
        this.mTextSplitDigit = mTextSplitDigit;
    }


    private boolean isSplit = false;

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        checkTextLen();
        if (isShowClearDrawable) {
            setClearIconVisible(text.length() > 0);
        }
        if (!mTextSplit || isSplit)
            return;

        Editable currentEditable = getText();
        int lastPosition = getSelectionEnd();
        if (!TextUtils.isEmpty(currentEditable.toString()) && currentEditable.toString().endsWith(mTextSplitChar) && lengthAfter < lengthBefore) {
            currentEditable.delete(currentEditable.toString().lastIndexOf(mTextSplitChar), lastPosition);
            return;
        }

        if(TextUtils.isEmpty(text))
            return;
        int splitLength = mTextSplitDigit + 1;
        if (lengthAfter > splitLength) {
            isSplit = true;
            int loopCount = (text.toString().replace(mTextSplitChar, "").length()) / mTextSplitDigit;
            for (int i = 1; i <= loopCount; i++) {
                int insertPosition = i * mTextSplitDigit + (i - 1) * mTextSplitChar.length();
                if (insertPosition >= text.length())
                    break;
                if (mTextSplitChar.equals(String.valueOf(getText().charAt(insertPosition))))
                    continue;
                currentEditable.insert(insertPosition, mTextSplitChar);
            }
            isSplit = false;

        } else {
            int index = (text.toString().replace(mTextSplitChar, "").length()) / mTextSplitDigit;
            int insertPosition = index * mTextSplitDigit + (index - 1) * mTextSplitChar.length();
            if (text.length() - 1 != insertPosition || lengthBefore > lengthAfter)
                return;
            currentEditable.insert(insertPosition, mTextSplitChar);
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            String text = getText().toString();
            if (isShowClearDrawable)
                setClearIconVisible(getText().length() > 0);
            if (TextUtils.isEmpty(text))
                return;
        } else {
            if (isShowClearDrawable)
                setClearIconVisible(false);
        }
    }


    public Editable getHiddenText() {
        Editable hiddenEditable = (Editable) getTag(R.id.tag_hidden_text_flag);
        if ((!UiTool.isPasswordEditText(this) && !isCustomPassword(this)) || null == hiddenEditable) {
            return super.getText();
        } else {
            return hiddenEditable;
        }
    }


    public String getReplaceSpaceText() {
        return mTextSplit ? getText().toString().replace(mTextSplitChar, "") : getText().toString();
    }


    private boolean hasChecked = false;
    private boolean changeHintSize;
    private boolean changeTextSize = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!hasChecked) {
            mTextSize = getTextSize();
            hasChecked = true;
            if (-1 == mHintSize) {
                mHintSize = mTextSize;
            }
            checkTextLen();
        }
//            checkTextLen();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && isShowClearDrawable) {
            if (getCompoundDrawables()[2] != null && isEnabled()) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if(TextUtils.isEmpty(text)){
            setTag(R.id.tag_hidden_text_flag, null);
        }
        super.setText(text,type);
    }

    /**
     * 是不是自定义密码属性
     *
     * @param editText 输入框
     */
    private static boolean isCustomPassword(EditText editText) {
        if (!(editText instanceof CcbEditText))
            return false;
        return ((CcbEditText) editText).isSafePassword();
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     */
    public void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    private void checkTextLen() {
        int len = getText().length();
        if (!hasChecked)
            return;
        //在这里拦截当len == 0，如果此时设置textsize，将会设置成hintsize的问题
        if (len == 0 && changeHintSize) {
            changeTextSize = true;
            setTextSize(TypedValue.COMPLEX_UNIT_PX, mHintSize);
        } else {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            //在此判断当内容长度改变时，切换textsize和hintsize的显示
            if(len == 0){
                changeTextSize = true;
                setTextSize(TypedValue.COMPLEX_UNIT_PX, mHintSize);
            }
        }
    }

    protected boolean isGeneralSkin = true;

    @Override
    public void onSkinChange() {
        if (isInEditMode()) {
            return;
        }
        if (!isGeneralSkin)
            return;
        UiTool.setCursorColorAndVisible(this);
        setHighlightColor(CcbSkinColorTool.getInstance().getSkinColorWithAlpha(0.5f));

    }

    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }

    @Override
    public void setTextSize(int unit, float size) {
        //这里标记settextsize是从哪里设置的，如果是hintsize 这做不保存
        //如果是textsize，则需要保存当前size
        if (changeTextSize) {
            changeTextSize=false;
        } else {
            changeHintSize = false;
            this.mTextSize = size;
        }
        super.setTextSize(unit, size);
    }

    @Override
    public float getTextSize() {
        if (!hasChecked) return super.getTextSize();
        return mTextSize;
    }
    @Override
    public void onIconChange() {
        if(!isGeneralIcon)return;
        setGeneralBackground(getBackground());
        setCompoundDrawablesWithIntrinsicBounds(drawablesResId[0],drawablesResId[1],drawablesResId[2],drawablesResId[3]);

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
}
