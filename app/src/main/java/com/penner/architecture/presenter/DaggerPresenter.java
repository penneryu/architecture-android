package com.penner.architecture.presenter;

import com.penner.architecture.DomainDaggerModel;
import com.penner.architecture.model.DataProvider;
import com.penner.architecture.model.ErrorInfo;
import com.penner.architecture.model.ListDataModel;
import com.penner.architecture.model.http.PennerCategoryModel;
import com.penner.architecture.model.http.PennerDataProvier;
import com.penner.architecture.model.sqlite.YuCategoryModel;
import com.penner.architecture.model.sqlite.YuDataProvider;
import com.penner.architecture.view.DaggerView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by penneryu on 16/8/5.
 */
public class DaggerPresenter {

    private DomainDaggerModel.DomainDaggerProvider domainProvider;
    private PennerDataProvier pennerDataProvier;
    private YuDataProvider yuDataProvider;
    private DaggerView daggerView;

    @Inject
    public DaggerPresenter(DomainDaggerModel.DomainDaggerProvider daggerProvider
            , PennerDataProvier pennerDataProvier
            , YuDataProvider yuDataProvider
            , DaggerView daggerView) {
        this.domainProvider = daggerProvider;
        this.pennerDataProvier = pennerDataProvier;
        this.yuDataProvider = yuDataProvider;
        this.daggerView = daggerView;
    }

    public void loadDatas() {
        daggerView.loadData(domainProvider.getDomainDagger());

        pennerDataProvier.getCategory(new DataProvider<ListDataModel<PennerCategoryModel>>() {
            @Override
            public void dataSuccess(ListDataModel<PennerCategoryModel> result) {
                if (daggerView == null) {
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
                daggerView.loadPennerData(builder.toString());
            }

            @Override
            public void dataError(ErrorInfo errorInfo) {

            }
        });

        yuDataProvider.findRecords(new DataProvider<List<YuCategoryModel>>() {
            @Override
            public void dataSuccess(List<YuCategoryModel> result) {
                if (daggerView == null) {
                    return;
                }

                StringBuilder builder = new StringBuilder();
                for (YuCategoryModel categoryModel : result) {
                    builder.append(categoryModel.id);
                    builder.append(" ");
                    builder.append(categoryModel.name);
                    builder.append("; ");
                }
                daggerView.loadYuData(builder.toString());
            }

            @Override
            public void dataError(ErrorInfo errorInfo) {

            }
        });
    }

    public void detachView() {
        daggerView = null;
    }
}
