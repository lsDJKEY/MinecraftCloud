package io.ibj.onevone.server;

/**
 * Represents the state of a game server.
 */
public enum  ServerState {
    /**
     * Represents a Server that has either timed out, or a server controller that had a planned shutdown.
     */
    OFFLINE,
    /**
     * Represents a server that is initiating a match, but hasn't started running yet.
     */
    INITIATION,
    /**
     * Represents a server that is currently running a match.
     */
    RUNNING,
    /**
     * Represents a server that does not currently have a match.
     */
    FREE

}
