package com.penner.architecture.presenter;

import android.content.Context;

import com.penner.architecture.base.BasePresenter;
import com.penner.architecture.model.DataProvider;
import com.penner.architecture.model.ErrorInfo;
import com.penner.architecture.model.ListDataModel;
import com.penner.architecture.model.http.PennerCategoryModel;
import com.penner.architecture.model.http.PennerDataProvier;
import com.penner.architecture.model.sqlite.YuCategoryModel;
import com.penner.architecture.model.sqlite.YuDataProvider;
import com.penner.architecture.view.MvpView;

import java.util.List;

/**
 * Created by penneryu on 16/8/5.
 */
public class MvpPresenter extends BasePresenter<MvpView> {

    MvpView mvpView;
    PennerDataProvier pennerDataProvier;
    YuDataProvider yuDataProvider;

    public MvpPresenter(Context context) {
        pennerDataProvier = new PennerDataProvier();
        yuDataProvider = new YuDataProvider(context);
    }

    public void loadDatas() {
        mvpView.onLoadding(true);
        getMainString();
        getTwoString();
    }

    public void getMainString() {
        pennerDataProvier.getCategory(new DataProvider<ListDataModel<PennerCategoryModel>>() {
            @Override
            public void dataSuccess(ListDataModel<PennerCategoryModel> result) {
                if (mvpView == null) {
                    return;
                }

                List<PennerCategoryModel> list = result.data;
                StringBuilder builder = new StringBuilder();
                for (PennerCategoryModel categoryModel : list) {
                    builder.append(categoryModel.id);
                    builder.append(" ");
                    builder.append(categoryModel.name);
                    builder.append("; ");
                }
                mvpView.onShowMainString(builder.toString());
                mvpView.onLoadding(false);
            }

            @Override
            public void dataError(ErrorInfo errorInfo) {

            }
        });
    }

    public void getTwoString() {
        YuCategoryModel categoryModel = new YuCategoryModel();
        categoryModel.name = "penner";
        yuDataProvider.insertRecord(categoryModel);

        yuDataProvider.findRecords(new DataProvider<List<YuCategoryModel>>() {
            @Override
            public void dataSuccess(List<YuCategoryModel> result) {
                if (mvpView == null) {
                    return;
                }

                StringBuilder builder = new StringBuilder();
                for (YuCategoryModel categoryModel : result) {
                    builder.append(categoryModel.id);
                    builder.append(" ");
                    builder.append(categoryModel.name);
                    builder.append("; ");
                }
                mvpView.onShowTwoString(builder.toString());
            }

            @Override
            public void dataError(ErrorInfo errorInfo) {

            }
        });
    }

    @Override
    public void attachView(MvpView view) {
        mvpView = view;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }
}
