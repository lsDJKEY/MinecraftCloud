package io.ibj.onevone.persistance.match;

import io.ibj.onevone.persistance.DEntityStore;
import io.ibj.onevone.persistance.DRankedEntity;

import java.util.List;

/**
 * Provides all matches by various query methods. Can also provide
 */
public interface MatchStore extends DEntityStore<Match> {

    /**
     * Returns all matches a certain player has participated in
     * @param player    Player to query
     * @return  All matches in chronological order the player has participated in, with index 0 being their first match
     */
    public List<Match> getMatchesFromParticipant(DRankedEntity player);


}
