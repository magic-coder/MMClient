package com.life.mm.framework.ui.view;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by zjj on 2016/4/20.
 */
public abstract class mmOnItemClickListener implements AdapterView.OnItemClickListener {

    public long MIN_CLICK_DELAY_TIME = 800;
    private long lastClickTime = 0;

    public mmOnItemClickListener() {
    }

    public mmOnItemClickListener(long MIN_CLICK_DELAY_TIME) {
        this.MIN_CLICK_DELAY_TIME = MIN_CLICK_DELAY_TIME;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastClickTime > MIN_CLICK_DELAY_TIME){
            lastClickTime = currentTime;
            mmOnItemClick(parent, view, position, id);
        }
    }

    public abstract void mmOnItemClick(AdapterView<?> parent, View view, int position, long id);
}
