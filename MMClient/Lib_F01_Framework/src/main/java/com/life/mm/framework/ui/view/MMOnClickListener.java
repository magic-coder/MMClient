package com.life.mm.framework.ui.view;

import android.view.View;

public abstract class MMOnClickListener implements View.OnClickListener {
    public MMOnClickListener() {
    }

    public MMOnClickListener(long MIN_CLICK_DELAY_TIME) {
        this.MIN_CLICK_DELAY_TIME = MIN_CLICK_DELAY_TIME;
    }

    public MMOnClickListener(long MIN_CLICK_DELAY_TIME, boolean collect) {
        this.MIN_CLICK_DELAY_TIME = MIN_CLICK_DELAY_TIME;
    }

    public long MIN_CLICK_DELAY_TIME = 800;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            mmOnClick(v);
        }
    }

    public abstract void mmOnClick(View view);
}
