package ua.dp.rename.dniprostreets.core;

import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public abstract class BasePresenter<V extends MvpView> extends MvpNullObjectBasePresenter<V> {

    public void onStart() {
        //
    }
}
