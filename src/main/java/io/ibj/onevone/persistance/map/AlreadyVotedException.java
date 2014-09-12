package io.ibj.onevone.persistance.map;

import io.ibj.MattLib.PlayerException;

import java.util.UUID;

/**
 * Thrown if a user has voted on a map twice.
 */
public class AlreadyVotedException extends PlayerException {
    public AlreadyVotedException(UUID voter) {
        super(voter.toString()+" has already voted for this map.");
    }
}
