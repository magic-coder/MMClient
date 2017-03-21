package com.life.mm.app;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.life.mm.MMMainActivity;
import com.life.mm.R;
import com.life.mm.common.log.MMLogManager;
import com.life.mm.framework.app.base.activity.BaseActivity;
import com.life.mm.framework.app.base.fragment.BaseFragment;
import com.life.mm.map.contract.MessageContract;

/**
 * Created by Thinkpad on 2017/2/28.
 */

public class MainMessageFragment extends BaseFragment<MessageContract.Presenter> {

    private Menu mMenu = null;
    private Toolbar toolbar = null;
    private MenuInflater menuInflater = null;

    private MMMainActivity.OnMenuInflateListener onMenuInflateListener = new MMMainActivity.OnMenuInflateListener() {
        @Override
        public void onMenuInflated(Menu menu) {
            mMenu = menu;
            menuInflater = getActivity().getMenuInflater();
            mMenu.clear();
            menuInflater.inflate(R.menu.main_home_menu, menu);

        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_message;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View rootView) {
    }

    @Override
    protected void doLazyLoad() {

    }


    private void initToolbar() {
        if (null != mContext) {
            this.toolbar = ((BaseActivity) mContext).getToolbar();
            this.menuInflater = ((BaseActivity) mContext).getMenuInflater();
        }
        if (null != toolbar) {
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    MMLogManager.logD(TAG + item.getTitle());
                    switch (item.getItemId()) {
                        case R.id.main_home_action_clear_all:
                            break;

                        case R.id.main_home_action_clear_selected:
                            break;

                        case R.id.main_home_action_filter:
                            break;

                        default:
                            break;
                    }
                    return true;
                }
            });
        }

        if (null != mMenu && null != menuInflater) {
            menuInflater.inflate(R.menu.main_message_menu, mMenu);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MMLogManager.logD(TAG + ", onCreateOptionsMenu");
        this.mMenu = menu;
        this.menuInflater = inflater;
        this.mMenu.clear();
        inflater.inflate(R.menu.main_home_menu, menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MMMainActivity) mContext).registMenuInflate(onMenuInflateListener);
        initToolbar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MMMainActivity) mContext).removeMenuInflate(onMenuInflateListener);
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        initToolbar();
    }

    @Override
    protected void onInVisible() {
        super.onInVisible();
        if (null == toolbar && null != mContext) {
            this.toolbar = ((BaseActivity) mContext).getToolbar();
            this.mMenu = ((MMMainActivity) mContext).getmMenu();
        }
        if (null != mMenu) {
            mMenu.clear();
        }
    }
}
