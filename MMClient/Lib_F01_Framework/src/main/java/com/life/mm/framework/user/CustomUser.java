package com.life.mm.framework.user;

import com.avos.avoscloud.AVUser;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.user <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/20 16:49. <P>
 * Function: 系统用户表，并不会开放所有的查询权限<P>
 * Modified: <P>
 */

public class CustomUser extends AVUser {

    private int age = 0;//年龄
    private String headUrl = null;//头像
    private String nickName = null;//昵称
    private String address = null;//地址
    private double height = 0d;//身高[cm]
    private double weight = 0d;//体重[kg]
    private String selfDescription = null;//自我介绍
    private String diyName = null;//修改签名
    private String photoAlbum;//照片墙

    public int getAge() {
        return getInt("age");
    }

    public void setAge(int age) {
        put("age", age);
    }

    public String getHeadUrl() {
        return getString("headUrl");
    }

    public void setHeadUrl(String headUrl) {
        put("headUrl", headUrl);
    }

    public String getNickName() {
        return getString("nickName");
    }

    public void setNickName(String nickName) {
        put("nickName", nickName);
    }

    public String getAddress() {
        return getString("address");
    }

    public void setAddress(String address) {
        put("address", address);
    }

    public double getHeight() {
        return getDouble("height");
    }

    public void setHeight(double height) {
        put("height", height);
    }

    public double getWeight() {
        return getDouble("weight");
    }

    public void setWeight(double weight) {
        put("weight", weight);
    }

    public String getSelfDescription() {
        return getString("selfDescription");
    }

    public void setSelfDescription(String selfDescription) {
        put("selfDescription", selfDescription);
    }

    public String getDiyName() {
        return getString("diyName");
    }

    public void setDiyName(String diyName) {
        put("diyName", diyName);
    }

    public String getPhotoAlbum() {
        return getString("photoAlbum");
    }

    public void setPhotoAlbum(String photoAlbum) {
        put("photoAlbum", photoAlbum);
    }
}
