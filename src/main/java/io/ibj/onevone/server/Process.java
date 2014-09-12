package io.ibj.onevone.server;

import io.ibj.onevone.persistance.DEntity;
import io.ibj.onevone.server.event.Eventable;

/**
 * Represents a process that a server can be assigned to run.
 */
public interface Process extends DEntity, Eventable {
    /**
     * Halts this process immediately.
     */
    public void halt();

    /**
     * Returns if this process is active and running
     * @return  If the process is active
     */
    public boolean isActive();

    /**
     * Server that is hosing this process
     * @return  This process's hosing server
     */
    public Server getServer();

}