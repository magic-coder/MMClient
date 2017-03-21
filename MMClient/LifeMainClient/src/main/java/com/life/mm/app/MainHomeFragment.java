package com.life.mm.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureSupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.cloud.CloudItem;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.nearby.NearbyInfo;
import com.amap.api.services.nearby.NearbySearch;
import com.amap.api.services.nearby.NearbySearchFunctionType;
import com.amap.api.services.nearby.NearbySearchResult;
import com.amap.api.services.nearby.UploadInfo;
import com.amap.api.services.nearby.UploadInfoCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.google.gson.Gson;
import com.life.mm.MMMainActivity;
import com.life.mm.R;
import com.life.mm.Test;
import com.life.mm.app.presenter.MainPresenter;
import com.life.mm.common.log.MMLogManager;
import com.life.mm.common.utils.DateUtil;
import com.life.mm.framework.app.MMApplication;
import com.life.mm.framework.app.base.activity.BaseActivity;
import com.life.mm.framework.app.base.fragment.BaseFragment;
import com.life.mm.framework.bean.YunTuDataBean;
import com.life.mm.framework.map.CloudOverlay;
import com.life.mm.framework.map.LocationManager;
import com.life.mm.framework.map.LocationResult;
import com.life.mm.framework.map.LocationResultListener;
import com.life.mm.framework.utils.AMapUtil;
import com.life.mm.framework.utils.MMUtils;
import com.life.mm.framework.utils.SensorEventHelper;
import com.life.mm.framework.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 地图容器页面,里面包含地图页面
 */

public class MainHomeFragment extends BaseFragment<MainPresenter> {

    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private static final int MAP_LEVEL = 19;

    private MenuInflater menuInflater = null;
    private Menu mMenu = null;
    private Toolbar toolbar = null;

    private float currentMapLevel = MAP_LEVEL;
    private AMap aMap = null;
    private Marker mLocMarker;
    private SensorEventHelper mSensorHelper;
    private boolean mFirstFix = false;
    private Circle mCircle;
    private OkHttpClient okHttpClient = null;
    private LocationManager locationManager = null;
    private ProgressDialog progDialog = null;
    private String mTableID = "58b7b9a22376c11121054209";
    private String mKeyWord = ""; // 搜索关键字
    private CloudSearch.Query mQuery;
    private Marker mCloudIDMarer;
    private CloudOverlay mPoiCloudOverlay;
    private CloudSearch mCloudSearch = null;
    private List<CloudItem> mCloudItems;
    private String mLocalCityName = "广州";
    private ArrayList<CloudItem> mCloudItemSearyByIds = new ArrayList<CloudItem>();
    private int searchDistance = 5 * 1000;//5km
    private LatLonPoint mCenterPoint = null;
    private NearbySearch mNearbySearch = null;
    private List<Marker> nearbyMarkerList = new ArrayList<>();
    private boolean uploadInfo = false;
    private GeocodeSearch geocodeSearch = null;

    private MMMainActivity.OnMenuInflateListener onMenuInflateListener = new MMMainActivity.OnMenuInflateListener() {
        @Override
        public void onMenuInflated(Menu menu) {
            mMenu = menu;
            initToolbar();
        }
    };

