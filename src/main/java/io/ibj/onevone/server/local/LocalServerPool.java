package io.ibj.onevone.server.local;

import com.google.common.collect.ImmutableSet;
import io.ibj.onevone.IllegalCallException;
import io.ibj.onevone.server.Server;
import io.ibj.onevone.server.ServerController;
import io.ibj.onevone.server.ServerPool;
import io.ibj.onevone.server.event.EventController;
import io.ibj.onevone.server.event.LocalizedEventController;

import java.util.Iterator;
import java.util.Set;

/**
 * Represents a server controller pool with only one server. This server will always be online, and always be run on this server.
 * This server will never go offline.
 */
public class LocalServerPool implements ServerPool {

    private ServerController serverController;

    private EventController eventController = new LocalizedEventController();

    @Override
    public Set<ServerController> getServerControllers() {
        return ImmutableSet.copyOf(new ServerController[]{serverController});
    }

    @Override
    public Set<Server> getServers() {
        return ImmutableSet.copyOf(serverController.getServers());
    }

    @Override
    public ServerController startNewController() {
        throw new IllegalCallException();
    }

    @Override
    public EventController getEventController() {
        return eventController;
    }

    @Override
    public Iterator<ServerController> iterator() {
        return new Iterator<ServerController>() {
            boolean hasCalled = false;
            @Override
            public boolean hasNext() {
                return !hasCalled;
            }

            @Override
            public ServerController next() {
                return serverController;
            }

            @Override
            public void remove() {
                throw new IllegalCallException();
            }
        };
    }
}
