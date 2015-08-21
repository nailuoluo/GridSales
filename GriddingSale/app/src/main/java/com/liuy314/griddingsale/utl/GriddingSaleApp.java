package com.liuy314.griddingsale.utl;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.liuy314.griddingsale.db.ShopDatabaseHelper;

/**
 * Created by liuy314 on 2015/8/10.
 */
public  class GriddingSaleApp extends Application {

    // 分局代码
    public static final int CODE_FENJU_XINSHUN = 80602;
    public static final int CODE_FENJU_LINHE = 80601;
    public static final int CODE_FENJU_KONGGANG = 80604;
    public static final int CODE_FENJU_NIULANSHAN = 80606;
    public static final int CODE_FENJU_YANGZHEN = 80605;
    public static final int CODE_FENJU_MULIN = 80603;

    // 网格代码
    // 新顺分局网格
//    public final int CODE_WANGGE_JIANXIN = 0x01;
//    public final int CODE_WANGGE_XIXIN = 0x02;
//    public final int CODE_WANGGE_NANFAXIN = 0x03;
//    public final int CODE_WANGGE_YULONG = 0x04;
//    public final int CODE_WANGGE_NANCAI = 0x05;
//    // 林河分局网格
//    public final int CODE_WANGGE_LINHE = 0x11;
//    public final int CODE_WANGGE_LISUI = 0x12;
//    public final int CODE_WANGGE_BANBIDIAN = 0x13;
//    // 空港分局网格
//    public final int CODE_WANGGE_HOUSHAYU = 0x21;
//    public final int CODE_WANGGE_AQU = 0x22;
//    public final int CODE_WANGGE_GAOLIYING = 0x23;
//    // 牛栏山分局网格
//    public final int CODE_WANGGE_MAPO = 0x31;
//    public final int CODE_WANGGE_NIUSHAN = 0x32;
//    public final int CODE_WANGGE_ZHAOQUANYING = 0x33;
//    // 杨镇分局网格
//    public final int CODE_WANGGE_YANGZHEN = 0x41;
//    public final int CODE_WANGGE_ZHANGZHEN = 0x42;
//    // 木林分局网格
//    public final int CODE_WANGGE_MULIN = 0x51;
//    public final int CODE_WANGGE_BEIXIAOYING = 0x52;

    public final String TABLE_NAME = "shoplist";
    public final String INDEX_FENJU = "fenju";
    public final String INDEX_CODE_FENJU = "code_fenju";
    public final String INDEX_WANGGE= "wangge";
    public final String INDEX_CODE_WANGGE = "code_wangge";
    public final String INDEX_QUDAOWANGGE= "qudaowangge";
    public final String INDEX_CODE_QUDAOWANGGE = "code_qudaowangge";
    public final String INDEX_CODE_SHOP="code_shop";
    public final String INDEX_SHOP="shop";
    public final String INDEX_LATITUDE="latitude";
    public final String INDEX_LONGITUDE="longitude";


    private SQLiteDatabase db;

    public SQLiteDatabase getDb() {
        if(db == null) {
            db = new ShopDatabaseHelper(getApplicationContext()).getDatabase();
        }
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }


}
