package com.amrendra.codefiesta.bus.events;

/**
 * Created by Amrendra Kumar on 08/05/16.
 */
public class SnackBarMessageDetailFragmentEvent {
    String msg;

    public SnackBarMessageDetailFragmentEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
