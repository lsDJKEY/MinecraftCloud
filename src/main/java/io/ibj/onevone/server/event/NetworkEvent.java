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

    /**
     * Represents the sending server. To prevent double calls
     * @return  UUID of the sending server
     */
    public UUID getSender();

}
