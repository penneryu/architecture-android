package com.penner.architecture.view;

/**
 * Created by penneryu on 16/8/5.
 */
public interface MvpView {

    void showHttpString(String json);

    void showSqliteString(String json);

    void showSPString(String json);
    
    void onLoadding(boolean loadding);
}
