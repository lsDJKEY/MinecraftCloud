package io.ibj.onevone.server;

import io.ibj.onevone.persistance.DEntity;
import io.ibj.onevone.server.event.Eventable;

import java.net.InetSocketAddress;

/**
 * Represents a server that hosts one match and map at a time.
 */
public interface Server extends DEntity, Eventable {

    /**
     * Returns the current state of the server.
     * @return  Current state of the server
     */
    public ServerState getState();

    /**
     * Returns the process that this server is currently running. Will return null if the server is Offline, or if it is free.
     * @return  Process currently running
     */
    public io.ibj.onevone.server.Process getProcess();

    /**
     * Kills the process that is currently running and stops the server.
     */
    public void kill();

    /**
     * Returns the InetSocketAddress for this server
     * @return
     */
    public InetSocketAddress getAddress();

}
