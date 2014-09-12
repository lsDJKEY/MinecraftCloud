package io.ibj.onevone.server.remote;

import io.ibj.onevone.server.event.NetworkEvent;

/**
 * Created by Joe on 9/10/2014.
 */
public interface NetEventTranslator {

    public byte[] toBytes(NetworkEvent event);
    public NetworkEvent fromBytes(byte[] bytes);

}
