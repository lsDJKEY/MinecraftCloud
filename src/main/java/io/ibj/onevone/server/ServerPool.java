package io.ibj.onevone.server;

import io.ibj.onevone.server.event.Eventable;

import java.util.Set;

/**
 * Represents a collection of server controllers.
 */
public interface ServerPool extends Iterable<ServerController>, Eventable {

    /**
     * Returns all server controllers inside of this server controller pool
     * @return  Set of all server controllers
     */
    public Set<ServerController> getServerControllers();

    /**
     * Returns all servers hosted by all of the server controllers managed by this instance
     * @return  Set of all servers
     */
    public Set<Server> getServers();

    /**
     * Starts up a new server controller into this pool, if possible. Should throw an exception if starting a new controller is not possible
     * @return  Created controller
     */
    public ServerController startNewController();
}
