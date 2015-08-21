package com.liuy314.griddingsale.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import com.liuy314.griddingsale.utl.GriddingSaleApp;
import com.liuy314.griddingsale.utl.SQLiteAssetHelper;

/**
 * Created by liuy314 on 2015/8/10.
 */
public class ShopDatabaseHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "GridShop.db";
    private static final int DATABASE_VERSION = 1;

    public ShopDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteDatabase getDatabase() {
        return getReadableDatabase();
    }
}
