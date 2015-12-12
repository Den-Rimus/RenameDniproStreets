package ua.dp.rename.dniprostreets.core;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import de.greenrobot.event.EventBus;
import ua.dp.rename.dniprostreets.event.DontGiveAFuckEvent;

public abstract class BasePresenter<V extends MvpView> extends MvpNullObjectBasePresenter<V> {

    protected EventBus eventBus;

    public BasePresenter() {
        eventBus = EventBus.getDefault();
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
        eventBus.register(this);
    }

    @Override
    public void detachView(boolean retainInstance) {
        eventBus.unregister(this);
        super.detachView(retainInstance);
    }

    public void onEvent(final DontGiveAFuckEvent event) {
        ; // this line doesn't give a fuck, as intended
    }
}
