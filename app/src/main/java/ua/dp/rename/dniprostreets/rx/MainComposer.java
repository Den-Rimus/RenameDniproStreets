package ua.dp.rename.dniprostreets.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainComposer<T> implements Observable.Transformer<T, T> {

    @Override
    public Observable<T> call(Observable<T> observable) {
        return observable.subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
