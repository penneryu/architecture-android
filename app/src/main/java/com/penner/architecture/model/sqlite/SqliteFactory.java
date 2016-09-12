package com.penner.architecture.model.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.penner.architecture.model.DataProvider;
import com.penner.architecture.util.LogUtils;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.QueryObservable;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by penneryu on 16/8/3.
 */
public abstract class SqliteFactory<T> {

    protected SqliteLocalOpenHelper helper = null;

    public SqliteFactory(Context context) {
        helper = new SqliteLocalOpenHelper(context);
    }

    protected abstract String getTableName();
    protected abstract String getprimaryKey();
    protected abstract T createModel(Cursor cursor);
    protected abstract void insertRecord(SQLiteDatabase db, T record);

    protected String getOrderColumnName() {
        return null;
    }

    protected String getOrderType() {
        return "desc";
    }

    protected long getMaxCount() {
        return 0;
    }

    public void findRecords(final DataProvider<List<T>> dataProvider) {
        SqlBrite sqlBrite = SqlBrite.create();
        final BriteDatabase db = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
        try {
            String sql;
            if (!TextUtils.isEmpty(getOrderColumnName())) {
                sql = String.format("select * from %s order by %s %s", getTableName(), getOrderColumnName(), getOrderType());
            } else {
                sql = String.format("select * from %s", getTableName());
            }
            QueryObservable list = db.createQuery(getTableName(), sql);
            list.onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<SqlBrite.Query, Observable<List<T>>>() {
                    @Override
                    public Observable<List<T>> call(SqlBrite.Query query) {
                        return query.asRows(new Func1<Cursor, T>() {
                            @Override
                            public T call(Cursor cursor) {
                                return createModel(cursor);
                            }
                        }).onBackpressureBuffer()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .toList();
                    }
                }).subscribe(new Observer<List<T>>() {
                    @Override
                    public void onCompleted() {
                        db.close();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dataProvider.dataError(null);
                    }

                    @Override
                    public void onNext(List<T> ts) {
                        dataProvider.dataSuccess(ts);
                    }
                });

        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
    }

    /**
     * 插入一条记录
     * @param record
     */
    public void insertRecord(T record) {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            insertRecord(db, record);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * drop table
     * */
    public static void dropTable(SQLiteDatabase db, String tableName){
        try {
            db.execSQL(String.format("DROP TABLE IF EXISTS %s", tableName));
        } catch (Exception e) {
            LogUtils.e("LocalFactoryBase", "drop table:" + tableName + "error.");
        }
    }
}
