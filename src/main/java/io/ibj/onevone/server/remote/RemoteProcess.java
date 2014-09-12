package io.ibj.onevone.server.remote;

import io.ibj.onevone.server.*;
import io.ibj.onevone.server.event.EventController;

import java.util.UUID;

/**
 * Created by Joe on 9/10/2014.
 */
public class RemoteProcess implements io.ibj.onevone.server.Process {



    EventController controller;

    @Override
    public void halt() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public Server getServer() {
        return null;
    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public EventController getEventController() {
        return controller;
    }

}
