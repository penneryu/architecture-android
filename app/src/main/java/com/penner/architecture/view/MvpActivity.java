package com.penner.architecture.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.penner.architecture.R;
import com.penner.architecture.base.BaseActivity;
import com.penner.architecture.presenter.MvpPresenter;

public class MvpActivity extends BaseActivity implements MvpView {

    MvpPresenter presenter;
    TextView mMvpTxt;
    TextView mTwoTxt;
    TextView mThreeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        mMvpTxt = (TextView)findViewById(R.id.mvp_txt);
        mTwoTxt = (TextView)findViewById(R.id.two_txt);
        mThreeTxt = (TextView)findViewById(R.id.three_txt);

        presenter = new MvpPresenter(this);
        presenter.attachView(this);
        presenter.loadDatas();
    }

    @Override
    public void showHttpString(String json) {
        mMvpTxt.setText(json);
    }

    @Override
    public void showSqliteString(String json) {
        mTwoTxt.setText(json);
    }

    @Override
    public void showSPString(String json) {
        mThreeTxt.setText(json);
    }

    @Override
    public void onLoadding(boolean loadding) {
        if (loadding) {
            mMvpTxt.setVisibility(View.GONE);
            mTwoTxt.setVisibility(View.GONE);
        } else {
            mMvpTxt.setVisibility(View.VISIBLE);
            mTwoTxt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.detachView();
    }
}
