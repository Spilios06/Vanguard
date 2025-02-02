package me.rex.vanguard.manager;

import com.google.common.eventbus.EventBus;

public class EventManager{
    public static final EventManager INSTANCE = new EventManager();
    private static final EventBus EVENT_BUS = new EventBus();

    public void init() {
        EVENT_BUS.register(this);
    }

    public EventBus getEventBus() {
        return EVENT_BUS;
    }
}
