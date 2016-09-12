package com.penner.architecture.base;

import com.penner.architecture.model.DataProvider;
import com.squareup.sqlbrite.BriteDatabase;

import rx.Observer;

/**
 * Created by penneryu on 16/9/12.
 */
public class BaseSqliteSubscriber <T> implements Observer<T> {

    private DataProvider<T> mDataProvider;
    private BriteDatabase mDB;

    public BaseSqliteSubscriber(DataProvider<T> dataProvider, BriteDatabase db) {
        mDataProvider = dataProvider;
        mDB = db;
    }

    @Override
    public void onCompleted() {
        mDB.close();
    }

    @Override
    public void onError(Throwable e) {
        mDataProvider.dataError(null);
    }

    @Override
    public void onNext(T t) {
        mDataProvider.dataSuccess(t);
    }
}
