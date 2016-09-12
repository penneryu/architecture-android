package com.penner.architecture;

import android.app.Application;
import android.content.Context;

/**
 * Created by penneryu on 16/8/5.
 */
public class PennerApplication extends Application {

    public static Context sContext;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .domainDaggerModel(new DomainDaggerModel())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
