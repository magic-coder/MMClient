package com.life.mm;

import com.life.mm.common.log.MMLogManager;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/13 16:05. <P>
 * Function: <P>
 * Modified: <P>
 */

public class Test implements Runnable{

    public Test() {
        MMLogManager.logE("NEW tEST");
    }

    @Override
    public void run() {
        MMLogManager.logE("run thread begin thread = " + Thread.currentThread().getId());

        runThread();
        MMLogManager.logE("run thread end");
    }

    public void runThread() {
        MMLogManager.logE("enter sync");
        synchronized (Test.class) {
            try {
                Thread.sleep(45 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MMLogManager.logE("exit sync");
    }
}
