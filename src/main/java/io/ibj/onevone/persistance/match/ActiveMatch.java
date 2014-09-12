package io.ibj.onevone.persistance.match;

import io.ibj.onevone.persistance.DRankedEntity;
import io.ibj.onevone.server.event.Eventable;

/**
 * Represents a match that is being created and or being fought.
 *
 * This match is locked after the state is set to MatchState.ENDED. After this point, all functions should call a {@link java.lang.IllegalStateException}.
 */
public interface ActiveMatch extends Match, Eventable {

    /**
     * Adds a participant to the match. If the match is already out of initiation, this will throw an {@link java.lang.IllegalStateException}.
     * @param participant The player to add to the match.
     */
    public void addParticipant(DRankedEntity participant);

    /**
     * Removes a participant from the match. If the match is already out of initiation, this will throw an {@link java.lang.IllegalStateException}.
     * @param participant The player to remove from the match.
     */
    public void removeParticipant(DRankedEntity participant);

    /**
     * Sets the state of the PVP match. Will throw an {@link java.lang.IllegalStateException} if the match has already ended, or the state is moving backwards.
     * @param state New state of the Pvp match.
     */
    public void setState(MatchState state);

    /**
     * Sets the result of the Pvp match. Currently, this method only accepts a player as a winner, and may be changed in the future to a more broad object, allowing for the use of more statistics to be collected.
     * If this is called before the state of the match is ended, this will throw an {@link java.lang.IllegalStateException}. If the winner is changed twice, this will throw an {@link java.lang.IllegalStateException}.
     * @param result Sets the result of the match.
     */
    public void setResult(MatchResult result);



}
