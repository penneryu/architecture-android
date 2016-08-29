package com.penner.architecture.view.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by penneryu on 16/8/29.
 */
public class DatabindingViewModel extends BaseObservable {

    private boolean loadding;
    private String mainString;
    private String twoString;

    @Bindable
    public String getMainString() {
        return mainString;
    }

    @Bindable
    public String getTwoString() {
        return twoString;
    }

    @Bindable
    public boolean getLoadding() {
        return loadding;
    }

    public void setMain(String mainString) {
        this.mainString = mainString;
        notifyPropertyChanged(BR.mainString);
    }

    public void setTwo(String twoString) {
        this.twoString = twoString;
        notifyPropertyChanged(BR.twoString);
    }

    public void setLoadding(boolean loadding) {
        this.loadding = loadding;
        notifyPropertyChanged(BR.loadding);
    }

}
