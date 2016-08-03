package com.penner.architecture.model.http;

import com.penner.architecture.base.BaseSubscriber;
import com.penner.architecture.model.DataProvider;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by penneryu on 16/8/3.
 */
public final class RetrofitFactory {

    public static <T> T createService(final Class<T> service, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(service);
    }

    public static <T> void createDatas(Observable<T> observable, DataProvider<T> provider) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<T>(provider));
    }
}
