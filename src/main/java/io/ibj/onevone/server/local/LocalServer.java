package io.ibj.onevone.server.local;

import io.ibj.onevone.*;
import io.ibj.onevone.server.*;
import io.ibj.onevone.server.Process;
import io.ibj.onevone.server.event.EventController;
import io.ibj.onevone.server.event.LocalizedEventController;

import java.util.UUID;

/**
 * Created by Joe on 8/31/2014.
 */
public class LocalServer implements Server {

    public LocalServer(LocalServerController controller){
        this.controller = controller;
        id = UUID.randomUUID();
        state = ServerState.FREE;
        process = null;
    }

    private UUID id;
    private ServerState state;
    private LocalServerController controller;
    private LocalProcess process;
    private boolean markedForHalt = false;
    private LocalizedEventController eventController;

    @Override
    public ServerState getState() {
        return state;
    }

    @Override
    public Process getProcess() {
        return process;
    }

    @Override
    public void stopProcess() {
        if(state != ServerState.RUNNING){
            throw new IllegalStateException("Server not running a process, the process is initializing, or the server is offline.");
        }
        try{
            process.stop();
        }
        catch(Exception e){
            OvO.getI().handleError(e);
            try{
                process.stopFallback();
            }
            catch(Exception ex){
                OvO.getI().handleError(ex);
            }
        }
        process = null;
        state = ServerState.FREE;
        if(markedForHalt){
            forceHalt();
        }
    }

    @Override
    public void forceHalt() {
        if(state != ServerState.FREE){
            if(state == ServerState.RUNNING){
                stopProcess();
            }
            else
            {
                throw new IllegalStateException("The server is either initializing or already stopped.");
            }
        }
        controller.removeServer(this);
        state = ServerState.OFFLINE;
    }

    @Override
    public void halt() {
        markedForHalt = true;
    }

    @Override
    public Process createNewProcess(ProcessInvoker invoker) {
        if(state != ServerState.FREE){
            throw new IllegalStateException("Server is already running a process, or has shut down.");
        }
        state = ServerState.INITIATION;
        LocalProcess p;
        try {
            p = invoker.createLocalProcess();
        }
        catch(Exception e){
            OvO.getI().handleError(e);
            state = ServerState.FREE;
            return null;
        }
        try{
            p.start();
        }
        catch(Exception e){
            OvO.getI().handleError(e);
            try {
                p.startFallback();
            }
            catch(Exception ex){
                OvO.getI().handleError(ex);
            }
            state = ServerState.FREE;
            return null;
        }
        state = ServerState.RUNNING;
        process = p;
        return p;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public EventController getEventController() {
        return eventController;
    }
}
