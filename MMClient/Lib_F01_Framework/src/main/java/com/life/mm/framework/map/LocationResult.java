package com.life.mm.framework.map;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ProjectName:MMClient <P>
 * ClassName: <P>
 * Created by zfang on 2017/3/2 16:58. <P>
 * Function: <P>
 * Modified: <P>
 */

public class LocationResult implements Parcelable {

    private String lng = null;//经    度
    private String lat = null;//纬    度
    private String speed = null;
    private String bearing = null;
    private String accuracy = null; //精度

    private String countryName = null;
    private String countryCode = null;
    private String provinceName = null;
    private String provinceCode = null;
    private String cityName = null;
    private String cityCode = null;
    private String districtName = null;
    private String districtCode = null;

    private String address = null;//定位地址
    private String poiName = null;//兴趣点名字
    private String locationTime = null;//定位时间

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getBearing() {
        return bearing;
    }

    public void setBearing(String bearing) {
        this.bearing = bearing;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public String getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(String locationTime) {
        this.locationTime = locationTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lng);
        dest.writeString(this.lat);
        dest.writeString(this.speed);
        dest.writeString(this.bearing);
        dest.writeString(this.accuracy);
        dest.writeString(this.countryName);
        dest.writeString(this.countryCode);
        dest.writeString(this.provinceName);
        dest.writeString(this.provinceCode);
        dest.writeString(this.cityName);
        dest.writeString(this.cityCode);
        dest.writeString(this.districtName);
        dest.writeString(this.districtCode);
        dest.writeString(this.address);
        dest.writeString(this.poiName);
        dest.writeString(this.locationTime);
    }

    public LocationResult() {
    }

    protected LocationResult(Parcel in) {
        this.lng = in.readString();
        this.lat = in.readString();
        this.speed = in.readString();
        this.bearing = in.readString();
        this.accuracy = in.readString();
        this.countryName = in.readString();
        this.countryCode = in.readString();
        this.provinceName = in.readString();
        this.provinceCode = in.readString();
        this.cityName = in.readString();
        this.cityCode = in.readString();
        this.districtName = in.readString();
        this.districtCode = in.readString();
        this.address = in.readString();
        this.poiName = in.readString();
        this.locationTime = in.readString();
    }

    public static final Creator<LocationResult> CREATOR = new Creator<LocationResult>() {
        @Override
        public LocationResult createFromParcel(Parcel source) {
            return new LocationResult(source);
        }

        @Override
        public LocationResult[] newArray(int size) {
            return new LocationResult[size];
        }
    };
}
