package com.penner.architecture.base;

import android.content.Context;

/**
 * Created by penneryu on 16/7/29.
 */
public abstract class BasePresenter<T> {

    public abstract void setView(T view);
}
