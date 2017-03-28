package com.life.mm;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.life.mm.app.MainDynamicFragment;
import com.life.mm.app.MainFriendsFragment;
import com.life.mm.app.MainHomeFragment;
import com.life.mm.app.MainInfoCenterFragment;
import com.life.mm.app.MainMessageFragment;
import com.life.mm.common.log.MMLogManager;
import com.life.mm.framework.app.ActivityHelper;
import com.life.mm.framework.app.MMApplication;
import com.life.mm.framework.app.base.activity.BaseActivity;
import com.life.mm.framework.app.base.adapter.BaseFragmentAdapter;
import com.life.mm.framework.app.base.fragment.BaseFragment;
import com.life.mm.framework.app.login.LoginActivity;
import com.life.mm.framework.libwrapper.glide.GlideCircleTransform;
import com.life.mm.framework.libwrapper.glide.GlideUtils;
import com.life.mm.framework.skin.CcbSkinColorTool;
import com.life.mm.framework.user.CustomUser;
import com.life.mm.framework.utils.MMUtils;
import com.life.mm.infocenter.MineInfoActivity;
import com.mm.life.settings.SettingsActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MMMainActivity extends BaseActivity {



    /**
     * 默认灰色色值
     */
    private static final int TAB_COLOR_GRAY = Color.parseColor("#a2a1a6");
    /**
     * 默认白色色值
     */
    private static final int TAB_COLOR_WHITE = Color.WHITE;
    /**
     * 默认浅蓝色色值
     */
    private static final int TAB_COLOR_ORG = Color.parseColor("#09b6f2");

    private BaseFragment[] fragments = new BaseFragment[] {
            new MainHomeFragment(),     //附近
            new MainMessageFragment(),  //消息
            new MainFriendsFragment(),  //好友
            new MainDynamicFragment(),  //动态
            new MainInfoCenterFragment()//个人中心
    };

    private int[] mTitles = new int[] {
            R.string.main_nearby,
            R.string.main_message,
            R.string.main_friends,
            R.string.main_dynamic,
            R.string.main_info_center
    };

    private int mCurrentIndex = 0;
    private Menu mMenu = null;
    private ViewPager viewPager = null;

    private RadioGroup navigation_bar = null;
    private RadioButton radio_button_nearby = null;         //附近
    private RadioButton radio_button_message = null;        //消息
    private RadioButton radio_button_friends = null;        //好友
    private RadioButton radio_button_dynamic = null;        //动态
    private RadioButton radio_button_info_center = null;    //个人中心

    private ImageView main_menu_cover = null;
    private TextView main_menu_nick = null;
    private TextView main_menu_level = null;

    private TextView main_menu_mine = null;
    private TextView main_menu_settings = null;
    private TextView main_menu_about = null;

    //处理再按一次退出
    private static boolean isDoubleClick = false;
    private static final long DOUBLE_CLICK_DELAY_TIME = 1000;
    private Handler mBackDelayHandler = null;
    private List<OnMenuInflateListener> menuInflateListenerList = new ArrayList<>();

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switchTab(v);
        }
    };

    private View.OnClickListener leftMenuClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            slidingPaneLayout.closePane();
            switch (v.getId()) {
                case R.id.main_menu_cover:
                    goMyInfoCenter();
                    break;

                case R.id.main_menu_mine:
                    goMyInfoCenter();
                    break;

                case R.id.main_menu_settings:
                    ActivityHelper.goActivity(mContext, SettingsActivity.class, null);
                    break;

                case R.id.main_menu_about:
                    break;

                default:
            }
        }
    };

    private void goMyInfoCenter() {
        if (!MMApplication.getInstance().isLogin()) {
            ActivityHelper.goActivity(mContext, LoginActivity.class, null);
        } else {
            ActivityHelper.goActivity(mContext, MineInfoActivity.class, null);
        }
    }

    private void switchTab(View view) {
        //mMenu.clear();
        switch (view.getId()) {
            case R.id.radio_button_nearby:
                mCurrentIndex = 0;
                //getMenuInflater().inflate(R.menu.main_home_menu, mMenu);
                break;
            case R.id.radio_button_message:
                //getMenuInflater().inflate(R.menu.main_message_menu, mMenu);
                mCurrentIndex = 1;
                break;

            case R.id.radio_button_friends:
                mCurrentIndex = 2;
                break;

            case R.id.radio_button_dynamic:
                mCurrentIndex = 3;
                break;

            case R.id.radio_button_info_center:
                mCurrentIndex = 4;
                break;

            default:
                MMLogManager.logD(TAG + ", clickListener, Nothing to do.");
        }
        navigation_bar.check(view.getId());
        viewPager.setCurrentItem(mCurrentIndex, true);
        setToolbarTitle(fragments[mCurrentIndex].getmTitle());
        checkState();
    }

    @Override
    protected boolean hasLeftMenu() {
        return true;
    }


    public Menu getmMenu() {
        return mMenu;
    }

    @Override
    protected void initLeftMenu(View contentRootView) {
        super.initLeftMenu(contentRootView);
        findMenuView(contentRootView);
        setLeftMenuClickListener();
        setMenuView();
    }

    private void setMenuView() {
        CustomUser customUser = MMApplication.getInstance().getCustomUser();
        if (null != customUser) {
            String nickName = customUser.getNickName();
            if (!TextUtils.isEmpty(nickName)) {
                main_menu_nick.setText(MMUtils.getFormatSpan(mContext, R.string.navigation_menu_info_nick, customUser.getNickName()));
            }
        }
    }

    private void findMenuView(View contentRootView) {
        this.main_menu_cover = (ImageView) contentRootView.findViewById(R.id.main_menu_cover);
        this.main_menu_nick = (TextView) contentRootView.findViewById(R.id.main_menu_nick);
        this.main_menu_level = (TextView) contentRootView.findViewById(R.id.main_menu_level);

        this.main_menu_mine = (TextView) contentRootView.findViewById(R.id.main_menu_mine);
        this.main_menu_settings = (TextView) contentRootView.findViewById(R.id.main_menu_settings);
        this.main_menu_about = (TextView) contentRootView.findViewById(R.id.main_menu_about);
    }

    private void setLeftMenuClickListener() {
        this.main_menu_cover.setOnClickListener(leftMenuClickListener);
        this.main_menu_mine.setOnClickListener(leftMenuClickListener);
        this.main_menu_settings.setOnClickListener(leftMenuClickListener);
        this.main_menu_about.setOnClickListener(leftMenuClickListener);
    }

    @Override
    protected void initToolBar(View contentView) {
        super.initToolBar(contentView);

//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.main_home_action_student:
//                        MMLogManager.logD(TAG + ", 点击了学生");
//                        break;
//
//                    case R.id.main_home_action_teacher:
//                        break;
//
//                    case R.id.main_home_action_doctor:
//                        MMLogManager.logD(TAG + ", 点击了医生");
//                        break;
//
//                    default:
//                        break;
//                }
//                return true;
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < fragments.length; i++) {
            if (i != mCurrentIndex) {
                fragments[i].setUserVisibleHint(false);
            }
        }
        fragments[mCurrentIndex].setUserVisibleHint(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MMLogManager.logD(TAG + ", onCreateOptionsMenu, mCurrentIndex = " + mCurrentIndex);
        this.mMenu = menu;
        for (OnMenuInflateListener listener : menuInflateListenerList) {
            listener.onMenuInflated(menu);
        }
//        if (0 == mCurrentIndex) {
//            getMenuInflater().inflate(R.menu.main_home_menu, menu);
//        } else if (1 == mCurrentIndex) {
//            getMenuInflater().inflate(R.menu.main_message_menu, menu);
//        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MMLogManager.logD(TAG + ", onOptionsItemSelected, item = " + item.getTitle());
        return true;
    }

    @Override
    protected void handleBack() {
        if (null != slidingPaneLayout && slidingPaneLayout.isOpen() && slidingPaneLayout.isSlideable()) {
            slidingPaneLayout.closePane();
            return ;
        }
        if(0 != mCurrentIndex){
            switchTab(findViewById(R.id.radio_button_nearby));
            return ;
        }
        if (isDoubleClick) {
            finish();
            Process.killProcess(Process.myPid());
            return ;
        }
        Toast toast =  Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT);
        toast.getView().setBackgroundDrawable(CcbSkinColorTool.getInstance().changeDrawableColor(getResources().getDrawable(R.drawable.home_quite_app_toast_bg)));
        toast.show();
        isDoubleClick = true;
        if (null == mBackDelayHandler) {
            mBackDelayHandler = new MyHandler(this);
        }
        mBackDelayHandler.sendEmptyMessageDelayed(0, DOUBLE_CLICK_DELAY_TIME);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mm_main;
    }

    @Override
    protected void initView() {
        setToolbarIndicator(R.drawable.main_menu);
        findWidget();
        initViewPager();
        setListener();
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void findWidget() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        navigation_bar = (RadioGroup) findViewById(R.id.navigation_bar);
        radio_button_nearby = (RadioButton) findViewById(R.id.radio_button_nearby);
        radio_button_message = (RadioButton) findViewById(R.id.radio_button_message);
        radio_button_friends = (RadioButton) findViewById(R.id.radio_button_friends);
        radio_button_dynamic = (RadioButton) findViewById(R.id.radio_button_dynamic);
        radio_button_info_center = (RadioButton) findViewById(R.id.radio_button_info_center);
    }

    private void setListener() {
        radio_button_nearby.setOnClickListener(clickListener);
        radio_button_message.setOnClickListener(clickListener);
        radio_button_friends.setOnClickListener(clickListener);
        radio_button_dynamic.setOnClickListener(clickListener);
        radio_button_info_center.setOnClickListener(clickListener);
    }

    public void initViewPager() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        final List<String> title = new ArrayList<>();

        for (int i = 0; i < fragments.length; i++) {
            fragmentList.add(fragments[i]);
            title.add(mResources.getString(mTitles[i]));
            fragments[i].setmTitle(mResources.getString(mTitles[i]));
        }

        if (null == viewPager.getAdapter()) {
            BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragmentList, title);
            viewPager.setAdapter(adapter);
        } else {
            final BaseFragmentAdapter adapter = (BaseFragmentAdapter) viewPager.getAdapter();
            adapter.updateFragments(fragmentList, title);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
                setDrawerSlideAble();

                ((RadioButton) navigation_bar.getChildAt(position)).setChecked(true);
                setToolbarTitle(fragments[mCurrentIndex].getmTitle());
                checkState();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0, false);
        //viewPager.setOffscreenPageLimit(4);
        setToolbarTitle(fragments[mCurrentIndex].getmTitle());
        setDrawerSlideAble();
    }

    private void setDrawerSlideAble() {
        if (0 == mCurrentIndex) {
            setDrawerCanSlide(true, true);
        } else {
            setDrawerCanSlide(false, true);
        }
    }


    private void checkState() {
        RadioGroup tabContainer = (RadioGroup) findViewById(R.id.navigation_bar);
        int skinColor = CcbSkinColorTool.getInstance().getSkinColor();
        ColorStateList colorStateList;

        int normalColor = TAB_COLOR_GRAY;// 正常颜色
        int checkColor = TAB_COLOR_WHITE;// 选中颜色
        boolean isLightSkin = true;

        if (skinColor != TAB_COLOR_ORG) {// 背景透明、选中为换肤颜色、未选中灰色
            checkColor = skinColor;
            isLightSkin = false;
        }
        // 字体颜色
        int[][] states = new int[][]{{android.R.attr.state_checked}, {-android.R.attr.state_checked}, {}};
        colorStateList = new ColorStateList(states, new int[]{checkColor, normalColor, normalColor});
        // 选中图片颜色
        for (int i = 0, count = tabContainer.getChildCount(); i < count; i++) {
            RadioButton child = (RadioButton) tabContainer.getChildAt(i);
            child.setTextColor(colorStateList);
            child.setBackgroundDrawable(isLightSkin ? getResources().getDrawable(R.drawable.main_nav_button_bg) : new ColorDrawable(Color.TRANSPARENT));
            Drawable skinDrawable;
            if (child.isChecked()) {// 选中
                skinDrawable = CcbSkinColorTool.getInstance().changeDrawableColor(child.getCompoundDrawables()[1], checkColor);
            } else {// 未选中
                skinDrawable = CcbSkinColorTool.getInstance().changeDrawableColor(child.getCompoundDrawables()[1], normalColor);
            }

            int width = getResources().getDimensionPixelOffset(R.dimen.y72);
            skinDrawable.setBounds(0, 0, width, width);
            child.setCompoundDrawables(null, skinDrawable, null, null);
        }
    }

    static class MyHandler extends Handler {
        private WeakReference<Activity> mActivity = null;
        MyHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            if (null == mActivity.get()) {
                MMLogManager.logD(mActivity.getClass().getSimpleName() + ", Activity has Destory.....");
                return;
            }
            isDoubleClick = false;
        }
    }

    public void removeMenuInflate(OnMenuInflateListener listener) {
        menuInflateListenerList.remove(listener);
    }

    public void registMenuInflate(OnMenuInflateListener listener) {
        menuInflateListenerList.add(listener);
    }

    public interface OnMenuInflateListener {
        void onMenuInflated(Menu menu);
    }

    @Override
    protected void onLeftMenuOpened(View panel) {
        super.onLeftMenuOpened(panel);
        CustomUser customUser = MMApplication.getInstance().getCustomUser();
        if (null != customUser && !TextUtils.isEmpty(customUser.getHeadUrl())) {
            GlideUtils.loadHeadImg(customUser.getHeadUrl(), main_menu_cover, false, DecodeFormat.DEFAULT, new GlideCircleTransform(mContext), DiskCacheStrategy.SOURCE);
        }
    }
}
