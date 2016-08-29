package com.penner.architecture.presenter;

import com.penner.architecture.base.BasePresenter;
import com.penner.architecture.view.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by penneryu on 16/7/27.
 */
public class MainPresenter extends BasePresenter<MainView> {

    private  MainView mainView;

    public void loadDatas() {
        List<String> datas = new ArrayList<>();
        datas.add("MVP");
        datas.add("Dagger");
        datas.add("Databinding");
        mainView.loadDatas(datas);
    }

    @Override
    public void attachView(MainView view) {
        mainView = view;
    }

    @Override
    public void detachView() {
        mainView = null;
    }
}
