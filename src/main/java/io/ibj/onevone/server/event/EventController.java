package io.ibj.onevone.server.event;

/**
 * Represents a controller to handle all events.
 */
public interface EventController {

    /**
     * Registers an event handler to this event controller.
     * @param handler Handler to register
     * @param registerFor All classes to register this handler for
     */
    public void register(EventHandler handler, Class<? extends NetworkEvent>... registerFor);

    /**
     * Calls the event over the network
     * @param event Event to call
     */
    public void call(NetworkEvent event);

    /**
     * Called in the end of life for this event controller. Severs all possible network ties, and removes all event handlers.
     */
    public void cleanup();

}
