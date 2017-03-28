package com.life.mm.framework.user;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.life.mm.common.log.MMLogManager;
import com.life.mm.framework.app.MMApplication;
import com.life.mm.framework.app.base.BaseCallBack;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.user <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/27 10:58. <P>
 * Function: <P>
 * Modified: <P>
 */

public class UserManager {

    private String TAG = UserManager.class.getSimpleName();
    private static UserManager INSTANCE = null;

    public static UserManager getInstance() {
        if (null == INSTANCE) {
            synchronized (UserManager.class) {
                if (null == INSTANCE) {
                    INSTANCE = new UserManager();
                }
            }
        }
        return INSTANCE;
    }

    private UserManager() {
        init();
    }
    private void init() {

    }

    public void saveDevUser(AVUser avUser) {
        DevUser devUser = new DevUser(avUser);
        devUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                MMLogManager.logD(TAG + (null != e ? ", Save Failure" : "Save Success"));
            }
        });
    }

    /**
     * 保存当前操作的User对象，让CustomUser对象的数据与DevUser对象数据保持一致.
     * @param avUser    当前操作的对象
     */
    public void saveDevUser(final CustomUser avUser) {
        MMApplication.getInstance().setUserShouldRefresh(true);//用户数据表有变动，通知下次在使用的时候应该刷新当前值。
        AVQuery<DevUser> customUserAVQuery = new AVQuery<>(DevUser.class.getSimpleName());
        customUserAVQuery.whereEqualTo(DevUser.Constants.DEV_OBJECT_ID_KEY, avUser.getObjectId());
        customUserAVQuery.getFirstInBackground(new GetCallback<DevUser>() {
            @Override
            public void done(DevUser devUser, AVException e) {
                if (null == e) {
                    if (null != devUser) {
                        devUser.setUser(avUser);
                    } else {
                        devUser = new DevUser(avUser);
                    }
                } else {
                    MMLogManager.logD(TAG + ", Query Failure, e = " + e);
                    if (AVException.OBJECT_NOT_FOUND == e.getCode()) {
                        MMLogManager.logD(TAG + ", Class not create. now created.");
                        devUser = new DevUser(avUser);
                    }
                }

                devUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        MMLogManager.logD(TAG + (null != e ? ", Save Failure" : "Save Success"));
                    }
                });
            }
        });
    }

    /**
     * 根据objectId查询相应的user信息
     * @param callBack
     * @param objectId
     * @param userCallback
     */
    public void queryDevUser(final BaseCallBack callBack, String objectId, final OnQueryUserCallback<DevUser> userCallback) {
        callBack.onBegin();
        AVQuery<DevUser> devUserAVQuery = new AVQuery<>(DevUser.class.getSimpleName());
        devUserAVQuery.getInBackground(objectId, new GetCallback<DevUser>() {
            @Override
            public void done(DevUser devUser, AVException e) {
                callBack.onFinish();
                if (null != e) {
                    callBack.onError(e.getCode(), e.getMessage());
                } else {
                    userCallback.onGetUser(devUser);
                }
            }
        });
    }
}
