package com.liuy314.griddingsale.utl;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import com.liuy314.griddingsale.R;

/**
 * Created by liuy314 on 2015/8/11.
 */
public class BaseAcitivity extends AppCompatActivity {

    private LinearLayout mExternalLayout;
    private Toolbar mToolbar;
    protected ProgressBar mLoadingDBProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mLoadingDBProgressBar = (ProgressBar)findViewById(R.id.loading_data_progressBar);
    }

    protected void InitializeContent(int layoutID) {
        mExternalLayout = (LinearLayout)findViewById(R.id.external_layout);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutID, null);
        mExternalLayout.addView(view);
    }


}
