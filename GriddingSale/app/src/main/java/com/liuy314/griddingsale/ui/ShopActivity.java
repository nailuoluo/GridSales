package com.liuy314.griddingsale.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liuy314.griddingsale.R;
import com.liuy314.griddingsale.adapter.ShopAdapter;
import com.liuy314.griddingsale.data.ShopData;
import com.liuy314.griddingsale.utl.BaseAcitivity;
import com.liuy314.griddingsale.utl.GriddingSaleApp;

import java.util.ArrayList;

/**
 * Created by liuy314 on 2015/8/13.
 */
public class ShopActivity extends BaseAcitivity {

    private final String MSG_STATUS = "msg";
    private final int DATA_LOADING_OK = 0;

    private RecyclerView mShopRecyclerView;
    private ShopAdapter mShopAdapter;

    private ArrayList<ShopData> mShopDatas;
    private String[] queryColumn = {"shop", "code_shop", "latitude", "longitude"};
    private String mCodeQudaoWangge;
    private String mQueryShop = "code_qudaowangge LIKE ?";
    private String mShopName;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (bundle.getInt(MSG_STATUS)) {
                case DATA_LOADING_OK:
                    mShopAdapter.notifyDataSetChanged();
                    mLoadingDBProgressBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeContent(R.layout.activity_shop);
        initializeComponents();
        new Thread(new LoadingShopDatasThread()).start();
    }

    private void initializeComponents() {
        initializeData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(mShopName);
        mShopRecyclerView = (RecyclerView)findViewById(R.id.shop_recycler_view);
        mShopRecyclerView.setHasFixedSize(true);
        mShopRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mShopRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mShopAdapter = new ShopAdapter(ShopActivity.this, mShopDatas);
        mShopRecyclerView.setAdapter(mShopAdapter);

    }

    private void initializeData() {
        Intent intent = getIntent();
        mShopName = intent.getStringExtra("QudaoWanggeName");
        mCodeQudaoWangge = intent.getStringExtra("QudaoWanggeCode");
        mShopDatas = new ArrayList<ShopData>();

    }

    private void loadSQLDatabase() {
        String shop = ((GriddingSaleApp)getApplication()).INDEX_SHOP;
        String codeShop = ((GriddingSaleApp)getApplication()).INDEX_CODE_SHOP;
        String latitude = ((GriddingSaleApp)getApplication()).INDEX_LATITUDE;
        String longitude = ((GriddingSaleApp)getApplication()).INDEX_LONGITUDE;
        SQLiteDatabase database = ((GriddingSaleApp) getApplication()).getDb();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(((GriddingSaleApp) getApplication()).TABLE_NAME);
        queryBuilder.setDistinct(true);

        Cursor cursor = queryBuilder.query(database, queryColumn, mQueryShop, new String[] {mCodeQudaoWangge+""}, null, null, null);
        while(cursor.moveToNext()) {
            ShopData data = new ShopData();
//            int picId = 0;
            data.setmShopName(cursor.getString(cursor.getColumnIndex(shop)));
            data.setmShopCode(cursor.getString(cursor.getColumnIndex(codeShop)));
            data.setmShopLatitude(cursor.getString(cursor.getColumnIndex(latitude)));
            data.setmShopLongitude(cursor.getString(cursor.getColumnIndex(longitude)));
//            switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(codeWangge)))) {
//                case GriddingSaleApp.CODE_FENJU_XINSHUN:
//                    picId = R.mipmap.fenju_pic_xinshun;
//                    break;
//                case GriddingSaleApp.CODE_FENJU_KONGGANG:
//                    picId = R.mipmap.fenju_pic_konggang;
//                    break;
//                case GriddingSaleApp.CODE_FENJU_LINHE:
//                    picId = R.mipmap.fenju_pic_linhe;
//                    break;
//                case GriddingSaleApp.CODE_FENJU_NIULANSHAN:
//                    picId = R.mipmap.fenju_pic_niulanshan;
//                    break;
//                case GriddingSaleApp.CODE_FENJU_YANGZHEN:
//                    picId = R.mipmap.fenju_pic_yangzhen;
//                    break;
//                case GriddingSaleApp.CODE_FENJU_MULIN:
//                    picId = R.mipmap.fenju_pic_mulin;
//                    break;
//                default:
//                    break;
//            }
//            data.setmFenjuPic(picId);
            mShopDatas.add(data);
        }
        cursor.close();
    }

    private class LoadingShopDatasThread implements Runnable {

        @Override
        public void run() {
            loadSQLDatabase();
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt(MSG_STATUS, DATA_LOADING_OK);
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }
    }
}
