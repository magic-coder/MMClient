package com.life.mm.app;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.life.mm.R;
import com.life.mm.framework.app.base.fragment.BaseFragment;
import com.life.mm.friends.fragment.FolloweesFragment;
import com.life.mm.friends.fragment.FollowersFragment;
import com.life.mm.friends.fragment.FriendsFragment;
import com.life.mm.friends.fragment.PhoneContactFragment;

import butterknife.Bind;

/**
 * 通讯录好友主页
 */

public class MainFriendsFragment extends BaseFragment {


    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private String[] contactTitle = null;
    private BaseFragment[] fragments = new BaseFragment[] {
            new FriendsFragment(),
            new FolloweesFragment(),
            new FollowersFragment(),
            new PhoneContactFragment()
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_friends;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View rootView) {
        contactTitle = mResources.getStringArray(R.array.contact_page_title);
        initViewPager();
    }

    private void initViewPager() {
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.text_normal_size));
        viewPager.setOffscreenPageLimit(4);
        ContactAdapter contactAdapter = new ContactAdapter(getFragmentManager());
        viewPager.setAdapter(contactAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void doLazyLoad() {

    }

    class ContactAdapter extends FragmentStatePagerAdapter {


        ContactAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return contactTitle[position];
        }
    }
}
