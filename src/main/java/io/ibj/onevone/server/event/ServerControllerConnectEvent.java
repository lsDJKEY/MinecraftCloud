package io.ibj.onevone.server.event;

import io.ibj.onevone.server.ServerController;

import java.util.UUID;

/**
 * Called when a server is connected to the server pool.
 */
public class ServerControllerConnectEvent implements NetworkEvent {

    public ServerControllerConnectEvent(ServerController controller){
        this.controller = controller;
        id = UUID.randomUUID();
    }

    public ServerControllerConnectEvent(ServerController controller, UUID id){
        this.controller = controller;
        this.id = id;
    }

    private UUID id;
    private ServerController controller;

    @Override
    public UUID getId() {
        return id;
    }

    public ServerController getServerController(){
        return controller;
    }
}
