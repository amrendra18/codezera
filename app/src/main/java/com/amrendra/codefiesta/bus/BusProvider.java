package com.amrendra.codefiesta.bus;

import com.squareup.otto.Bus;

/**
 * Created by Amrendra Kumar on 07/05/16.
 */
public class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    public BusProvider() {
        throw new IllegalStateException("BusProvider is singleton! Use static method getInstance instead");
    }
}
