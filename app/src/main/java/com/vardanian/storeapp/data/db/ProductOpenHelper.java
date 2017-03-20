package com.vardanian.storeapp.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vardanian.storeapp.data.db.ProductDbSchema.ProductTable;

public class ProductOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "productBase.db";

    public ProductOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ProductTable.NAME + "(" +
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProductTable.Cols.UUID + ", " +
                ProductTable.Cols.PRODUCT_NAME + ", " +
                ProductTable.Cols.PRODUCT_PRICE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + DB_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
