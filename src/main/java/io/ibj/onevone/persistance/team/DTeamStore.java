package io.ibj.onevone.persistance.team;

import io.ibj.onevone.persistance.DEntityStore;

/**
 * Stores a set of teams.
 */
public interface DTeamStore extends DEntityStore<DTeam> {

    /**
     * Returns a team by its name, if it exists
     * @param name  Name of team to search by
     * @return  Team found
     */
    public DTeam getByName(String name);

}
