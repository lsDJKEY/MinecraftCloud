package io.ibj.onevone.server.remote.event;

import io.ibj.onevone.server.event.EventController;
import io.ibj.onevone.server.event.EventHandler;

/**
 * Created by Joe on 9/11/2014.
 */
public class ObjectEOLEventHandler implements EventHandler<ObjectEOLEvent> {

    public ObjectEOLEventHandler(EventController controller){
        this.controller = controller;
    }

    private EventController controller;

    @Override
    public void handle(ObjectEOLEvent event) {
        controller.cleanup();
    }
}
