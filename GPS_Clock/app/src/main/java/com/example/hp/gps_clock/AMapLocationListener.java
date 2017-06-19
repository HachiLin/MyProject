package com.example.hp.gps_clock;

import com.amap.api.location.AMapLocation;

/**
 * Created by HP on 2017/3/20.
 */
public interface AMapLocationListener {
    void onLocationChanged(AMapLocation aMapLocation);
}
