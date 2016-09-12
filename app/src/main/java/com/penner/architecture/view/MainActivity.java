package com.penner.architecture.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.penner.architecture.R;
import com.penner.architecture.base.BaseActivity;
import com.penner.architecture.model.sp.SettingPreferencesFactory;
import com.penner.architecture.presenter.MainPresenter;

import java.util.List;

public class MainActivity extends BaseActivity implements MainView {

    MainPresenter presenter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new MainPresenter();
        presenter.attachView(this);
        presenter.loadDatas();
    }

    @Override
    public void loadDatas(List<String> datas) {
        recyclerView.setAdapter(new MainRecyclerAdapter(this, datas));

        SettingPreferencesFactory settingPreferencesFactory = new SettingPreferencesFactory(getApplicationContext());
        settingPreferencesFactory.setBoolValue(SettingPreferencesFactory.sBoolType, true);
        settingPreferencesFactory.setStringValue(SettingPreferencesFactory.sStringType, "Penner");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.detachView();
    }
}
