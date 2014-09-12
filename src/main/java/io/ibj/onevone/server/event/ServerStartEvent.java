package io.ibj.onevone.server.event;

import io.ibj.onevone.server.Server;

import java.util.UUID;

/**
 * Created by Joe on 8/31/2014.
 */
public class ServerStartEvent implements NetworkEvent {

    UUID uuid;
    Server server;

    public ServerStartEvent(Server server){
        uuid = UUID.randomUUID();
        this.server = server;
    }
    public ServerStartEvent(Server server, UUID uuid){
        this.uuid = uuid;
        this.server = server;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    public Server getServer(){
        return server;
    }

}
