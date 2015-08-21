package com.liuy314.griddingsale.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.liuy314.griddingsale.R;

/**
 * Created by liuy314 on 2015/8/17.
 */
public class ShopMapActivity extends AppCompatActivity {

    private MapView mShopMapView;
    private BaiduMap mShopMap;
    private Toolbar mToolbar;
    private InfoWindow mShopInfoWindow;
    private Marker mMarker;

    private String mShopLatitude;
    private String mShopLongitude;
    private String mShopCode;
    private String mShopName;

    private boolean mIsPopup = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_shopmap);
        initializeComponents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShopMapView.onDestroy();
    }


    protected void onPause() {
        super.onPause();
        mShopMapView.onPause();
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mShopMapView.onResume();
    }

    /**
     * This is the fragment-orientated version of {@link #onResume()} that you
     * can override to perform operations in the Activity at the same point
     * where its fragments are resumed.  Be sure to always call through to
     * the super-class.
     */
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    private void initializeComponents() {
        initializeData();
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(mShopName);
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        initializeBaiduMap();
    }

    private void initializeData() {
        Intent intent = getIntent();
//        intent.putExtra("shopName", mShopDatas.get(position).getmShopName());
//        intent.putExtra("shopCode", mShopDatas.get(position).getmShopCode());
//        intent.putExtra("shopLatitude", mShopDatas.get(position).getmShopLatitude());
//        intent.putExtra("shopLongitude", mShopDatas.get(position).getmShopLongitude());
        mShopName = intent.getStringExtra("shopName");
        mShopCode = intent.getStringExtra("shopCode");
        mShopLatitude = intent.getStringExtra("shopLatitude");
        mShopLongitude = intent.getStringExtra("shopLongitude");
    }

    private void initializeBaiduMap() {
        mShopMapView = (MapView)findViewById(R.id.shop_mapview);
        mShopMap = mShopMapView.getMap();
        mShopMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mShopMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18));
        mShopMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(!mIsPopup) {
                    showPopupShopWindow(marker);
                }else {
                    mShopMap.hideInfoWindow();
                }
                mIsPopup = !mIsPopup;
                return false;
            }
        });
        LatLng point = new LatLng(Double.parseDouble(mShopLatitude), Double.parseDouble(mShopLongitude));
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.marker);
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        mMarker = (Marker)mShopMap.addOverlay(option);
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
        mShopMap.setMapStatus(update);
    }

    private void showPopupShopWindow(Marker marker) {
        LatLng pt = null;
        double latitude, longitude;
        final LatLng ll = marker.getPosition();
//        Point p = mShopMap.getProjection().toScreenLocation(ll);
        View view = LayoutInflater.from(this).inflate(R.layout.popup_marker_shop, null);

        mShopInfoWindow = new InfoWindow(view, ll, -77);
        mShopMap.showInfoWindow(mShopInfoWindow);
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        if(mIsPopup) {
            mShopMap.hideInfoWindow();
            mIsPopup = false;
            return;
        }
        super.onBackPressed();
    }
}
