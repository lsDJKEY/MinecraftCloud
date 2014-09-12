package io.ibj.onevone.server;

import io.ibj.onevone.server.event.NetworkEvent;
import io.ibj.onevone.server.local.LocalServer;

import java.util.UUID;

/**
 * Represents data sent to initiate a server/process.
 */
public class ProcessDefinition implements NetworkEvent {

    public ProcessDefinition(LocalServer me, String processName, String data){

        this.eventId = UUID.randomUUID();
        this.senderId = me.getId();
        this.processName = processName;
        this.processData = data;

    }

    UUID eventId;
    UUID senderId;
    String processName;
    String processData;

    @Override
    public UUID getId() {
        return eventId;
    }

    @Override
    public UUID getSender() {
        return senderId;
    }

    public String getProcessName(){
        return processName;
    }

    public String getProcessData(){
        return processData;
    }
}
