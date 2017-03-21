package com.mm.life.settings;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.life.mm.framework.app.MMApplication;
import com.life.mm.framework.app.base.activity.BaseActivity;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.mm.life.settings <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/16 14:32. <P>
 * Function: <P>
 * Modified: <P>
 */

public class SettingsActivity extends BaseActivity {

    private RelativeLayout settings_account_safe = null;
    private RelativeLayout settings_clear_storage = null;
    private RelativeLayout settings_app_share = null;
    private Button settings_quite_app = null;


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (R.id.settings_account_safe == v.getId()) {

            } else if (R.id.settings_clear_storage == v.getId()) {

            } else if (R.id.settings_app_share == v.getId()) {

            } else if (R.id.settings_quite_app == v.getId()) {
                MMApplication.getInstance().getAppManager().AppExit(mContext, false);
            }
        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.app_settings);
        findWidget();
        setListener();
        initWidget();
    }

    private void initWidget() {
        if (null != MMApplication.getInstance().getAvUser()) {
            settings_quite_app.setVisibility(View.VISIBLE);
        } else {
            settings_quite_app.setVisibility(View.GONE);
        }
    }

    private void findWidget() {
        settings_account_safe = (RelativeLayout) findViewById(R.id.settings_account_safe);
        settings_clear_storage = (RelativeLayout) findViewById(R.id.settings_clear_storage);
        settings_app_share = (RelativeLayout) findViewById(R.id.settings_app_share);
        settings_quite_app = (Button) findViewById(R.id.settings_quite_app);
    }
    private void setListener() {
        settings_account_safe.setOnClickListener(onClickListener);
        settings_clear_storage.setOnClickListener(onClickListener);
        settings_app_share.setOnClickListener(onClickListener);
        settings_quite_app.setOnClickListener(onClickListener);
    }
    @Override
    protected void initPresenter() {

    }
}
