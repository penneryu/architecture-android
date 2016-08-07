package com.penner.architecture;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by penneryu on 16/8/5.
 */
@Singleton
@Component(modules = {DomainDaggerModel.class, ApplicationModule.class})
public interface ApplicationComponent {

    Context getContext();
    DomainDaggerModel.DomainDaggerProvider getProvider();
}
