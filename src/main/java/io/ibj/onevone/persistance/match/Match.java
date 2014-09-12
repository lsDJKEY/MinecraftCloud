package io.ibj.onevone.persistance.match;

import io.ibj.onevone.persistance.DEntity;
import io.ibj.onevone.persistance.DRankedEntity;
import io.ibj.onevone.persistance.map.GameMap;

import java.util.Set;

/**
 * Represents an abstract Match.
 */
public interface Match extends DEntity{

    /**
     *
     * @return  Time of when the match was initially created
     */
    public Long getTimestamp();

    /**
     *
     * @return All registered participants to the match. This will probably be returned as a {@link com.google.common.collect.ImmutableSet} instead of
     * a garden variety HashSet in order to prevent attempted mutation to the participants.
     */
    public Set<DRankedEntity> getParticipants();

    /**
     * Returns the power from the passed entity before the match.
     * @param entity    Entity to return previous power for
     * @return  Previous power for passed entity
     */
    public Integer getPreviousPowers(DRankedEntity entity);

    /**
     * Returns the resulting power from the passed entity before the match.
     * @param entity    Entity to return resultant power for
     * @return  Resultant power for passed entity
     */
    public Integer getResultantPowers(DRankedEntity entity);

    /**
     *
     * @return The map that the match was played on
     */
    public GameMap getMap();

    /**
     *
     * @return The type of PVP match this was.
     */
    public MatchType getMatchType();

    /**
     * NOTE: This method may in the future be changed to an object to accommodate statistics or more complex results.3
     * If the match is not finished, this will throw an {@link java.lang.IllegalStateException}
     * @return The winner of the match.
     */
    public MatchResult getResult();

    /**
     *
     * @return State of the match
     */
    public MatchState getState();

    /**
     *
     * @return If the match is an exhibition match
     */
    public Boolean isExhibition();

}
