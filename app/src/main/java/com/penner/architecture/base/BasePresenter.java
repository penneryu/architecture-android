package com.penner.architecture.base;

/**
 * Created by penneryu on 16/7/29.
 */
public abstract class BasePresenter<T> {

    public abstract void attachView(T view);
    public abstract void detachView();
}
