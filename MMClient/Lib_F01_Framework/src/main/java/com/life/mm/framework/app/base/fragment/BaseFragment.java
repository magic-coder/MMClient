package com.life.mm.framework.app.base.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.life.mm.common.log.MMLogManager;
import com.life.mm.framework.app.base.presenter.BasePresenter;

/**
 * Created by Thinkpad on 2017/2/27.
 */

public abstract class BaseFragment<P extends BasePresenter> extends LazyLoadFragment {

    protected String TAG = getClass().getSimpleName();
    protected P mPresenter = null;


    protected FragmentManager fragmentManager = null;
    protected Context mContext = null;
    protected Resources mResources = null;
    protected LayoutInflater mLayoutInflater = null;

    protected String mTitle = null;
    protected int mTitleResId = 0;


    protected abstract int getLayoutId();
    protected abstract void initPresenter();


    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MMLogManager.logD(TAG + ", onAttach");
        this.mContext = getActivity();
        this.mResources = mContext.getResources();
        this.mLayoutInflater = LayoutInflater.from(mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.fragmentManager = getChildFragmentManager();
        } else {
            this.fragmentManager = getFragmentManager();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MMLogManager.logD(TAG + ", onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MMLogManager.logD(TAG + ", onCreateView");
        initPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MMLogManager.logD(TAG + ", onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        MMLogManager.logD(TAG + ", onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        MMLogManager.logD(TAG + ", onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        MMLogManager.logD(TAG + ", onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        MMLogManager.logD(TAG + ", onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MMLogManager.logD(TAG + ", onDestroyView");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MMLogManager.logD(TAG + ", onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MMLogManager.logD(TAG + ", onDetach");
    }
}
