package com.penner.architecture.view.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.penner.architecture.R;
import com.penner.architecture.base.BaseActivity;
import com.penner.architecture.databinding.ActivityDatabindingBinding;
import com.penner.architecture.presenter.MvpPresenter;
import com.penner.architecture.view.MvpView;

public class DatabindingActivity extends BaseActivity implements MvpView {

    private MvpPresenter presenter;
    private DatabindingViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDatabindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
        presenter = new MvpPresenter(this);
        presenter.attachView(this);
        viewModel = new DatabindingViewModel();

        binding.setPresenter(presenter);
        binding.setPenner(viewModel);

        presenter.loadDatas();
    }

    @Override
    public void showHttpString(String json) {
        viewModel.setMain(json);
    }

    @Override
    public void showSqliteString(String json) {
        viewModel.setTwo(json);
    }

    @Override
    public void showSPString(String json) {

    }

    @Override
    public void onLoadding(final boolean loadding) {
        viewModel.setLoadding(loadding);
        if (!loadding) {
            final SwipeRefreshLayout refresh_layout = (SwipeRefreshLayout)findViewById(R.id.refresh_layout);
            refresh_layout.post(new Runnable() {
                @Override
                public void run() {
                    refresh_layout.setRefreshing(loadding);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.detachView();
    }
}
