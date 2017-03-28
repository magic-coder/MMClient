package com.life.mm.framework.user;

import android.text.TextUtils;

import com.avos.avoscloud.AVUser;

import static com.life.mm.framework.user.CustomUser.Constants.BIRTHDAY_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.COMPANY_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.CONSTELLATION_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.HOMETOWN_ADDRESS_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.OCCUPATION_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.QQ_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.SCHOOL_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.WEI_BO_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.WEI_XIN_KEY;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.user <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/20 16:49. <P>
 * Function: 系统用户表，并不会开放所有的查询权限<P>
 * Modified: <P>
 */

public class CustomUser extends AVUser {

    public static class Constants {
        public static final String AGE_KEY = "age";
        public static final String GENDER_KEY = "gender";
        public static final String HEAD_URL_KEY = "headUrl";
        public static final String NICK_NAME_KEY = "nickName";
        public static final String HEIGHT_KEY = "height";
        public static final String WEIGHT_KEY = "weight";
        public static final String SELF_DESCRIPTION_KEY = "selfDescription";
        public static final String DIY_NAME_KEY = "diyName";
        public static final String PHOTO_ALBUM_KEY ="photoAlbum";
        public static final String BIRTHDAY_KEY = "birthDay";
        public static final String CONSTELLATION_KEY = "constellation";
        public static final String OCCUPATION_KEY = "occupation";
        public static final String COMPANY_KEY = "company";
        public static final String SCHOOL_KEY = "school";
        public static final String HOMETOWN_ADDRESS_KEY = "hometownAddress";

        public static final String QQ_KEY = "qq";
        public static final String WEI_BO_KEY = "weiBo";
        public static final String WEI_XIN_KEY = "weiXin";
        public static final String ADDRESS_KEY = "address";
    }

    //基本信息
    private int age = 0;//年龄
    private int gender = -1;//性别
    private String headUrl = null;//头像
    private String nickName = null;//昵称
    private double height = 0d;//身高[cm]
    private double weight = 0d;//体重[kg]
    private String selfDescription = null;//自我介绍
    private String diyName = null;//修改签名
    private String photoAlbum;//照片墙
    private String birthDay = null;//生日
    private String constellation = null;//星座
    private String occupation = null;//职业
    private String company = null;//公司
    private String school = null;//学校
    private String hometownAddress = null;//家乡

    //联系方式
    private String address = null;//当前地址
    private String qq = null;
    private String weiBo = null;
    private String weiXin = null;

    public int getAge() {
        return getInt("age");
    }

    public void setAge(int age) {
        this.age = age;
        put("age", age);
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.put(Constants.GENDER_KEY, gender);
        this.gender = gender;
    }

    public String getHeadUrl() {
        return getString("headUrl");
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
        put("headUrl", headUrl);
    }

    public String getNickName() {
        return getString("nickName");
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        put("nickName", nickName);
    }

    public String getAddress() {
        return getString("address");
    }

    public void setAddress(String address) {
        this.address = address;
        put("address", address);
    }

    public double getHeight() {
        return getDouble("height");
    }

    public void setHeight(double height) {
        this.height = height;
        put("height", height);
    }

    public double getWeight() {
        return getDouble("weight");
    }

    public void setWeight(double weight) {
        this.weight = weight;
        put("weight", weight);
    }

    public String getSelfDescription() {
        return getString("selfDescription");
    }

    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
        put("selfDescription", selfDescription);
    }

    public String getDiyName() {
        return getString("diyName");
    }

    public void setDiyName(String diyName) {
        this.diyName = diyName;
        put("diyName", diyName);
    }

    public String getPhotoAlbum() {
        return getString("photoAlbum");
    }

    public void setPhotoAlbum(String photoAlbum) {
        this.photoAlbum = photoAlbum;
        put("photoAlbum", photoAlbum);
    }

    public String getQq() {
        if (TextUtils.isEmpty(qq)) {
            qq = getString(QQ_KEY);
        }
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
        this.put(Constants.QQ_KEY, qq);
    }

    public String getWeiBo() {
        if (TextUtils.isEmpty(weiBo)) {
            this.weiBo = getString(WEI_BO_KEY);
        }
        return weiBo;
    }

    public void setWeiBo(String weiBo) {
        this.weiBo = weiBo;
        this.put(Constants.WEI_BO_KEY, weiBo);
    }

    public String getWeiXin() {
        if (TextUtils.isEmpty(weiXin)) {
            this.weiXin = getString(WEI_XIN_KEY);
        }
        return weiXin;
    }

    public void setWeiXin(String weiXin) {
        this.weiXin = weiXin;
        this.put(Constants.WEI_XIN_KEY, weiXin);
    }

    public String getBirthDay() {
        if (TextUtils.isEmpty(birthDay)) {
            this.birthDay = getString(BIRTHDAY_KEY);
        }
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
        this.put(BIRTHDAY_KEY, birthDay);
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
        this.put(CONSTELLATION_KEY, constellation);
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
        this.put(OCCUPATION_KEY, occupation);
    }

    public String getCompany() {
        if (TextUtils.isEmpty(company)) {
            this.company = getString(COMPANY_KEY);
        }
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
        this.put(COMPANY_KEY, company);
    }

    public String getSchool() {
        if (TextUtils.isEmpty(school)) {
            this.school = getString(SCHOOL_KEY);
        }
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
        this.put(SCHOOL_KEY, school);
    }

    public String getHometownAddress() {
        if (TextUtils.isEmpty(hometownAddress)) {
            this.hometownAddress = getString(HOMETOWN_ADDRESS_KEY);
        }
        return hometownAddress;
    }

    public void setHometownAddress(String hometownAddress) {
        this.hometownAddress = hometownAddress;
        this.put(HOMETOWN_ADDRESS_KEY, hometownAddress);
    }
}
