package com.penner.architecture.model;

/**
 * Created by penneryu on 16/8/3.
 */
public interface DataProvider<T> {

    void dataSuccess(T result);
    void dataError(ErrorInfo errorInfo);
}
