package ua.dp.rename.dniprostreets.core;

import de.greenrobot.event.EventBus;
import ua.dp.rename.dniprostreets.event.DontGiveAFuckEvent;

public class Presenter<VT extends Presenter.View> {

    protected VT view;
    protected EventBus eventBus;

    public Presenter() {
        eventBus = EventBus.getDefault();
    }

    public void takeView(VT view) {
        this.view = view;
        eventBus.register(this);
    }

    public void dropView() {
        eventBus.unregister(this);
        view = null;
    }

    public void onEvent(final DontGiveAFuckEvent event) { }

    public interface View { }
}
