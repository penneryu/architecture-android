package com.penner.architecture.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.penner.architecture.R;
import com.penner.architecture.presenter.MvpPresenter;

public class MvpActivity extends AppCompatActivity implements MvpView {

    MvpPresenter presenter;
    TextView mMvpTxt;
    TextView mTwoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        mMvpTxt = (TextView)findViewById(R.id.mvp_txt);
        mTwoTxt = (TextView)findViewById(R.id.two_txt);
        loadDatas();
    }

    public void loadDatas() {
        presenter = new MvpPresenter(this);
        presenter.attachView(this);
        presenter.getMainString();
        presenter.getTwoString();
    }

    @Override
    public void onShowMainString(String json) {
        mMvpTxt.setText(json);
    }

    @Override
    public void onShowTwoString(String json) {
        mTwoTxt.setText(json);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.detachView();
    }
}
