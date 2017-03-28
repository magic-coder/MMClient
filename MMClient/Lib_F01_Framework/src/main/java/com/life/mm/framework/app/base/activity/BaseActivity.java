package com.life.mm.framework.app.base.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.life.mm.common.log.MMLogManager;
import com.life.mm.common.utils.MeasureUtil;
import com.life.mm.common.utils.ViewUtil;
import com.life.mm.framework.R;
import com.life.mm.framework.app.base.presenter.BasePresenter;
import com.life.mm.framework.app.base.view.BaseView;
import com.life.mm.framework.ui.loading.LoadingDialogUtil;
import com.life.mm.framework.ui.widget.CustomSlidingPaneLayout;
import com.life.mm.framework.utils.TUtil;
import com.life.mm.framework.utils.ThemeUtil;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Created by Thinkpad on 2017/2/27.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView{

    protected String TAG = getClass().getSimpleName();
    private ViewGroup mDecoreView = null;
    protected Context mContext = null;
    protected Resources mResources = null;
    protected LayoutInflater mLayoutInflater = null;


    protected P mPresenter = null;


    protected CustomSlidingPaneLayout slidingPaneLayout = null;

    private int mToolbarTitle;
    private int mMenuDefaultCheckedItem;
    private int mToolbarIndicator;

    protected Toolbar toolbar = null;
    private View main_menu_mine;

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initPresenter();

    protected boolean hasLeftMenu() {
        return false;
    }

    /**
     * 是否使用有首页侧滑的布局
     * @return  true 使用有侧滑的布局， false 不使用有侧滑的布局
     */
    protected boolean shouldUseBaseLayout() {
        return true;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MMLogManager.logD(TAG + ", onCreate");
        this.mContext = this;
        this.mResources = mContext.getResources();
        this.mLayoutInflater = LayoutInflater.from(this);
        this.mDecoreView = (ViewGroup) getWindow().getDecorView();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenter = TUtil.getT(this, 0);
        initView();
        initPresenter();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        MMLogManager.logD(TAG + ", onAttachedToWindow");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        MMLogManager.logD(TAG + ", setContentView");
        setContentView(mLayoutInflater.inflate(layoutResID, mDecoreView, false));
    }

    protected void handleBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        handleBack();
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, null);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (!shouldUseBaseLayout()) {//不使用有侧滑的布局
            super.setContentView(view, params);
            return;
        }
        View contentRootView = mLayoutInflater.inflate(R.layout.activity_base, (ViewGroup) getWindow().getDecorView(), false);
        ViewGroup contentContainer = (ViewGroup) contentRootView.findViewById(R.id.app_content);
        slidingPaneLayout = (CustomSlidingPaneLayout) contentRootView.findViewById(R.id.app_slidingpane_layout);

        initToolBar(contentRootView);

        if (hasLeftMenu()) {
            handleStatusView();
            initSlidePanel();
            initLeftMenu(contentRootView);
        } else {
            setDrawerCanSlide(false, false);
        }


        contentContainer.removeAllViews();
        contentContainer.addView(view);
        params = params != null ? params : new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        super.setContentView(contentRootView, params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MMLogManager.logD(TAG + ", onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MMLogManager.logD(TAG + ", onDestroy");
        ButterKnife.unbind(this);
        if (null != mPresenter) {
            mPresenter.detach();
        }
    }


    @Override
    public void onBegin() {
        LoadingDialogUtil.showLoading();
    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void onFinish() {
        LoadingDialogUtil.dismiss();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(int status, String message) {
        LoadingDialogUtil.showErrorDlg(status, message);
    }

    protected void initToolBar(View contentView) {
        toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            // 24.0.0版本后导航图标会有默认的与标题的距离，这里设置去掉
            toolbar.setContentInsetStartWithNavigation(0);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            if (mToolbarTitle != -1) {
                setToolbarTitle(mToolbarTitle);
            }
            if (mToolbarIndicator != -1) {
                setToolbarIndicator(mToolbarIndicator);
            } else {
                setToolbarIndicator(R.drawable.ic_menu_back);
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if (BaseActivity.this.getClass().getSimpleName().equals("MMMainActivity")) {
                         if (null != slidingPaneLayout && slidingPaneLayout.isOpen() && slidingPaneLayout.isSlideable()) {
                             slidingPaneLayout.closePane();
                             return ;
                         } else if (null != slidingPaneLayout && !slidingPaneLayout.isOpen() && slidingPaneLayout.isSlideable()) {
                             slidingPaneLayout.openPane();
                             return;
                         }
                     }

                    handleBack();
                }
            });
        }
    }

    /**
     * 处理布局延伸到状态栏，对4.4以上系统有效
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void handleStatusView() {
        // 针对4.4和SettingsActivity(因为要做换肤，而状态栏在5.0是设置为透明的，若不这样处理换肤时状态栏颜色不会变化)
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {

            // 生成一个状态栏大小的矩形
            View statusBarView = ViewUtil.createStatusView(this, ThemeUtil.getColor(this, R.attr.colorPrimary));
            // 添加 statusBarView 到布局中
            ViewGroup contentLayout = (ViewGroup) slidingPaneLayout.getChildAt(0);
            contentLayout.addView(statusBarView, 0);
            // 内容布局不是 LinearLayout 时,设置margin或者padding top
            final View view = contentLayout.getChildAt(1);
            final int statusBarHeight = MeasureUtil.getStatusBarHeight(this);
            if (!(contentLayout instanceof LinearLayout) && view != null) {
                view.setPadding(0, statusBarHeight, 0, 0);
            }
            // 设置属性
            ViewGroup drawer = (ViewGroup) slidingPaneLayout.getChildAt(1);
            slidingPaneLayout.setFitsSystemWindows(false);
            contentLayout.setFitsSystemWindows(false);
            contentLayout.setClipToPadding(true);
            drawer.setFitsSystemWindows(false);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            // 5.0以上跟4.4统一，状态栏颜色和Toolbar的一致
            //slidingPaneLayout.setBackgroundColor(ThemeUtil.getColor(this, R.attr.colorPrimary));
        }
    }

    private void initSlidePanel() {

        // 通过反射改变mOverhangSize的值为0，
        // 这个mOverhangSize值为菜单到右边屏幕的最短距离，
        // 默认是32dp，现在给它改成0
        try {
            Field overhangSize = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
            overhangSize.setAccessible(true);
            overhangSize.set(slidingPaneLayout, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                onLeftMenuSlide(panel, slideOffset);
            }

            @Override
            public void onPanelOpened(View panel) {
                onLeftMenuOpened(panel);
            }

            @Override
            public void onPanelClosed(View panel) {
                onLeftMenuClosed(panel);
            }
        });
        slidingPaneLayout.setCoveredFadeColor(Color.TRANSPARENT);
        slidingPaneLayout.setSliderFadeColor(Color.TRANSPARENT);
    }

    protected void initLeftMenu(View contentRootView) {
    }


    /**
     * 设置侧边栏是否可滑动
     * @param canSlide   是否可以滑动
     * @param isFromHome 是否在主activity调用
     */
    protected void setDrawerCanSlide(boolean canSlide, boolean isFromHome) {
        slidingPaneLayout.setCanSlide(canSlide);
        View menuLayout = slidingPaneLayout.getChildAt(0);
        ViewGroup.LayoutParams params = menuLayout.getLayoutParams();
        params.width = !canSlide && !isFromHome ? 0 : getResources().getDimensionPixelOffset(R.dimen.x720);
        menuLayout.setLayoutParams(params);
    }

    protected void setToolbarIndicator(int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(resId);
        }
    }

    protected void setToolbarTitle(String str) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(str);
        }
    }

    protected void setToolbarTitle(int strId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(strId);
        }
    }


    protected void onLeftMenuSlide(View panel, float slideOffset) {

    }

    protected void onLeftMenuOpened(View panel) {

    }

    protected void onLeftMenuClosed(View panel) {

    }

    protected String getResourceValue(int resId) {
        return mResources.getString(resId);
    }
}
