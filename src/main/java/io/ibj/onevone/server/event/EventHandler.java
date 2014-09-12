package io.ibj.onevone.server.event;

/**
 * Represents a code block to handle an event that was triggered by the network.
 */
public interface EventHandler<T extends NetworkEvent> {
    /**
     * Triggered when an event is called.
     * @param event Event that was called.
     */
    public void handle(T event);
}
