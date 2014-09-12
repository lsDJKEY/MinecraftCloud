package io.ibj.onevone.server;

/**
 * Represents the state of a server.
 */
public enum  ServerState {
    /**
     * Represents a Server that has not sent its heartbeat to this server in beatPeriod*2 time.
     */
    OFFLINE_TIMEOUT,
    /**
     * Represents a server that has sent its listeners a shutdown signal, and is offline
     */
    OFFLINE_SHUTDOWN,
    /**
     * Represents the state of a server that is offline and who's heartbeat is healthy.
     */
    ONLINE


}
