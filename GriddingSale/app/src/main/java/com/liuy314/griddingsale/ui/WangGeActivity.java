package com.liuy314.griddingsale.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.liuy314.griddingsale.R;
import com.liuy314.griddingsale.adapter.WanggeAdapter;
import com.liuy314.griddingsale.data.WanggeData;
import com.liuy314.griddingsale.utl.BaseAcitivity;
import com.liuy314.griddingsale.utl.GriddingSaleApp;

import java.util.ArrayList;

/**
 * Created by liuy314 on 2015/8/12.
 */
public class WangGeActivity extends BaseAcitivity {

    private final String MSG_STATUS = "msg";
    private final int DATA_LOADING_OK = 0;

    private RecyclerView mWanggeRecyclerView;
    private WanggeAdapter mWanggeAdapter;
    private TextView mFenjuNameTextView;

    private ArrayList<WanggeData> mWanggeDatas;
    private String[] queryColumn = {"wangge", "code_wangge"};
    private String mCodeFenju;
    private String mQueryWangge = "code_fenju LIKE ?";
    private String mFenjuName;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (bundle.getInt(MSG_STATUS)) {
                case DATA_LOADING_OK:
                    mWanggeAdapter.notifyDataSetChanged();
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
        InitializeContent(R.layout.activity_wangge);
        initializeComponents();
        new Thread(new LoadingWanggeDatasThread()).start();
    }

    private void initializeComponents() {
        initializeData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mFenjuNameTextView = (TextView)findViewById(R.id.fenju_name_textview);
        mFenjuNameTextView.setText(mFenjuName);
        mWanggeRecyclerView = (RecyclerView)findViewById(R.id.wangge_recycler_view);
        mWanggeRecyclerView.setHasFixedSize(true);
        mWanggeRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mWanggeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mWanggeAdapter = new WanggeAdapter(WangGeActivity.this, mWanggeDatas);
        mWanggeRecyclerView.setAdapter(mWanggeAdapter);
        getSupportActionBar().setTitle(mFenjuName);
    }

    private void initializeData() {
        mWanggeDatas = new ArrayList<WanggeData>();
        Intent intent = getIntent();
        mCodeFenju = intent.getStringExtra("FenjuCode");
        mFenjuName = intent.getStringExtra("FenjuName");
    }

    private void loadSQLDatabase() {
        String wangge = ((GriddingSaleApp)getApplication()).INDEX_WANGGE;
        String codeWangge = ((GriddingSaleApp)getApplication()).INDEX_CODE_WANGGE;
        SQLiteDatabase database = ((GriddingSaleApp) getApplication()).getDb();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(((GriddingSaleApp) getApplication()).TABLE_NAME);
        queryBuilder.setDistinct(true);

        Cursor cursor = queryBuilder.query(database, queryColumn, mQueryWangge, new String[] {mCodeFenju+""}, null, null, null);
        while(cursor.moveToNext()) {
            WanggeData data = new WanggeData();
//            int picId = 0;
            data.setmWanggeName(cursor.getString(cursor.getColumnIndex(wangge)));
            data.setmWanggeCode(cursor.getString(cursor.getColumnIndex(codeWangge)));
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
            mWanggeDatas.add(data);
        }
        cursor.close();
    }

    private class LoadingWanggeDatasThread implements Runnable {

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
