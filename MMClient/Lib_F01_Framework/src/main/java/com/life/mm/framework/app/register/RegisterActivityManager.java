package com.life.mm.framework.app.register;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/17 10:30. <P>
 * Function: <P>
 * Modified: <P>
 */

public class RegisterActivityManager extends RegisterResultListener{

    private String TAG = RegisterActivityManager.class.getSimpleName();

    private static RegisterActivityManager instance = null;

    private List<RegisterResultListener> listeners = null;
    public static RegisterActivityManager getInstance() {
        if (null == instance) {
            synchronized (RegisterActivityManager.class) {
                if (null == instance) {
                    instance = new RegisterActivityManager();
                }
            }
        }
        return instance;
    }

    private RegisterActivityManager() {
        init();
    }

    private void init() {
        listeners = new ArrayList<>();
    }

    public void registerListener(RegisterResultListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(RegisterResultListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    private void clearListener() {
        listeners.clear();
    }

    @Override
    protected void onSuccess() {
        for (RegisterResultListener listener : listeners) {
            listener.onSuccess();
        }
        clearListener();
    }
}
