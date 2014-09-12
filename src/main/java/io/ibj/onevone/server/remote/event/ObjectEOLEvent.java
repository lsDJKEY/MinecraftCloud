package io.ibj.onevone.server.remote.event;

import io.ibj.onevone.server.event.NetworkEvent;
import io.ibj.onevone.server.local.LocalServer;

import java.util.UUID;

/**
 * Created by Joe on 9/11/2014.
 */
public class ObjectEOLEvent implements NetworkEvent {

    public ObjectEOLEvent(LocalServer me){
        id = UUID.randomUUID();
        sender = me.getId();

    }

    UUID id;
    UUID sender;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getSender() {
        return sender;
    }
}
