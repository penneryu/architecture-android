package com.penner.architecture.model.http;

import com.penner.architecture.model.ListDataModel;
import com.penner.architecture.model.DataProvider;
import com.penner.architecture.util.UriUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by penneryu on 16/8/3.
 */
public class PennerDataProvier  {

    private IPennerDataService mService;

    public PennerDataProvier() {
        mService = RetrofitFactory.createService(IPennerDataService.class, UriUtils.s9Host);
    }

    public void getCategory(DataProvider<ListDataModel<PennerCategoryModel>> dataProvider) {
        RetrofitFactory.createDatas(mService.getRXCategory(), dataProvider);
    }

    private interface IPennerDataService {

        @GET("app/v2/channel")
        Call<ListDataModel<PennerCategoryModel>> getCategory();

        @GET("app/v2/channel")
        Observable<ListDataModel<PennerCategoryModel>> getRXCategory();
    }

}
