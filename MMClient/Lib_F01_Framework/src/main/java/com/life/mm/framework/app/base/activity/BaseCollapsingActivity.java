package com.life.mm.framework.app.base.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.life.mm.framework.R;
import com.life.mm.framework.libwrapper.glide.GlideUtils;
import com.life.mm.framework.utils.GlideCircleTransform;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.base.activity <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/17 21:29. <P>
 * Function: <P>
 * Modified: <P>
 */

public abstract class BaseCollapsingActivity extends BaseActivity {

    private AppBarLayout framework_app_bar = null;
    private CollapsingToolbarLayout framework_collaps_tool_bar = null;
    private Toolbar framework_toolbar = null;
    private ImageView framework_header_img = null;
    private FloatingActionButton framework_floating_action_button = null;
    private LinearLayout framework_content = null;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.framework_header_img) {
                onClickHeadImg();
            } else if (v.getId() == R.id.framework_floating_action_button) {
                onClickEdit();
            }
        }
    };

    @Override
    protected boolean shouldUseBaseLayout() {
        return false;
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        findWidget();
        initWidget();
        initCollapsToolbar();
        setMyContent();
    }

    private void initWidget() {
        framework_header_img.setOnClickListener(onClickListener);
        framework_floating_action_button.setOnClickListener(onClickListener);
    }

    private void findWidget() {
        framework_app_bar = (AppBarLayout) findViewById(R.id.framework_app_bar);
        framework_collaps_tool_bar = (CollapsingToolbarLayout) findViewById(R.id.framework_collaps_tool_bar);
        framework_toolbar = (Toolbar) findViewById(R.id.framework_toolbar);
        framework_header_img = (ImageView) findViewById(R.id.framework_header_img);
        framework_floating_action_button = (FloatingActionButton) findViewById(R.id.framework_floating_action_button);
        framework_content = (LinearLayout) findViewById(R.id.framework_content);
    }

    private void initCollapsToolbar() {
        setSupportActionBar(framework_toolbar);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
        }
        framework_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setMyContent() {
        framework_content.addView(mLayoutInflater.inflate(getContentLayout(), null, false));
    }

    protected void setEditFloatActionBarVisible(boolean visible) {
        framework_floating_action_button.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    protected void loadHeadImg(String url) {
        GlideUtils.loadDefault(url, framework_header_img, false,
                DecodeFormat.DEFAULT, new GlideCircleTransform(mContext), DiskCacheStrategy.SOURCE);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_collapsing;
    }

    @Override
    protected void setToolbarTitle(int strId) {
        setToolbarTitle(mResources.getString(strId));
    }

    @Override
    protected void setToolbarTitle(String str) {
        framework_collaps_tool_bar.setTitle(str);
    }

    protected abstract int getContentLayout();
    protected abstract void  onClickHeadImg();
    protected void onClickEdit() {}
}
