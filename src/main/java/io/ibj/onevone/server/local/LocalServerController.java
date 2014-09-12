package io.ibj.onevone.server.local;

import com.google.common.collect.ImmutableSet;
import io.ibj.onevone.IllegalCallException;
import io.ibj.onevone.server.Server;
import io.ibj.onevone.server.ServerController;
import io.ibj.onevone.server.event.EventController;
import io.ibj.onevone.server.event.LocalizedEventController;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Joe on 8/31/2014.
 */
public class LocalServerController implements ServerController {

    public LocalServerController(){
        uuid = UUID.randomUUID();
    }

    UUID uuid;

    Set<Server> servers = new HashSet<>();

    EventController eventController = new LocalizedEventController();

    @Override
    public Set<Server> getServers() {
        return ImmutableSet.copyOf(servers);
    }

    @Override
    public Server getServer(@NonNull UUID uuid) {
        for(Server s : servers){
            if(s.getId().equals(uuid)){
                return s;
            }
        }
        return null;
    }

    @Override
    public Boolean isConnected() {
        return true;    //The local server is ALWAYS connected.
    }

    @Override
    public void shutdown() {
        throw new IllegalCallException();
    }

    @Override
    public Server startNewServer() {
        LocalServer localServer = new LocalServer(this);
        servers.add(localServer);
        return localServer;
    }

    @Override
    public EventController getEventController() {
        return eventController;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public Iterator<Server> iterator() {
        return servers.iterator();
    }

    void removeServer(LocalServer localServer){
        servers.remove(localServer);
    }
}
