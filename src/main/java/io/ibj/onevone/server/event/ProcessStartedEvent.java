package io.ibj.onevone.server.event;

import io.ibj.onevone.server.Server;

import java.util.UUID;

/**
 * Created by Joe on 8/31/2014.
 */
public class ProcessStartedEvent implements NetworkEvent {

    UUID id;
    Process processStarted;
    Server server;

    @Override
    public UUID getId() {
        return id;
    }

    public Process getProcess(){
        return processStarted;
    }

    public Server getServer(){
        return server;
    }
}
