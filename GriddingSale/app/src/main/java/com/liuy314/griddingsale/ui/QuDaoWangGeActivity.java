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
import com.liuy314.griddingsale.adapter.QudaoWanggeAdapter;
import com.liuy314.griddingsale.data.QudaoWanggeData;
import com.liuy314.griddingsale.utl.BaseAcitivity;
import com.liuy314.griddingsale.utl.GriddingSaleApp;

import java.util.ArrayList;

/**
 * Created by liuy314 on 2015/8/13.
 */
public class QuDaoWangGeActivity extends BaseAcitivity {

    private final String MSG_STATUS = "msg";
    private final int DATA_LOADING_OK = 0;

    private RecyclerView mQudaoWanggeRecyclerView;
    private QudaoWanggeAdapter mQudaoWanggeAdapter;

    private ArrayList<QudaoWanggeData> mQudaoWanggeDatas;
    private String[] queryColumn = {"qudaowangge", "code_qudaowangge"};
    private String mCodeWangge;
    private String mWanggeName;
    private String mQueryQudaoWangge = "code_wangge LIKE ?";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (bundle.getInt(MSG_STATUS)) {
                case DATA_LOADING_OK:
                    mQudaoWanggeAdapter.notifyDataSetChanged();
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
        InitializeContent(R.layout.activity_qudaowangge);
        initializeComponents();
        new Thread(new LoadingQudaoWanggeDatas()).start();
    }

    private void initializeComponents() {
        initializeData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(mWanggeName);
        mQudaoWanggeRecyclerView = (RecyclerView)findViewById(R.id.qudaowangge_recycler_view);
        mQudaoWanggeRecyclerView.setHasFixedSize(true);
        mQudaoWanggeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mQudaoWanggeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mQudaoWanggeAdapter = new QudaoWanggeAdapter(this, mQudaoWanggeDatas);
        mQudaoWanggeRecyclerView.setAdapter(mQudaoWanggeAdapter);
    }

    private void initializeData() {
        mQudaoWanggeDatas = new ArrayList<QudaoWanggeData>();
        Intent intent = getIntent();
        mCodeWangge = intent.getStringExtra("WanggeCode");
        mWanggeName = intent.getStringExtra("WanggeName");
    }

    private void loadSQLDatabase() {
        String wangge = ((GriddingSaleApp)getApplication()).INDEX_QUDAOWANGGE;
        String codeWangge = ((GriddingSaleApp)getApplication()).INDEX_CODE_QUDAOWANGGE;
        SQLiteDatabase database = ((GriddingSaleApp) getApplication()).getDb();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(((GriddingSaleApp) getApplication()).TABLE_NAME);
        queryBuilder.setDistinct(true);

        Cursor cursor = queryBuilder.query(database, queryColumn, mQueryQudaoWangge, new String[] {mCodeWangge+""}, null, null, null);
        while(cursor.moveToNext()) {
            QudaoWanggeData data = new QudaoWanggeData();
//            int picId = 0;
            data.setmQudaoWanggeName(cursor.getString(cursor.getColumnIndex(wangge)));
            data.setmQudaoWanggeCode(cursor.getString(cursor.getColumnIndex(codeWangge)));
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
            mQudaoWanggeDatas.add(data);
        }
        cursor.close();
    }

    private class LoadingQudaoWanggeDatas implements Runnable {

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
