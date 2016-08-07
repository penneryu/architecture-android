package com.penner.architecture;

import android.app.Application;

/**
 * Created by penneryu on 16/8/5.
 */
public class PennerApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .domainDaggerModel(new DomainDaggerModel())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
