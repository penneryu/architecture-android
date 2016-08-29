package com.penner.architecture.view.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

import com.penner.architecture.presenter.MvpPresenter;

/**
 * Created by penneryu on 16/8/29.
 */
public class SwipeRefreshLayoutDataBinding {

    @BindingAdapter("android:onRefresh")
    public static void setSwipeRefreshLayoutOnRefreshListener(SwipeRefreshLayout view, final MvpPresenter presenter) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadDatas();
            }
        });
    }
}
