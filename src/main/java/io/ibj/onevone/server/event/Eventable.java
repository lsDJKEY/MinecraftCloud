package io.ibj.onevone.server.event;

import io.ibj.onevone.persistance.DEntity;

/**
 * Created by Joe on 9/1/2014.
 */
public interface Eventable extends DEntity {

    public EventController getEventController();

}
