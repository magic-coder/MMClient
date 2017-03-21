package com.life.mm.framework.app.base.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.life.mm.framework.R;
import com.life.mm.framework.utils.ToastUtil;

import java.util.Random;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.base.activity <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/3 14:56. <P>
 * Function: <P>
 * Modified: <P>
 */

public class MapActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collaps_tool_bar;
    private Toolbar framework_toolbar;
    private Random random = new Random();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        collaps_tool_bar = (CollapsingToolbarLayout) findViewById(R.id.collaps_tool_bar);
        framework_toolbar = (Toolbar) findViewById(R.id.framework_toolbar);

        setSupportActionBar(framework_toolbar);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
        }
        int index = random.nextInt(2);
//        if (0 == index) {//禁用滑动效果
//            collaps_tool_bar.setTitleEnabled(true);
//        } else {
//            collaps_tool_bar.setTitleEnabled(false);
//        }
        collaps_tool_bar.setTitle("title");
        framework_toolbar.setContentInsetStartWithNavigation(0);
        framework_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(MapActivity.this, "返回");
                finish();
            }
        });
    }

    /*    @Override
    protected boolean hasLeftMenu() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initPresenter() {

    }*/
}
