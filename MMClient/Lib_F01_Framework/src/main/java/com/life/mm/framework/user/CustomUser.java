package com.life.mm.framework.user;

import android.text.TextUtils;

import com.avos.avoscloud.AVUser;

import static com.life.mm.framework.user.BaseUser.Constants.NICK_NAME_PY_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.ADDRESS_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.AGE_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.BIRTHDAY_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.COMPANY_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.CONSTELLATION_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.DIY_NAME_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.GENDER_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.HEAD_URL_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.HEIGHT_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.HOMETOWN_ADDRESS_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.NICK_NAME_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.OCCUPATION_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.PHOTO_ALBUM_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.QQ_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.SCHOOL_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.SELF_DESCRIPTION_KEY;
import static com.life.mm.framework.user.CustomUser.Constants.WEIGHT_KEY;
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
        public static final String NICK_NAME_PY_KEY = "nickNamePY";
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
    private String headUrl = "";//头像
    private String nickName = "";//昵称
    private String nickNamePy = "";//昵称汉字拼音
    private double height = 0d;//身高[cm]
    private double weight = 0d;//体重[kg]
    private String selfDescription = "";//自我介绍
    private String diyName = "";//修改签名
    private String photoAlbum;//照片墙
    private String birthDay = "";//生日
    private String constellation = "";//星座
    private String occupation = "";//职业
    private String company = "";//公司
    private String school = "";//学校
    private String hometownAddress = "";//家乡

    //联系方式
    private String address = "";//当前地址
    private String qq = "";
    private String weiBo = "";
    private String weiXin = "";

    public int getAge() {
        if (0 == age) {
            this.age = getInt(AGE_KEY);
        }
        return getInt(AGE_KEY);
    }

    public void setAge(int age) {
        this.age = age;
        put(AGE_KEY, age);
    }

    public int getGender() {
        if (-1 == gender) {
            this.gender = getInt(GENDER_KEY);
        }
        return gender;
    }

    public void setGender(int gender) {
        this.put(Constants.GENDER_KEY, gender);
        this.gender = gender;
    }

    public String getHeadUrl() {
        if (TextUtils.isEmpty(headUrl)) {
            this.headUrl = getString(HEAD_URL_KEY);
            if (TextUtils.isEmpty(headUrl)) {
                headUrl = "";
            }
        }
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
        put(HEAD_URL_KEY, headUrl);
    }

    public String getNickName() {
        if (TextUtils.isEmpty(nickName)) {
            this.nickName = getString(NICK_NAME_KEY);
            if (TextUtils.isEmpty(nickName)) {
                nickName = "";
            }
        }
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        put(NICK_NAME_KEY, nickName);
    }

    public String getNickNamePy() {
        if (TextUtils.isEmpty(nickNamePy)) {
            this.nickNamePy = getString(NICK_NAME_PY_KEY);
        }
        return nickNamePy;
    }

    public void setNickNamePy(String nickNamePy) {
        this.nickNamePy = nickNamePy;
        put(NICK_NAME_PY_KEY, nickNamePy);
    }

    public String getAddress() {
        if (TextUtils.isEmpty(address)) {
            this.address = getString(ADDRESS_KEY);
            if (TextUtils.isEmpty(address)) {
                address = "";
            }
        }
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
        put(ADDRESS_KEY, address);
    }

    public double getHeight() {
        if (0 == height) {
            height = getDouble(HEIGHT_KEY);
        }
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        put(HEIGHT_KEY, height);
    }

    public double getWeight() {
        return (0 == weight) ? (weight = getDouble(WEIGHT_KEY)) : weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        put(WEIGHT_KEY, weight);
    }

    public String getSelfDescription() {
        if (TextUtils.isEmpty(selfDescription)) {
            this.selfDescription = getString(SELF_DESCRIPTION_KEY);
            if (TextUtils.isEmpty(selfDescription)) {
                selfDescription = "";
            }
        }
        return selfDescription;
    }

    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
        put(SELF_DESCRIPTION_KEY, selfDescription);
    }

    public String getDiyName() {
        if (TextUtils.isEmpty(diyName)) {
            this.diyName = getString(DIY_NAME_KEY);
            if (TextUtils.isEmpty(diyName)) {
                diyName = "";
            }
        }
        return diyName;
    }

    public void setDiyName(String diyName) {
        this.diyName = diyName;
        put(DIY_NAME_KEY, diyName);
    }

    public String getPhotoAlbum() {
        if (TextUtils.isEmpty(photoAlbum)) {
            this.photoAlbum = getString(PHOTO_ALBUM_KEY);
            if (TextUtils.isEmpty(photoAlbum)) {
                photoAlbum = "";
            }
        }
        return photoAlbum;
    }

    public void setPhotoAlbum(String photoAlbum) {
        this.photoAlbum = photoAlbum;
        put(PHOTO_ALBUM_KEY, photoAlbum);
    }

    public String getQq() {
        if (TextUtils.isEmpty(qq)) {
            qq = getString(QQ_KEY);
            if (TextUtils.isEmpty(qq)) {
                qq = "";
            }
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
            if (TextUtils.isEmpty(weiBo)) {
                weiBo = "";
            }
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
            if (TextUtils.isEmpty(weiXin)) {
                weiXin = "";
            }
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
            if (TextUtils.isEmpty(birthDay)) {
                birthDay = "";
            }
        }
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
        this.put(BIRTHDAY_KEY, birthDay);
    }

    public String getConstellation() {
        if (TextUtils.isEmpty(constellation)) {
            this.constellation = getString(CONSTELLATION_KEY);
            if (TextUtils.isEmpty(constellation)) {
                constellation = "";
            }
        }
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
        this.put(CONSTELLATION_KEY, constellation);
    }

    public String getOccupation() {
        if (TextUtils.isEmpty(occupation)) {
            occupation = getString(OCCUPATION_KEY);
            if (TextUtils.isEmpty(occupation)) {
                occupation = "";
            }
        }
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
        this.put(OCCUPATION_KEY, occupation);
    }

    public String getCompany() {
        if (TextUtils.isEmpty(company)) {
            this.company = getString(COMPANY_KEY);
            if (TextUtils.isEmpty(company)) {
                company = "";
            }
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
            if (TextUtils.isEmpty(school)) {
                school = "";
            }
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
            if (TextUtils.isEmpty(hometownAddress)) {
                hometownAddress = "";
            }
        }
        return hometownAddress;
    }

    public void setHometownAddress(String hometownAddress) {
        this.hometownAddress = hometownAddress;
        this.put(HOMETOWN_ADDRESS_KEY, hometownAddress);
    }
}
