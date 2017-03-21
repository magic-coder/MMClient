package com.life.mm.framework.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/15 11:37. <P>
 * Function: <P>
 * Modified: <P>
 */

public class ActivityHelper {

    public static void goActivity(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }

        context.startActivity(intent);
    }
}
