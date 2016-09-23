package com.penner.architecture.model.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.penner.architecture.base.BaseSqliteSubscriber;
import com.penner.architecture.model.DataProvider;
import com.penner.architecture.util.LogUtils;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.QueryObservable;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
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
                }).subscribe(new BaseSqliteSubscriber<>(dataProvider, db));

        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
    }

    /**
     * 分页查询
     * @param firstResult
     * @param maxResult
     * @return
     */
    public ArrayList<T> findRecords(int firstResult, int maxResult) {
        return findRecords(firstResult, maxResult, null, null);
    }

    public ArrayList<T> findRecords(int firstResult, int maxResult, String selection, String[] selectionArgs) {
        SQLiteDatabase db = null;
        ArrayList<T> result = new ArrayList<>(maxResult);
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();
            cursor = findCursorRecords(db, firstResult, maxResult, selection, selectionArgs);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    result.add(createModel(cursor));
                }
                return result;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    /**
     * 返回分页记录的游标，适配CursorAdapter
     * @param firstResult
     * @param maxResult
     * @return
     */
    public Cursor findCursorRecords(SQLiteDatabase db, int firstResult, int maxResult, String selection, String[] selectionArgs) {
        String sql;
        String where = "";
        String[] args = selectionArgs;
        if (!TextUtils.isEmpty(selection)) {
            int length = args.length;
            where = String.format("where %s", selection);
            args = new String[length + 2];
            System.arraycopy(selectionArgs, 0, args, 0, length);
            args[length] = String.valueOf(firstResult);
            args[length + 1] = String.valueOf(maxResult);
        }
        if (!TextUtils.isEmpty(getOrderColumnName())) {
            sql = String.format("select * from %s %s order by %s %s limit ?,?", getTableName(), where, getOrderColumnName(), getOrderType());
        } else {
            sql = String.format("select * from %s %s limit ?,?", getTableName(), where);
        }
        try {
            return db.rawQuery(sql, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 返回所有记录的游标，适配CursorAdapter
     * @return
     */
    public Cursor findCursorRecords(SQLiteDatabase db) {
        String sql;
        if (!TextUtils.isEmpty(getOrderColumnName())) {
            sql = String.format("select * from %s order by %s %s", getTableName(), getOrderColumnName(), getOrderType());
        } else {
            sql = String.format("select * from %s", getTableName());
        }
        try {
            return db.rawQuery(sql, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 根据主键查询一条记录
     * @param primaryKey
     * @return
     */
    public T findRecord(int primaryKey) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();
            String sql = String.format("select * from %s where %s=?", getTableName(), getprimaryKey());
            cursor = db.rawQuery(sql, new String[] {String.valueOf(primaryKey)});
            T result = null;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    result = createModel(cursor);
                }
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    /**根据一个键值对查询
     *
     * @param property
     * @param value
     * @return
     */
    public T findByProperty(String property, String value) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();
            String sql = String.format("select * from %s where %s=?", getTableName(), property);
            cursor = db.rawQuery(sql, new String[] {value});
            T result = null;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    result = createModel(cursor);
                }
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    /**
     * 查询最新的一条记录
     */
    public T findNewestRecord() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();
            String sql = String.format("select * from %s order by %s %s limit 1", getTableName(), getOrderColumnName(), getOrderType());
            cursor = db.rawQuery(sql, new String[] {});
            T result = null;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    result = createModel(cursor);
                }
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
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
     * 批量插入记录
     * @param records
     */
    public void insertRecord(List<T> records) {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            db.beginTransaction();
            int size = records.size();
            long max = getMaxCount();
            if (max > 0) {
                long curCount = DatabaseUtils.queryNumEntries(db, getTableName());
                long total = curCount + size;
                if (total >= max) {
                    long del = total - max + 1;
                    for (int i = 0; i < del; i ++) {
                        deleteOldRecord(db);
                    }
                }
            }
            for (T record : records) {
                insertRecord(db, record);
            }
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 删除所有记录
     */
    public void deletedRecords() {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            String sql = String.format("delete from %s", getTableName());
            db.execSQL(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * 根据主键删除
     * @param primaryKey
     */
    public void deletedRecord(int primaryKey) {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            String sql = String.format("delete from %s where %s=?", getTableName(), getprimaryKey());
            db.execSQL(sql, new Integer[] {primaryKey});
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * 记录数量
     * @return
     */
    public long getRecordCount() {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            return DatabaseUtils.queryNumEntries(db, getTableName());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public long getRecordCount(String selection, String[] selectionArgs) {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            return DatabaseUtils.queryNumEntries(db, getTableName(), selection, selectionArgs);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * 删除旧的记录
     * @param db
     */
    private void deleteOldRecord(SQLiteDatabase db) {
        String sql;
        if (!TextUtils.isEmpty(getOrderColumnName())) {
            sql = String.format("delete from %s where %s in (select %s from %s order by %s %s limit 1)",
                    getTableName(), getprimaryKey(), getprimaryKey(), getTableName(), getOrderColumnName(), "desc".equals(getOrderType()) ? "asc" : "desc");
        } else {
            sql = String.format("delete from %s where %s in (select %s from %s limit 1)",
                    getTableName(), getprimaryKey(), getprimaryKey(), getTableName());
        }
        db.execSQL(sql);
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
