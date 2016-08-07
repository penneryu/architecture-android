package com.penner.architecture;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by penneryu on 16/8/5.
 */
@Module
public final class ApplicationModule {

    private final Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
