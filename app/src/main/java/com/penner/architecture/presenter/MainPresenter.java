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
import com.penner.architecture.view.MainView;

import java.util.List;

/**
 * Created by penneryu on 16/7/27.
 */
public class MainPresenter extends BasePresenter<MainView> {

    MainView mainView;
    PennerDataProvier pennerDataProvier;
    YuDataProvider yuDataProvider;

    public MainPresenter(Context context) {
        pennerDataProvier = new PennerDataProvier();
        yuDataProvider = new YuDataProvider(context);
    }

    public void getMainString() {
        pennerDataProvier.getCategory(new DataProvider<ListDataModel<PennerCategoryModel>>() {
            @Override
            public void dataSuccess(ListDataModel<PennerCategoryModel> result) {
                List<PennerCategoryModel> list = result.data;

                StringBuilder builder = new StringBuilder();
                for (PennerCategoryModel categoryModel : list) {
                    builder.append(categoryModel.id);
                    builder.append(" ");
                    builder.append(categoryModel.name);
                    builder.append("; ");
                }
                mainView.onShowMainString(builder.toString());
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

                StringBuilder builder = new StringBuilder();
                for (YuCategoryModel categoryModel : result) {
                    builder.append(categoryModel.id);
                    builder.append(" ");
                    builder.append(categoryModel.name);
                    builder.append("; ");
                }
                mainView.onShowTwoString(builder.toString());
            }

            @Override
            public void dataError(ErrorInfo errorInfo) {

            }
        });
    }

    @Override
    public void setView(MainView view) {
        mainView = view;
    }
}
