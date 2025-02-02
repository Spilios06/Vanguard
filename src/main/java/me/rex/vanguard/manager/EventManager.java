package me.rex.vanguard.manager;

import com.google.common.eventbus.EventBus;

public class EventManager{
    private final EventBus EVENT_BUS = new EventBus();

    public void init() {
        EVENT_BUS.register(this);
    }

    public EventBus getEventBus() {
        return EVENT_BUS;
    }
}
