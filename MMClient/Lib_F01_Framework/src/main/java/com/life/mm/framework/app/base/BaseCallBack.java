package com.life.mm.framework.app.base;

/**
 * Created by Thinkpad on 2017/2/27.
 */

public interface BaseCallBack {
    void onBegin();
    void doInBackground();
    void onFinish();
    void onSuccess();
    void onError(int status, String message);
}
