package ua.dp.rename.dniprostreets.event;

import ua.dp.rename.dniprostreets.entity.RenamedObject;

public class RenamedObjectClickedEvent {

    private RenamedObject model;

    public RenamedObjectClickedEvent(RenamedObject model) {
        this.model = model;
    }

    public RenamedObject getModel() {
        return model;
    }
}
