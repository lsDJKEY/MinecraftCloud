package io.ibj.onevone.server;

import io.ibj.onevone.persistance.DEntity;
import io.ibj.onevone.server.event.Eventable;

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
     * Forcibly kill a running match
     */
    public void stopProcess();

    /**
     * Forcibly halts the server. Will immediately kill whatever game the server is running, if one is running
     */
    public void forceHalt();

    /**
     * Schedules a server halt. This will halt the server as soon as the current match is over.
     */
    public void halt();


    /**
     * Creates a new process from the passed invoker. If the process is created on a remote machine,
     * @param invoker   Invoker to model process from.
     * @return  Process that was created.
     */
    public Process createNewProcess(ProcessInvoker invoker);

}
