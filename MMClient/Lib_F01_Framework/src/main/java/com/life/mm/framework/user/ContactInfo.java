package com.life.mm.framework.user;

import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.user <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/27 15:25. <P>
 * Function: 联系人<P>
 * Modified: <P>
 */

public class ContactInfo implements Comparable<ContactInfo> {

    private String name = "";
    private String namePY = "ss";
    private String number = "";
    private String headImageUrl = "";
    private String email = "";
    private String qq = null;
    private String weiBo = null;
    private String weiXin = null;
    private String address = null;//地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePY() {
        return namePY;
    }

    public void setNamePY(String namePY) {
        this.namePY = namePY.toUpperCase(Locale.getDefault());
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeiBo() {
        return weiBo;
    }

    public void setWeiBo(String weiBo) {
        this.weiBo = weiBo;
    }

    public String getWeiXin() {
        return weiXin;
    }

    public void setWeiXin(String weiXin) {
        this.weiXin = weiXin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int compareTo(@NonNull ContactInfo o) {
        return namePY.compareToIgnoreCase(o.getNamePY());
    }
}
