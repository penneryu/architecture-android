package com.penner.architecture.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.penner.architecture.R;
import com.penner.architecture.presenter.MainPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter presenter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new MainPresenter();
        presenter.attachView(this);
        presenter.loadDatas();
    }

    @Override
    public void loadDatas(List<String> datas) {
        recyclerView.setAdapter(new MainRecyclerAdapter(this, datas));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.detachView();
    }
}