    private NearbySearch.NearbyListener nearbyListener = new NearbySearch.NearbyListener() {
        @Override
        public void onUserInfoCleared(int i) {
            MMLogManager.logD(TAG + ", onUserInfoCleared, i = " + i);
            if (1000 == i) {
                ToastUtil.show(mContext, mResources.getString(R.string.main_home_clear_my_location_success));
            } else {
                ToastUtil.show(mContext, mResources.getString(R.string.main_home_clear_my_location_failure));
            }
        }

        @Override
        public void onNearbyInfoSearched(NearbySearchResult nearbySearchResult, int resultCode) {
            MMLogManager.logD(TAG + ", onNearbyInfoSearched, resultCode = " + resultCode);
            //搜索周边附近用户回调处理
            if (resultCode == 1000) {
                nearbyMarkerList.clear();
                if (nearbySearchResult != null && nearbySearchResult.getNearbyInfoList() != null
                        && nearbySearchResult.getNearbyInfoList().size() > 0) {
                    List<NearbyInfo> nearbyInfoList = nearbySearchResult.getNearbyInfoList();
                    for (NearbyInfo nearbyInfo : nearbyInfoList) {
                        MMLogManager.logD(TAG + ", nearbyInfo = [ userID = " + nearbyInfo.getUserID()
                                + ", distances = " + nearbyInfo.getDistance()
                                + ", drivingDistance = " + nearbyInfo.getDrivingDistance()
                                + ", point = " + nearbyInfo.getPoint()
                                + ", timeStamp = " + DateUtil.timeStamp2DateStr(nearbyInfo.getTimeStamp()));
                        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.map_icon_default);
                        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
                        MarkerOptions options = new MarkerOptions();
                        options.icon(des);
                        options.anchor(0.5f, 0.5f);
                        options.position(new LatLng(nearbyInfo.getPoint().getLatitude(), nearbyInfo.getPoint().getLongitude()));

                        Marker marker = aMap.addMarker(options);
                        nearbyMarkerList.add(marker);
                        marker.setTitle("附近搜索测试");
                    }

                    if (MMUtils.isAvaliableList(nearbyMarkerList)) {
                        if (null == geocodeSearch) {
                            geocodeSearch = new GeocodeSearch(mContext);
                            geocodeSearch.setOnGeocodeSearchListener(geocodeSearchListener);
                        }
                        if (1 == nearbyMarkerList.size()) {
                            LatLonPoint latLonPoint = new LatLonPoint(nearbyMarkerList.get(0).getPosition().latitude, nearbyMarkerList.get(0).getPosition().longitude);
                            RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
                            geocodeSearch.getFromLocationAsyn(query);
                        }
                    }
                } else {
                    ToastUtil.show(mContext, mResources.getString(R.string.main_home_tips_nearby_search_empty));
                }
            }
        }

