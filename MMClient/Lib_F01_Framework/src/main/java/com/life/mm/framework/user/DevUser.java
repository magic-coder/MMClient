package com.life.mm.framework.user;

import com.avos.avoscloud.AVUser;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.user <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/20 16:47. <P>
 * Function: 和CustomUser数据保持一致，但是拥有所有的权限,独立成一个数据表，不受leanCloud权限限制<P>
 * Modified: <P>
 */

public class DevUser extends CustomUser{


    public class Constants {
        public static final String DEV_OBJECT_ID_KEY = "devObjectId";
        public static final String CUSTOM_USER_KEY = "customUser";
    }
    /**
     * 增加一个devObjectId字段表示当前User对应的CustomerUser
     */
    private String devObjectId = null;
    private CustomUser customUser = null;

    public DevUser(AVUser avUser) {
        this.setUsername(avUser.getUsername());
        this.setEmail(avUser.getEmail());
        this.setMobilePhoneNumber(avUser.getMobilePhoneNumber());
    }

    public DevUser(CustomUser customUser) {
        setUser(customUser);
    }

    public void setUser(CustomUser customUser) {
        this.customUser = customUser;
        this.devObjectId = customUser.getObjectId();


        this.setAge(customUser.getAge());
        this.setGender(customUser.getGender());
        this.setHeadUrl(customUser.getHeadUrl());
        this.setNickName(customUser.getNickName());
        this.setHeight(customUser.getHeight());
        this.setWeight(customUser.getWeight());
        this.setSelfDescription(customUser.getSelfDescription());
        this.setDiyName(customUser.getDiyName());
        this.setPhotoAlbum(customUser.getPhotoAlbum());

        this.setAddress(customUser.getAddress());
        this.setQq(customUser.getQq());
        this.setWeiBo(customUser.getWeiBo());
        this.setWeiXin(customUser.getWeiXin());


        this.setDevObjectId(customUser.getObjectId());
        this.setUsername(customUser.getUsername());
        this.setEmail(customUser.getEmail());
        this.setMobilePhoneNumber(customUser.getMobilePhoneNumber());
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
        this.put(Constants.CUSTOM_USER_KEY, customUser);
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setDevObjectId(String devObjectId) {
        this.devObjectId = devObjectId;
        put("devObjectId", devObjectId);
    }

    public String getDevObjectId() {
        return devObjectId;
    }
}
