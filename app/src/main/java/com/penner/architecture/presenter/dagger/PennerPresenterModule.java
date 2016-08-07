package com.penner.architecture.presenter.dagger;

import android.content.Context;

import com.penner.architecture.model.http.PennerDataProvier;
import com.penner.architecture.model.sqlite.YuDataProvider;
import com.penner.architecture.view.DaggerView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by penneryu on 16/8/5.
 */
@Module
public class PennerPresenterModule {

    private final DaggerView mView;

    public PennerPresenterModule(DaggerView view) {
        mView = view;
    }

    @Provides
    DaggerView provideDaggerView() {
        return mView;
    }

    @Provides
    PennerDataProvier provierRemoteDataProvider() {
        return new PennerDataProvier();
    }

    @Provides
    YuDataProvider providerLocalDataProvider(Context context) {
        return new YuDataProvider(context);
    }
}
