package com.example.hp.gps_clock;

import android.app.Activity;
import android.app.Service;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;


public class MainActivity extends AppCompatActivity implements LocationSource,AMapLocationListener, com.amap.api.location.AMapLocationListener {
    //显示地图需要的变量
    private MapView mapView;//地图控件
    private AMap aMap;//地图对象
    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private OnLocationChangedListener mListener = null;//定位监听器
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc =true;
    private boolean isSet=false;
   //标记离目的地的距离
    private int a=0;
   //设置闹钟音乐
    private MediaPlayer mediaPlayer01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //显示地图
        mapView = (MapView) findViewById(R.id.map);
        //必须要写
        mapView.onCreate(savedInstanceState);
        //获取地图对象
        aMap = mapView.getMap();
        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        //设置定位监听
        aMap.setLocationSource(this);
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        aMap.setMyLocationStyle(myLocationStyle);
        //开始定位
        initLoc();

    }

    private MarkerOptions getMarkerOptions(LatLng point) {
        //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        //图标
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.hello));
        //位置
        options.position(new LatLng(point.latitude, point.longitude));
        options.period(60);
        options.snippet("目的地");
        return options;
    }


    //定位
    private void initLoc() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    //定位回调函数
    @Override
    public void onLocationChanged(final AMapLocation amapLocation) {

        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(amapLocation);
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;

                    //设置位置距离
                            Button bt;
                            bt = (Button)findViewById(R.id.button);
                            bt.setOnClickListener(new View.OnClickListener(){
                                @Override
                                //监听点击事件
                                public void onClick(View v)
                                {
                                    EditText word =(EditText) findViewById(R.id.e);
                                    String thisword = word.getText().toString();
                                    a=Integer.parseInt(thisword);
                                    Toast.makeText(getApplicationContext(), "设置距离成功", Toast.LENGTH_LONG).show();
                        }
                    });

                    Button button1=(Button)findViewById(R.id.button1);
                    Button button2=(Button)findViewById(R.id.button2);
                    button1.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            switch(v.getId()){
                                case R.id.button1:{
                                    isSet=true;
                                    if(isSet){
                                        //Toast类是能够在应用上浮点消息提高给用户看。
                                        Toast.makeText(getApplicationContext(), "点击按钮设置闹钟", Toast.LENGTH_LONG).show();
                                        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                                            @Override
                                            public void onMapClick(LatLng latLng) {
                                                double latitude = latLng.latitude;
                                                double longitude = latLng.longitude;

                                                //获取地图长度
                                                LatLng point=new LatLng(latitude,longitude);
                                                LatLng point2=new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
                                                aMap.addMarker(getMarkerOptions(point));;
                                                float distance = AMapUtils.calculateLineDistance(point,point2);
                                                Toast.makeText(getApplicationContext(), "当前距离"+distance, Toast.LENGTH_LONG).show();
                                                if(distance<=a){
                                                    //设置响铃音乐
                                                //  MediaPlayer mediaPlayer01;
                                                    mediaPlayer01 = MediaPlayer.create(getBaseContext(), R.raw.ring);
                                                    mediaPlayer01.start();
                                                    //设置震动
                                                    Vibrator vb=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
                                                    vb.vibrate(5000);}
                                            }});}}
                                break;
                                default:
                                    break;
                            }
                        }
                    });
                    button2.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            switch(v.getId()){
                                case R.id.button2:{
                                   //aMap.clear();
                                    isSet=false;
                                    //设置响铃音乐
                                    mediaPlayer01.release();
                                    break;}
                                default:
                                    break;
                            }
                        }
                    });

                }
            } else {
                Log.e("AmapError", "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());

                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
            }
        }
    }
    //激活定位
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
    }
    //停止定位
    @Override
    public void deactivate() {
        mListener = null;
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
   }


