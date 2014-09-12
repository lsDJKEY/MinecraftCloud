package io.ibj.onevone.persistance;

import io.ibj.onevone.persistance.match.MatchType;

/**
 * Represents an entity (player or team) that can be ranked against other entities.
 */
public interface DRankedEntity extends DNamedEntity {
    /**
     * Returns the specified ranking for the given match type
     * @param type Type to retrieve
     * @return  Score for the given type
     */
    public Integer getRating(MatchType type);

    /**
     * Sets the passed score as the new ranking for the passed match type
     * @param type  Match type to set
     * @param newRanking    Score for the given type
     */
    public void setRanking(MatchType type, Integer newRanking);

}
