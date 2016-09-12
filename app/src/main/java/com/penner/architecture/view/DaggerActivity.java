package com.penner.architecture.view;

import android.os.Bundle;
import android.widget.TextView;

import com.penner.architecture.PennerApplication;
import com.penner.architecture.R;
import com.penner.architecture.base.BaseActivity;
import com.penner.architecture.presenter.DaggerPresenter;
import com.penner.architecture.presenter.dagger.DaggerPennerComponent;
import com.penner.architecture.presenter.dagger.PennerPresenterModule;

import javax.inject.Inject;

public class DaggerActivity extends BaseActivity implements DaggerView {

    @Inject
    DaggerPresenter presenter;

    TextView mDaggerTxt;
    TextView mPennerTxt;
    TextView mYuTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);

        mDaggerTxt = (TextView)findViewById(R.id.dagger_txt);
        mPennerTxt = (TextView)findViewById(R.id.dagger_penner_txt);
        mYuTxt = (TextView)findViewById(R.id.dagger_yu_txt);

        DaggerPennerComponent.builder()
                .applicationComponent(((PennerApplication)getApplication()).getApplicationComponent())
                .pennerPresenterModule(new PennerPresenterModule(this))
                .build()
                .inject(this);

        presenter.loadDatas();
    }

    @Override
    public void loadData(String data) {
        mDaggerTxt.setText(data);
    }

    @Override
    public void loadPennerData(String data) {
        mPennerTxt.setText(data);
    }

    @Override
    public void loadYuData(String data) {
        mYuTxt.setText(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.detachView();
    }
}
