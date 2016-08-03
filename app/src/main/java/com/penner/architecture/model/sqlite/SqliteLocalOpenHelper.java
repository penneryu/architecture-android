package com.penner.architecture.model.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by penneryu on 16/8/3.
 */
public class SqliteLocalOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "penner.db";
    private static final int DATABASE_VERSION = 1;

    public SqliteLocalOpenHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        YuDataProvider.createDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        YuDataProvider.dropTable(db, YuDataProvider.tableName);

        onCreate(db);
    }
}
