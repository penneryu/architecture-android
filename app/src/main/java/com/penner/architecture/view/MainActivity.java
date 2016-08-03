package com.penner.architecture.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.penner.architecture.R;
import com.penner.architecture.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter presenter;
    TextView mMainTxt;
    TextView mTwoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainTxt = (TextView)findViewById(R.id.main_txt);
        mTwoTxt = (TextView)findViewById(R.id.two_txt);
        loadDatas();
    }

    public void loadDatas() {
        presenter = new MainPresenter(this);
        presenter.setView(this);
        presenter.getMainString();
        presenter.getTwoString();
    }

    @Override
    public void onShowMainString(String json) {
        mMainTxt.setText(json);
    }

    @Override
    public void onShowTwoString(String json) {
        mTwoTxt.setText(json);
    }
}
