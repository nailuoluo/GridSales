package com.liuy314.griddingsale.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.liuy314.griddingsale.R;
import com.liuy314.griddingsale.db.ShopDatabaseHelper;
import com.liuy314.griddingsale.utl.GriddingSaleApp;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by liuy314 on 2015/8/10.
 */
public class SplashActivity extends AppCompatActivity {

    private TextView mLoadingSatusTextView;

    private final String MSG_STATUS = "msg";
    private final int DB_STATUS_OK = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (bundle.getInt(MSG_STATUS)) {
                case 0:
                    mLoadingSatusTextView.setText(R.string.progressbar_status_loading_OK);
                    startActivity(new Intent().setClass(SplashActivity.this, FenJuActivity.class));
                    SplashActivity.this.finish();
                    break;
                default:
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mLoadingSatusTextView = (TextView)findViewById(R.id.loading_status_textview);
        new Thread(new PerpareDBThread()).start();
    }

    private class PerpareDBThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ((GriddingSaleApp)getApplication()).setDb(
                    new ShopDatabaseHelper(
                            SplashActivity.this).getDatabase()
            );
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt(MSG_STATUS, DB_STATUS_OK);
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }
    }
}
