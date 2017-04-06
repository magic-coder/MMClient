package com.life.mm.framework.map;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.reflect.TypeToken;
import com.life.mm.common.log.MMLogManager;
import com.life.mm.common.utils.GsonUtil;
import com.life.mm.framework.app.MMApplication;
import com.life.mm.framework.utils.MMUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName:MMClient <P>
 * ClassName: <P>
 * Created by zfang on 2017/3/2 16:45. <P>
 * Function: <P>
 * Modified: <P>
 */

public class LocationManager {

    private String TAG = getClass().getSimpleName();

    public final static String locationKey = "locationKey";
    static private LocationManager instance = null;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private List<LocationResultListener> listenerList = null;
    private LocationResult locationResult = null;

    public static LocationManager getInstance(Context context) {
        if (null == instance) {
            synchronized (LocationManager.class) {
                if (null == instance) {
                    instance = new LocationManager(context);
                    MMLogManager.logD("LocationManager, New Instance");
                }
            }
        } else {
            MMLogManager.logD("LocationManager, has a Instance");
        }
        return instance;
    }

    private LocationManager(Context context) {
        this.listenerList = new ArrayList<>();
        this.locationOption = getDefaultOption();
        initLocation(context);
    }

    public void addListener(LocationResultListener listener) {
        this.listenerList.add(listener);
    }

    public void removeListener(LocationResultListener listener) {
        this.listenerList.remove(listener);
    }

    public LocationResult getLocationResult() {
        if (null == locationResult) {
            try {
                locationResult = (LocationResult) GsonUtil.toObject(MMApplication.getInstance(), new TypeToken<LocationResult>(){}.getType(), locationKey, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return locationResult;
    }

    /**
     * 初始化定位
     *
     */
    private void initLocation(Context context) {
        //初始化client
        locationClient = new AMapLocationClient(context);
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     *
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2 * 60 * 1000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    /**
     * 定位监听
     */
    private AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc && 0 == loc.getErrorCode()) {
                //解析定位结果
                locationResult = toLocationBean(loc);
                GsonUtil.saveToJson(MMApplication.getInstance(), locationResult, locationKey);
                if (MMUtils.isAvaliableList(listenerList)) {
                    for (LocationResultListener listener : listenerList) {
                        listener.onLocationResult(locationResult);
                    }
                }
            } else {
                MMLogManager.logE(TAG + ", 定位失败");
            }
        }
    };

    private LocationResult toLocationBean(AMapLocation aMapLocation) {
        LocationResult result = new LocationResult();

        result.setLng(aMapLocation.getLongitude() + "");
        result.setLat(aMapLocation.getLatitude() + "");
        result.setSpeed(aMapLocation.getSpeed() + "");
        result.setBearing(aMapLocation.getBearing() + "");
        result.setAccuracy(aMapLocation.getAccuracy() + "");

        result.setCountryName(aMapLocation.getCountry());
        result.setPoiName(aMapLocation.getProvince());
        result.setCityName(aMapLocation.getCity());
        result.setCityCode(aMapLocation.getCityCode());
        result.setDistrictName(aMapLocation.getDistrict());

        result.setAddress(aMapLocation.getAddress());
        result.setPoiName(aMapLocation.getPoiName());
        result.setLocationTime(aMapLocation.getTime() + "");
        return result;
    }

    // 根据控件的选择，重新设置定位参数
    private void resetOption() {//单位定位
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(true);
        // 设置是否单次定位
        locationOption.setOnceLocation(false);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(false);
        //设置是否使用传感器
        locationOption.setSensorEnable(true);
        //设置是否开启wifi扫描，如果设置为false时同时会停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        locationOption.setInterval(2 * 1000);
        // 设置网络请求超时时间
        locationOption.setHttpTimeOut(1500);
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        //根据控件的选择，重新设置定位参数
        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     */
    public void stopLocation() {
        // 停止定位
        if (null != locationClient) {
            locationClient.stopLocation();
        }
    }

    /**
     * 销毁定位
     *
     */
    public void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
        instance = null;
    }
}
