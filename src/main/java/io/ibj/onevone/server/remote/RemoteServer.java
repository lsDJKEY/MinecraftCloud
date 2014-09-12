package io.ibj.onevone.server.remote;

import io.ibj.onevone.server.*;
import io.ibj.onevone.server.Process;
import io.ibj.onevone.server.event.EventController;

import java.util.UUID;

/**
 * Created by Joe on 9/11/2014.
 */
public class RemoteServer implements Server {

    UUID id;    //My ID
    EventController eventController;    //The server's event controller
    Long lastHeartbeat = 0L;            //Time of the last heartbeat received to this server
    boolean safeShutdown = false;       //Defines if the session shut down safely

    @Override
    public ServerState getState() {
        if(safeShutdown){
            return ServerState.OFFLINE_SHUTDOWN;
        }
        return

    }

    @Override
    public io.ibj.onevone.server.Process getProcess() {
        return null;
    }

    @Override
    public void stopProcess() {

    }

    @Override
    public void forceHalt() {

    }

    @Override
    public void halt() {

    }

    @Override
    public Process createNewProcess(ProcessInvoker invoker) {
        return null;
    }

    @Override
    public EventController getEventController() {
        return eventController;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
