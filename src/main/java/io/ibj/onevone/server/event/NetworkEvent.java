package io.ibj.onevone.server.event;

import java.util.UUID;

/**
 * Represents a network created event
 */
public interface NetworkEvent {

    /**
     * Returns this event's ID
     * @return  ID
     */
    public UUID getId();

}
