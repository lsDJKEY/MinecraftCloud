package io.ibj.onevone.server.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Joe on 8/31/2014.
 */
public class LocalizedEventController implements EventController {

    Map<Class<? extends NetworkEvent>, Set<EventHandler<?>>> eventHandlers = new HashMap<>();

    @Override
    public void register(EventHandler handler, Class<? extends NetworkEvent>... classes) {
        for(Class<? extends NetworkEvent> clazz : classes){
            Set<EventHandler<?>> handlers = eventHandlers.get(clazz);
            if(handlers == null){
                handlers = new HashSet<>();
                eventHandlers.put(clazz,handlers);
            }
            handlers.add(handler);
        }
    }

    @Override
    public void call(NetworkEvent event) {
        Set<EventHandler<?>> handlers = eventHandlers.get(event.getClass());
        if(handlers != null){
            for(EventHandler handler : handlers){
                handler.handle(event);
            }
        }
    }
}
