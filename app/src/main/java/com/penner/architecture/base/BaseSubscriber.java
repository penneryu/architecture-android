package com.penner.architecture.base;

import android.util.JsonReader;
import android.util.Log;

import com.penner.architecture.model.DataProvider;
import com.penner.architecture.model.ErrorInfo;

import java.io.IOException;
import java.net.ConnectException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

/**
 * Created by penneryu on 16/8/3.
 */
public class BaseSubscriber<T> implements Observer<T> {

    private DataProvider<T> mDataProvider;

    public BaseSubscriber(DataProvider<T> dataProvider) {
        mDataProvider = dataProvider;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            ErrorInfo errorInfo = parseError(responseBody);
            mDataProvider.dataError(errorInfo);
        } else {
            e.printStackTrace();
            if (e instanceof ConnectException) {
                mDataProvider.dataError(null);
            }
        }
    }

    @Override
    public void onNext(T t) {
        mDataProvider.dataSuccess(t);
    }

    protected ErrorInfo parseError(ResponseBody response) {
        JsonReader json = null;
        try {
            json = new JsonReader(response.charStream());
            json.beginObject();
            ErrorInfo errorInfo = new ErrorInfo();
            while (json.hasNext()) {
                String name = json.nextName();
                if (name.equals("code")) {
                    errorInfo.code = json.nextInt();
                } else if (name.equals("message")) {
                    errorInfo.message = json.nextString();
                } else {
                    json.skipValue();
                }
            }
            json.endObject();
            return errorInfo;
        } catch (Exception e) {
            Log.e("RxSubscriber onResponse", e.toString());
        } finally {
            response.close();
            if (json != null) {
                try {
                    json.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
