package com.life.mm.framework.user;

import android.os.Parcel;
import android.text.TextUtils;

import com.avos.avoscloud.AVClassName;

import static com.life.mm.framework.user.DevUser.Constants.DEV_OBJECT_ID_KEY;
import static com.life.mm.framework.user.DevUser.Constants.EMAIL_KEY;
import static com.life.mm.framework.user.DevUser.Constants.MOBILE_PHONE_NUMBER_KEY;
import static com.life.mm.framework.user.DevUser.Constants.USER_NAME_KEY;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.user <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/20 16:47. <P>
 * Function: 和CustomUser数据保持一致，但是拥有所有的权限,独立成一个数据表，不受leanCloud权限限制<P>
 * Modified: <P>
 */

@AVClassName("DevUser")
public class DevUser extends BaseUser {

    public class Constants {
        public static final String DEV_OBJECT_ID_KEY = "devObjectId";
        public static final String CUSTOM_USER_KEY = "customUser";
        public static final String USER_NAME_KEY = "userName";
        public static final String EMAIL_KEY = "email";
        public static final String MOBILE_PHONE_NUMBER_KEY = "mobilePhoneNumber";
    }
    /**
     * 增加一个devObjectId字段表示当前User对应的CustomerUser
     */
    private String devObjectId = "";
    private CustomUser customUser = null;

    private String userName = "";
    private String email = "";
    private String mobilePhoneNumber = "";
    public DevUser() {
        super();
    }

    public DevUser(Parcel in){
        super(in);
    }
    //此处为我们的默认实现，当然你也可以自行实现
    public static final Creator CREATOR = AVObjectCreator.instance;

    public DevUser(CustomUser customUser) {
        setUser(customUser);
    }

    public void setUser(CustomUser customUser) {
        //setCustomUser(customUser);
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
        this.setBirthDay(customUser.getBirthDay());
        this.setConstellation(customUser.getConstellation());
        this.setOccupation(customUser.getOccupation());
        this.setCompany(customUser.getCompany());
        this.setSchool(customUser.getSchool());
        this.setHometownAddress(customUser.getHometownAddress());

        this.setAddress(customUser.getAddress());
        this.setQq(customUser.getQq());
        this.setWeiBo(customUser.getWeiBo());
        this.setWeiXin(customUser.getWeiXin());


        this.setDevObjectId(customUser.getObjectId());
        this.setUserName(customUser.getUsername());
        this.setEmail(TextUtils.isEmpty(customUser.getEmail()) ? "" : customUser.getEmail());
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
        put(DEV_OBJECT_ID_KEY, devObjectId);
    }

    public String getDevObjectId() {
        return (TextUtils.isEmpty(devObjectId)) ? (devObjectId = getString(DEV_OBJECT_ID_KEY)) : devObjectId;
    }

    public String getUserName() {
        return (TextUtils.isEmpty(userName)) ? (userName = getString(USER_NAME_KEY)) : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        put(USER_NAME_KEY, userName);
    }

    public String getEmail() {
        return (TextUtils.isEmpty(email)) ? (email = getString(EMAIL_KEY)) : email;
    }

    public void setEmail(String email) {
        this.email = email;
        put(EMAIL_KEY, email);
    }

    public String getMobilePhoneNumber() {
        return (TextUtils.isEmpty(mobilePhoneNumber)) ? (mobilePhoneNumber = getString(MOBILE_PHONE_NUMBER_KEY)) : mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
        put(MOBILE_PHONE_NUMBER_KEY, mobilePhoneNumber);
    }
}
