package com.penner.architecture.model.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by penneryu on 16/8/3.
 */
public class YuDataProvider extends SqliteFactory<YuCategoryModel> {

    public final static String tableName = "yu_data";

    public static void createDB(SQLiteDatabase db){
        db.execSQL("create table if not exists yu_data(" +
                "_id integer primary key," +
                "name varchar)");
    }

    public YuDataProvider(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return tableName;
    }

    @Override
    protected String getprimaryKey() {
        return "_id";
    }

    @Override
    protected YuCategoryModel createModel(Cursor cursor) {
        YuCategoryModel categoryModel = new YuCategoryModel();
        categoryModel.id = cursor.getInt(cursor.getColumnIndex("_id"));
        categoryModel.name = cursor.getString(cursor.getColumnIndex("name"));
        return categoryModel;
    }

    @Override
    protected void insertRecord(SQLiteDatabase db, YuCategoryModel record) {
        db.execSQL("insert into yu_data(_id,name) values(?,?)",
                new Object[]{null, record.name});

    }
}
