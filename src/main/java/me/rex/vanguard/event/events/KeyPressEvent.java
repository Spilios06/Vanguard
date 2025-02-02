package me.rex.vanguard.event.events;

import me.rex.vanguard.event.Event;


public class KeyPressEvent extends Event {
    public int key;

    public KeyPressEvent(int key) {
        this.key = key;
    }
}
