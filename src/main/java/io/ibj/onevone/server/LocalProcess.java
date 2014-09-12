package io.ibj.onevone.server;

/**
 * Created by Joe on 8/31/2014.
 */
public interface LocalProcess extends io.ibj.onevone.server.Process {

    /**
     * Called whenever this process should be started. All events should IGNORE ALL EVENTS THAT DO NOT APPLY TO IT
     */
    public void start();

    /**
     * Called whenever this process should be stopped. Should clean up all items that were attached into the Bukkit system
     */
    public void stop();

    /**
     * Called when the process did not initiate as planned. (Threw an error)
     */
    public void startFallback();

    /**
     * Called when the process did not shut down as planned.
     */
    public void stopFallback();

}
