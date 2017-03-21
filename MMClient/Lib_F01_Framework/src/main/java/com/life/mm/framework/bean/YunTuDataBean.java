package com.life.mm.framework.bean;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.bean <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/9 11:06. <P>
 * Function: <P>
 * Modified: <P>
 */

public class YunTuDataBean {
    private String _name = null;
    private String _location = null;
    private String coordtype = null;
    private String _address = null;

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String getCoordtype() {
        return coordtype;
    }

    public void setCoordtype(String coordtype) {
        this.coordtype = coordtype;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }
}
