package com.penner.architecture.presenter.dagger;

import com.penner.architecture.ApplicationComponent;
import com.penner.architecture.util.ScopeActivity;
import com.penner.architecture.view.DaggerActivity;

import dagger.Component;

/**
 * Created by penneryu on 16/8/5.
 */
@ScopeActivity
@Component(dependencies = ApplicationComponent.class, modules = PennerPresenterModule.class)
public interface PennerComponent {

    void inject(DaggerActivity activity);
}
