package io.ibj.onevone.server;

import io.ibj.onevone.server.event.Eventable;

import java.util.Set;

/**
 * Represents a collection of server controllers.
 */
public interface ServerPool extends Iterable<Server>, Eventable {

    /**
     * Returns all servers within the ServerPool
     * @return  Set of all server controllers
     */
    public Set<Server> getServers();

    /**
     * Invokes a new process on a spare server. If a server has not been found, one is started, and this process WILL BLOCK UNTIL THE SERVER IS FOUND
     * @param definition Definition of the process to start on the remote server.
     * @return  Process created
     */
    public Process runProcess(ProcessDefinition definition);
}
