package com.life.mm.framework.app.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.life.mm.common.log.MMLogManager;

import butterknife.ButterKnife;

/**
 * Created by Thinkpad on 2017/2/28.
 */

/**
 * 所有的 fragment都要继承于此类
 */
public abstract class LazyLoadFragment extends Fragment {

    protected String TAG = getClass().getSimpleName();

    public enum LoadStatus {
        PrevInit(0),   //还没有初始化
        Init(1),       //正在初始化
        AfterInit(2),  //初始化过程执行完成(这是个瞬时状态，之后根据执行结果是成功还是失败最终转到到InitError或者InitSuccess状态)
        InitError(3),  //初始化成功
        InitSuccess(4);//初始化失败

        int status = 0;
        LoadStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    };
    /** 标志位，标志已经初始化完成 */
    private boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    protected boolean mHasLoadedOnce;
    protected View mFragmentView = null;
    protected boolean isVisible = false;
    protected LoadStatus loadStatus = LoadStatus.PrevInit;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInVisible();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mFragmentView) {
            mFragmentView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mFragmentView);
            isPrepared = true;
            initView(mFragmentView);
            lazyLoad();
        }
        ViewGroup parentView = (ViewGroup) mFragmentView.getParent();
        if (null != parentView) {
            parentView.removeView(mFragmentView);
        }
        return mFragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(mFragmentView);
    }

    protected void lazyLoad() {
        if (!isPrepared || mHasLoadedOnce || !isVisible) {
            return;
        }
        MMLogManager.logE(TAG + ", lazyLoad start ->");
        doLazyLoad();
    }

    /**
     * fragment可见
     */
    protected void onVisible() {
        MMLogManager.logD(TAG + ", onVisible");
        lazyLoad();
    }

    /**
     * fragment不可见
     */
    protected void onInVisible() {
        MMLogManager.logD(TAG + ", onInVisible");
    }
    protected abstract void initView(View rootView);
    protected abstract int getLayoutId();
    protected abstract void doLazyLoad();
}
