package io.ibj.onevone.server.local;

import com.google.common.collect.ImmutableSet;
import io.ibj.onevone.IllegalCallException;
import io.ibj.onevone.server.Server;
import io.ibj.onevone.server.ServerPool;
import io.ibj.onevone.server.event.EventController;
import io.ibj.onevone.server.event.LocalizedEventController;

import java.util.Iterator;
import java.util.Set;

/**
 * Represents a server pool with only one server. This server will always be online, and always be run on this server.
 * This server will never go offline.
 */
public class LocalServerPool implements ServerPool {

    private Server server;

    private EventController eventController = new LocalizedEventController();

    @Override
    public Set<Server> getServers() {
        return ImmutableSet.copyOf(new Server[]{server});
    }

    @Override
    public Server startNewServer() {
        throw new IllegalCallException();
    }

    @Override
    public EventController getEventController() {
        return eventController;
    }

    @Override
    public Iterator<Server> iterator() {
        return new Iterator<Server>() {
            boolean hasCalled = false;
            @Override
            public boolean hasNext() {
                return !hasCalled;
            }

            @Override
            public Server next() {
                return server;
            }

            @Override
            public void remove() {
                throw new IllegalCallException();
            }
        };
    }
}