        @Override
        public void onNearbyInfoUploaded(int i) {
            MMLogManager.logD(TAG + ", onNearbyInfoUploaded, i = " + i);
            if (1000 == i) {
                ToastUtil.show(mContext, mResources.getString(R.string.main_home_upload_location_success));
            } else {
                ToastUtil.show(mContext, mResources.getString(R.string.main_home_upload_location_failure));
            }
        }
    };

    private GeocodeSearch.OnGeocodeSearchListener geocodeSearchListener = new GeocodeSearch.OnGeocodeSearchListener() {
        @Override
        public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
            if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                if (result != null && result.getRegeocodeAddress() != null
                        && result.getRegeocodeAddress().getFormatAddress() != null) {
                    nearbyMarkerList.get(0).setTitle(result.getRegeocodeAddress().getFormatAddress());
                } else {
                    ToastUtil.show(mContext, R.string.no_result);
                }
            }
        }

        @Override
        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

        }
    };

    private LocationResultListener locationResultListener = new LocationResultListener() {
        @Override
        public void onLocationResult(LocationResult result) {
            LatLng location = new LatLng(Double.parseDouble(result.getLat()), Double.parseDouble(result.getLng()));
            mCenterPoint = new LatLonPoint(Double.parseDouble(result.getLat()), Double.parseDouble(result.getLng()));
            if (!mFirstFix) {
                mFirstFix = true;
                addCircle(location, Double.parseDouble(result.getAccuracy()));//添加定位精度圆
                addMarker(location, result.getAddress());//添加定位图标
                mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
            } else {
                mCircle.setCenter(location);
                mCircle.setRadius(Double.parseDouble(result.getAccuracy()));
                mLocMarker.setPosition(location);
            }
            changeCamera(true, 500, CameraUpdateFactory.newLatLngZoom(location, currentMapLevel), null);
            searchByBound();
            if (!uploadInfo) {
                uploadInfo = true;
                //upLoadNearbyInfo();
                upload2YunTu(result);
            }
        }
    };

    private CloudSearch.OnCloudSearchListener cloudSearchListener = new CloudSearch.OnCloudSearchListener() {

        @Override
        public void onCloudItemDetailSearched(CloudItemDetail item, int rCode) {
            if (rCode == AMapException.CODE_AMAP_SUCCESS && item != null) {
                if (mCloudIDMarer != null) {
                    mCloudIDMarer.destroy();
                }
                aMap.clear();
                if (null != mCircle) {
                    addCircle(mCircle.getCenter(), mCircle.getRadius());
                }
                if (null != mLocMarker) {
                    addMarker(mLocMarker.getPosition(), mLocMarker.getTitle());
                }
                LatLng position = AMapUtil.convertToLatLng(item.getLatLonPoint());
                //aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(position, 18, 0, 30)));
                changeCamera(true, 500, CameraUpdateFactory.newCameraPosition(new CameraPosition(position, currentMapLevel, 0, 30)), null);
                mCloudIDMarer = aMap.addMarker(new MarkerOptions()
                        .position(position)
                        .title(item.getTitle())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mCloudItemSearyByIds.add(item);
                Log.d(TAG, "_id" + item.getID());
                Log.d(TAG, "_location" + item.getLatLonPoint().toString());
                Log.d(TAG, "_name" + item.getTitle());
                Log.d(TAG, "_address" + item.getSnippet());
                Log.d(TAG, "_caretetime" + item.getCreatetime());
                Log.d(TAG, "_updatetime" + item.getUpdatetime());
                Log.d(TAG, "_distance" + item.getDistance());
                Iterator iter = item.getCustomfield().entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    Log.d(TAG, key + "   " + val);
                }
            } else {
                ToastUtil.showerror(mContext, rCode);
            }
        }

        /**
         * 检索结果回调
         * @param result
         * @param rCode
         */
        @Override
        public void onCloudSearched(CloudResult result, int rCode) {
            dissmissProgressDialog();

            if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                if (result != null && result.getQuery() != null) {
                    if (result.getQuery().equals(mQuery)) {
                        mCloudItems = result.getClouds();
                        clearMap();
                        if (null != mCircle) {
                            addCircle(mCircle.getCenter(), mCircle.getRadius());
                        }
                        if (null != mLocMarker) {
                            addMarker(mLocMarker.getPosition(), mLocMarker.getTitle());
                        }

                        if (mCloudItems != null && mCloudItems.size() > 0) {
                            mPoiCloudOverlay = new CloudOverlay(aMap, mCloudItems);
                            mPoiCloudOverlay.removeFromMap();
                            mPoiCloudOverlay.addToMap();
                            for (CloudItem item : mCloudItems) {
                                mCloudItemSearyByIds.add(item);
                                Log.d(TAG, "_id " + item.getID());
                                Log.d(TAG, "_location " + item.getLatLonPoint().toString());
                                Log.d(TAG, "_name " + item.getTitle());
                                Log.d(TAG, "_address " + item.getSnippet());
                                Log.d(TAG, "_caretetime " + item.getCreatetime());
                                Log.d(TAG, "_updatetime " + item.getUpdatetime());
                                Log.d(TAG, "_distance " + item.getDistance());
                                Iterator iter = item.getCustomfield().entrySet().iterator();
                                while (iter.hasNext()) {
                                    Map.Entry entry = (Map.Entry) iter.next();
                                    Object key = entry.getKey();
                                    Object val = entry.getValue();
                                    Log.d(TAG, key + "   " + val);
                                }
                            }
                            if (mQuery.getBound().getShape().equals(CloudSearch.SearchBound.BOUND_SHAPE)) {// 圆形
                                aMap.addCircle(new CircleOptions()
                                        .center(new LatLng(mCenterPoint.getLatitude(), mCenterPoint.getLongitude())).radius(searchDistance)
                                        .strokeColor(Color.RED)
                                        .fillColor(Color.TRANSPARENT)
                                        .strokeWidth(5));
                                changeCamera(true, 500, CameraUpdateFactory.newLatLngZoom(new LatLng(mCenterPoint.getLatitude(), mCenterPoint.getLongitude()), currentMapLevel), null);
                            } else  if ((mQuery.getBound().getShape().equals(CloudSearch.SearchBound.LOCAL_SHAPE))) {
                                mPoiCloudOverlay.zoomToSpan();
                            }
                        } else {
                            ToastUtil.show(mContext, R.string.no_result);
                        }
                    }
                } else {
                    ToastUtil.show(mContext, R.string.no_result);
                }
            } else {
                ToastUtil.showerror(mContext, rCode);
            }
        }
    };
    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocodeSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }
    private void clearMap() {
        aMap.clear();
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog(String message) {
        if (progDialog == null)
            progDialog = new ProgressDialog(mContext);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索:\n" + mTableID + "\n搜索方式:" + message);
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 本地检索
     */
    public void searchByLocal() {
        showProgressDialog("searchByLocal");
        mCloudItemSearyByIds.clear();
        CloudSearch.SearchBound bound = new CloudSearch.SearchBound(mLocalCityName);
        try {
            mQuery = new CloudSearch.Query(mTableID, mKeyWord, bound);
            mCloudSearch.searchCloudAsyn(mQuery);
        } catch (AMapException e) {
            e.printStackTrace();
        }
    }

    /**
     * 周边检索
     */
    public void searchByBound() {
        mCloudItemSearyByIds.clear();
        CloudSearch.SearchBound bound = new CloudSearch.SearchBound(new LatLonPoint(mCenterPoint.getLatitude(), mCenterPoint.getLongitude()), searchDistance);
        try {
            mQuery = new CloudSearch.Query(mTableID, mKeyWord, bound);
            mQuery.setPageSize(10);
            CloudSearch.Sortingrules sorting = new CloudSearch.Sortingrules("_id", false);
            mQuery.setSortingrules(sorting);
            mCloudSearch.searchCloudAsyn(mQuery);// 异步搜索
        } catch (AMapException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(boolean animated, long animateTime, CameraUpdate update, AMap.CancelableCallback callback) {
        if (animated) {
            aMap.animateCamera(update, animateTime, callback);
        } else {
            aMap.moveCamera(update);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_home;
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void initView(View rootView) {
        initAmap();
        mSensorHelper = new SensorEventHelper(mContext);
        mSensorHelper.registerSensorListener();
    }

    private void initAmap() {
        aMap = ((TextureSupportMapFragment) fragmentManager.findFragmentById(R.id.main_home_mapview)).getMap();
        initMapLoadListener();
        initMarkerListener();
        setCameraChangeListener();
    }

    private void setCameraChangeListener() {
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                currentMapLevel = cameraPosition.zoom;
            }
        });
    }

    private void initMarkerListener() {
        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                setInfoWindow(marker);
            }
        });
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                setInfoWindow(marker);
                return true;
            }
        });
    }


    private void setInfoWindow(Marker marker) {
        if (marker.isInfoWindowEnable()) {
            if (marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
            } else {
                marker.showInfoWindow();
                changeCamera(true, 500, CameraUpdateFactory.newLatLngZoom(marker.getPosition(), currentMapLevel), null);
            }
        }
    }

    private void initMapLoadListener() {
        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                MMLogManager.logD(TAG + ", onMapLoaded");
                initLocationListener();
                initNearbySearch();
                //searchByLocal();
            }
        });
    }

    private void initNearbySearch() {
        mNearbySearch = NearbySearch.getInstance(mContext);
        mNearbySearch.addNearbyListener(nearbyListener);
    }


    /**
     * 连续上传位置信息
     */
    private void upLoadNearbyInfo() {
        mNearbySearch.startUploadNearbyInfoAuto(new UploadInfoCallback() {
            @Override
            public UploadInfo OnUploadInfoCallback() {
                UploadInfo uploadInfo = new UploadInfo();
                uploadInfo.setCoordType(NearbySearch.AMAP);//坐标类型
                uploadInfo.setPoint(mCenterPoint);
                uploadInfo.setUserID("13059541309");
                return uploadInfo;
            }
        }, 5 * 60 * 1000);
    }

    /**
     * 单次上传位置信息
     */
    private void uploadNearbyInfoOnce() {
        UploadInfo loadInfo = new UploadInfo();
        loadInfo.setCoordType(NearbySearch.AMAP);
        loadInfo.setPoint(mCenterPoint);
        loadInfo.setUserID("13710762294");
        NearbySearch.getInstance(mContext).uploadNearbyInfoAsyn(loadInfo);
    }

    private void clearMyNearbyLocationInfo() {
        mNearbySearch.setUserID("13059541309");
        NearbySearch.getInstance(mContext).clearUserInfoAsyn();
    }
    private void upload2YunTu(LocationResult result) {
        if (null == okHttpClient) {
            okHttpClient = MMApplication.getInstance().getOkHttpClient();
        }
        Gson gson = new Gson();

        YunTuDataBean data = new YunTuDataBean();
        data.set_name("test");
        data.set_location(result.getLng() + "," + result.getLat());
        data.setCoordtype("autonavi");
        data.set_address(result.getAddress());
        String dataStr = gson.toJson(data);
        final RequestBody requestBody = new FormBody.Builder()
                .add("key", "0a5014430312611580c3f931bc17c5c7")
                .add("tableid", mTableID)
                .add("loctype", "1")
                .add("data", dataStr)
                .build();
        Request request = new Request.Builder()
                .url("http://yuntuapi.amap.com/datamanage/data/create")
                .post(requestBody)
                .build();
        MMLogManager.logD(TAG +", dataStr = " + dataStr);
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                MMLogManager.logD(TAG + ", upload2YunTu, result = " + response.toString() + ",\n body = " + response.body().string());
            }
        });
    }

    private void initLocationListener() {
        mCloudSearch = new CloudSearch(mContext);
        locationManager = MMApplication.getInstance().getLocationManager();
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        locationManager.addListener(locationResultListener);
        locationManager.startLocation();
        mCloudSearch.setOnCloudSearchListener(cloudSearchListener);
    }
    /**
     * 添加Circle
     * @param latlng  坐标
     * @param radius  半径
     */
    private void addCircle(LatLng latlng, double radius) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
    }

    /**
     * 添加Marker
     */
    private void addMarker(LatLng latlng, String title) {
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.navi_map_gps_locked);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = aMap.addMarker(options);
        mLocMarker.setTitle(title);
    }
    private ExecutorService executorService = null;
    private ScheduledThreadPoolExecutor executor = null;

    private void initToolbar() {
        if (null != mContext) {
            this.toolbar = ((BaseActivity) mContext).getToolbar();
            this.menuInflater = ((BaseActivity) mContext).getMenuInflater();
        }
        if (null != toolbar) {
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.main_home_action_student:
                            MMLogManager.logD(TAG + ", 点击了学生");
                            doNearbySearch();
                            break;

                        case R.id.main_home_action_teacher:
                            if (null == executorService) {
                                executorService = Executors.newSingleThreadExecutor();
                                executor = new ScheduledThreadPoolExecutor(111);
                            }
                            executor.submit(new Test());
                            toolbar.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    MMLogManager.logE("new obj --begin, thread = " + Thread.currentThread().getId());
                                    Test aTest = new Test();
                                    MMLogManager.logE("new obj --end");
                                }
                            }, 5000);
                            break;

                        case R.id.main_home_action_doctor:
                            // 测试 SDK 是否正常工作的代码
                            AVObject testObject = new AVObject("TestObject");
                            testObject.put("words","Hello World!");
                            testObject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if(e == null){
                                        Log.d("saved","success!");
                                    }
                                }
                            });
                            MMLogManager.logD(TAG + ", 点击了医生");
                            break;

                        case R.id.main_home_action_upload:
                            uploadNearbyInfoOnce();
                            break;

                        case R.id.main_home_action_clear_my_location:
                            clearMyNearbyLocationInfo();
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
        }

        if (null != mMenu && null != menuInflater) {
            menuInflater.inflate(R.menu.main_home_menu, mMenu);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MMLogManager.logD(TAG + ", onCreateOptionsMenu");
        this.mMenu = menu;
        this.menuInflater = inflater;
        this.mMenu.clear();
        inflater.inflate(R.menu.main_home_menu, menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MMMainActivity) mContext).registMenuInflate(onMenuInflateListener);
        initToolbar();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        initToolbar();
    }

    @Override
    protected void onInVisible() {
        super.onInVisible();
        if (null == toolbar && null != mContext) {
            this.toolbar = ((BaseActivity) mContext).getToolbar();
            this.mMenu = ((MMMainActivity) mContext).getmMenu();
        }
        if (null != mMenu) {
            mMenu.clear();
        }
    }

    @Override
    protected void doLazyLoad() {

    }

    private void doNearbySearch() {
        if (null == mCenterPoint) {
            return;
        }
        //设置搜索条件
        NearbySearch.NearbyQuery query = new NearbySearch.NearbyQuery();
        query.setCenterPoint(mCenterPoint);
        query.setCoordType(NearbySearch.AMAP);
        query.setRadius(searchDistance);
        query.setTimeRange(10000);
        query.setType(NearbySearchFunctionType.DRIVING_DISTANCE_SEARCH);
        NearbySearch.getInstance(mContext).searchNearbyInfoAsyn(query);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        } else {
            mSensorHelper = new SensorEventHelper(mContext);
            mSensorHelper.registerSensorListener();
            if (mSensorHelper.getCurrentMarker() == null && mLocMarker != null) {
                mSensorHelper.setCurrentMarker(mLocMarker);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
            mSensorHelper.setCurrentMarker(null);
            mSensorHelper = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MMMainActivity) mContext).removeMenuInflate(onMenuInflateListener);
        locationManager.removeListener(locationResultListener);
        locationManager.stopLocation();
        locationManager.destroyLocation();
        NearbySearch.destroy();
    }
}
