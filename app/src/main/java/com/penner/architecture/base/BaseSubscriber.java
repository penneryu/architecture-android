package com.penner.architecture.base;

import android.util.JsonReader;
import android.widget.Toast;

import com.penner.architecture.PennerApplication;
import com.penner.architecture.R;
import com.penner.architecture.model.DataProvider;
import com.penner.architecture.model.ErrorInfo;
import com.penner.architecture.util.LogUtils;

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
            Toast.makeText(PennerApplication.sContext, errorInfo.message, Toast.LENGTH_SHORT).show();
        } else {
            e.printStackTrace();
            if (e instanceof ConnectException) {
                mDataProvider.dataError(null);
                Toast.makeText(PennerApplication.sContext,
                        PennerApplication.sContext.getString(R.string.penner_netfail), Toast.LENGTH_SHORT).show();
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
                if ("code".equals(name)) {
                    errorInfo.code = json.nextInt();
                } else if ("message".equals(name)) {
                    errorInfo.message = json.nextString();
                } else {
                    json.skipValue();
                }
            }
            json.endObject();
            return errorInfo;
        } catch (Exception e) {
            LogUtils.e("RxSubscriber onResponse", e.toString());
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
