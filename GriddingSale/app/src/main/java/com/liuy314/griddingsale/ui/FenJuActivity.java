package com.liuy314.griddingsale.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.liuy314.griddingsale.R;
import com.liuy314.griddingsale.adapter.FenjuAdapter;
import com.liuy314.griddingsale.data.FenjuData;
import com.liuy314.griddingsale.utl.BaseAcitivity;
import com.liuy314.griddingsale.utl.GriddingSaleApp;

import java.util.ArrayList;

/**
 * Created by liuy314 on 2015/8/11.
 */
public class FenJuActivity extends BaseAcitivity {

    private final String MSG_STATUS = "msg";
    private final int DATA_LOADING_OK = 0;
//    private final int DATA_ADDED = 1;
//    private final int DATA_SET_COMPLETE = 2;

    private RecyclerView mFenjuRecyclerView;
    private FenjuAdapter mFenjuAdapter;

    private ArrayList<FenjuData> mFenjuDatas;
    private String[] queryColumn = {"fenju", "code_fenju"};
//    private Cursor mCursor;
//    private String mIndexFenju;
//    private String mIndexCodeFenju;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (bundle.getInt(MSG_STATUS)) {
                case DATA_LOADING_OK:
                    mFenjuAdapter.notifyDataSetChanged();
                    mLoadingDBProgressBar.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeContent(R.layout.activity_fenju);
        initializeComponents();
        new Thread(new LoadingFenjuDataThread()).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                showAboutDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeComponents() {
        initializeData();
        mFenjuRecyclerView = (RecyclerView)findViewById(R.id.fenju_recycler_view);
        mFenjuRecyclerView.setHasFixedSize(true);
        mFenjuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFenjuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFenjuAdapter = new FenjuAdapter(FenJuActivity.this, mFenjuDatas);
        mFenjuRecyclerView.setAdapter(mFenjuAdapter);
    }

    private void initializeData() {
        mFenjuDatas = new ArrayList<FenjuData>();
    }

    private void showAboutDialog() {
        new AboutDialog().show(getFragmentManager(), "About");
    }

    private void loadSQLDatabase() {
        String fenju = ((GriddingSaleApp)getApplication()).INDEX_FENJU;
        String codeFenju = ((GriddingSaleApp)getApplication()).INDEX_CODE_FENJU;
        SQLiteDatabase database = ((GriddingSaleApp) getApplication()).getDb();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(((GriddingSaleApp) getApplication()).TABLE_NAME);
        queryBuilder.setDistinct(true);

        Cursor cursor = queryBuilder.query(database, queryColumn, null, null, null, null, null);
        while(cursor.moveToNext()) {
            FenjuData data = new FenjuData();
            int picId = 0;
            data.setmFenjuName(cursor.getString(cursor.getColumnIndex(fenju)));
            data.setmFenjuCode(cursor.getString(cursor.getColumnIndex(codeFenju)));
            switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(codeFenju)))) {
                case GriddingSaleApp.CODE_FENJU_XINSHUN:
                    picId = R.mipmap.fenju_pic_xinshun;
                    break;
                case GriddingSaleApp.CODE_FENJU_KONGGANG:
                    picId = R.mipmap.fenju_pic_konggang;
                    break;
                case GriddingSaleApp.CODE_FENJU_LINHE:
                    picId = R.mipmap.fenju_pic_linhe;
                    break;
                case GriddingSaleApp.CODE_FENJU_NIULANSHAN:
                    picId = R.mipmap.fenju_pic_niulanshan;
                    break;
                case GriddingSaleApp.CODE_FENJU_YANGZHEN:
                    picId = R.mipmap.fenju_pic_yangzhen;
                    break;
                case GriddingSaleApp.CODE_FENJU_MULIN:
                    picId = R.mipmap.fenju_pic_mulin;
                    break;
                default:
                    break;
            }
            data.setmFenjuPic(picId);
            mFenjuDatas.add(data);
        }
        cursor.close();
    }

    private class LoadingFenjuDataThread implements Runnable {
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
