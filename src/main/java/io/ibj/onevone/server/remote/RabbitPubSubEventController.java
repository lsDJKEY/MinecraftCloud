package io.ibj.onevone.server.remote;

import com.rabbitmq.client.*;
import io.ibj.onevone.OvO;
import io.ibj.onevone.server.event.EventController;
import io.ibj.onevone.server.event.EventHandler;
import io.ibj.onevone.server.event.Eventable;
import io.ibj.onevone.server.event.NetworkEvent;
import io.ibj.onevone.server.local.LocalServer;
import io.ibj.onevone.server.remote.event.ObjectEOLEvent;
import io.ibj.onevone.server.remote.event.ObjectEOLEventHandler;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.*;

/**
 * This RabbitMQ topics
 */
public class RabbitPubSubEventController implements EventController {

    public RabbitPubSubEventController(LocalServer localServer, Eventable eventBase, Connection rabbitConnection, NetEventTranslator eventTranslator) {
        this.localSender = localServer.getId();
        this.connection = rabbitConnection;
        this.eventBase = eventBase.getId();
        this.eventTranslator = eventTranslator;
        register(new ObjectEOLEventHandler(this), ObjectEOLEvent.class);
    }

    UUID localSender;                                                   //Comparable UUID for incoming events. Used to make sure message replication is minimized.
    NetEventTranslator eventTranslator;                                 //Event translator to translate events from byte arrays to Event objects
    UUID eventBase;                                                     //UUID for the object we are representing. Used for naming channels.
    Channel activeChannel;                                              //Channel that is reserved for this EventController. Each event controller has one Channel specifically dedicated for it.
    String exchangeName;                                                //Name of the base exchange to publish events to.
    String queueName;                                                   //Name of the event receiving queue to pull events from.
    Connection connection;                                              //Connection of the channel used for the EventController.
    Map<Class<? extends NetworkEvent>, Set<EventHandler<?>>> eventMap;  //Map of all events registered to this Controller, paired with the key classtype.
    Consumer myConsumer = null;                                         //RabbitMQ consumer


    /**
     * Creates the activeChannel, if it does not already exist.
     */
    @SneakyThrows
    private void init(){
        if(activeChannel != null){
            return;
        }
        activeChannel = connection.createChannel();
        myConsumer = new DefaultConsumer(activeChannel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    NetworkEvent event = eventTranslator.fromBytes(body);
                    if (event != null) { //Make sure that our event is indeed decipherable, and we are able to use it
                        if (event.getSender() != localSender) {    //As long as this wasn't called locally, we need to call this
                            Set<EventHandler<?>> handlers = eventMap.get(event.getClass());  //Get our handlers
                            if (handlers != null) {  //If we do have handlers for this class
                                for (EventHandler t : handlers) {   //Loop through our handlers
                                    try {   //Safety net
                                        t.handle(event);    //Handle our event
                                    } catch (Exception e) {
                                        OvO.getI().handleError(e);  //Report the error to the authorities
                                    }
                                }
                            }
                        }
                    }
                }
                catch(Exception e){
                    OvO.getI().handleError(e);
                }
            }

            @Override
            public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                cleanup();
            }
        };
    }

    /**
     * Registers the handle to all of the classes passed. Progression of function:
     *
     *  1. Defines the queue if it has not already been defined.
     *  2. Defines the exchange if the exchange has not already been defined.
     *      (Should only really happen if this is the first EventController to do something with this entity)
     *  3. For each class we want to register for, do the following:
     *      a. Get all of the current handlers for this class type
     *      b. If we haven't defined any yet, register a bind from the main exchange to our subqueue, with a routingkey to accept the class name type of event.
     *      c. Add the handler to the handler list
     * @param handler Handler to register
     * @param registerFor All classes to register this handler for
     */
    @SafeVarargs
    @Override
    @SneakyThrows
    public final void register(EventHandler handler, Class<? extends NetworkEvent>... registerFor) {
        init(); //Make sure we are all set up to handle events
        if(queueName == null){
            queueName = activeChannel.queueDeclare().getQueue();
            activeChannel.basicConsume(queueName,true,myConsumer);
        }
        if(exchangeName == null){
            try {
                activeChannel.exchangeDeclarePassive(eventBase.toString());
            }
            catch(IOException e){
                activeChannel.exchangeDeclare(eventBase.toString(),"topic",false,true,new HashMap<String, Object>());
            }
            exchangeName = eventBase.toString();
        }
        for(Class<? extends NetworkEvent> e : registerFor){
            Set<EventHandler<?>> handlers = eventMap.get(e);
            if(handlers == null){
                //If we haven't had an event register for this event class yet...
                //We need to register a new topic from the exchange to our local queue.
                activeChannel.queueBind(queueName,exchangeName,e.getName());
                handlers = new HashSet<>();
                eventMap.put(e,handlers);
            }
            //Add our handler to the list
            handlers.add(handler);
        }
    }

    /**
     * Calls a network event over the network.
     *
     * Progression of the function:
     *  1.  Make sure that our exchange is alive and set up
     *  2.  Publish the event away across the exchange
     *  3.  Call any local events we may have registered.
     * @param event Event to call
     */
    @Override
    @SneakyThrows
    public void call(NetworkEvent event) {
        init(); //Make sure we are all set up to handle this stuff
        if(exchangeName == null){
            try {
                activeChannel.exchangeDeclarePassive(eventBase.toString()); //Lets try and see if the channel is already present
            }
            catch(IOException e){
                activeChannel.exchangeDeclare(eventBase.toString(),"topic",false,true,new HashMap<String, Object>());   //Channel threw a 404! Make the exchange
            }
            exchangeName = eventBase.toString();    //Save our base exchange for later
        }
        activeChannel.basicPublish(exchangeName,event.getClass().getName(), new AMQP.BasicProperties.Builder().build(),eventTranslator.toBytes(event)); //Publish out our event to the net
        Set<EventHandler<?>> localHandlers = eventMap.get(event.getClass());    //Make sure that we also call our local events. NOTE: May get called twice... caught on receive by filtering out our own sender ID events
        if(localHandlers != null){  //If we have some local events registered for this class
            for(EventHandler e : localHandlers){    //Call each of them, catching any errors from them
                try {
                    e.handle(event);
                }
                catch(Exception ex){
                    OvO.getI().handleError(ex);
                }
            }
        }
    }

    /**
     * Cleans up all possible hooks to rabbitMQ. Also called when a ObjectEOLEvent is sent over the network with this ID.
     * Removes all events in the eventMap, removes the RabbitMQ queue, and closes out our channel to RabbitMQ.
     */
    @SneakyThrows
    public void cleanup(){  //Removes all event bindings, removes our local queue, and closes our channel.
        eventMap.clear();   //Remove all of our event bindings

        if(activeChannel != null){  //If we do have a registered comm channel
            if(queueName != null){  //If we had a queue defined
                activeChannel.queueDelete(queueName);   //Remove our local queue
            }
            activeChannel.close();  //Close our channel
            activeChannel = null;   //Make it null, so no double close
        }
    }
}
