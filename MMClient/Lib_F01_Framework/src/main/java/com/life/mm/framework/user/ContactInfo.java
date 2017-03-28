package com.life.mm.framework.user;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.user <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/27 15:25. <P>
 * Function: 联系方式<P>
 * Modified: <P>
 */

@AVClassName("ContactInfo")
public class ContactInfo extends AVObject{

    public ContactInfo() {
        super();
    }
    public ContactInfo(Parcel in) {
        super(in);
    }
    //此处为我们的默认实现，当然你也可以自行实现
    public static final Creator CREATOR = AVObjectCreator.instance;

    public static class Constants {
        public static final String QQ_KEY = "qq";
        public static final String WEI_BO_KEY = "weiBo";
        public static final String WEI_XIN_KEY = "weiXin";
        public static final String ADDRESS_KEY = "address";
    }

    private String qq = null;
    private String weiBo = null;
    private String weiXin = null;
    private String address = null;//地址

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
}
