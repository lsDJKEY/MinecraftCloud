package io.ibj.onevone.persistance.team;

import io.ibj.onevone.persistance.DRankedEntity;
import io.ibj.onevone.persistance.player.DOfflinePlayer;

import java.util.Set;

/**
 * Represents a team of players that can be named and scored.
 */
public interface DTeam extends DRankedEntity {

    /**
     * Returns an immutable set of all of the members in the team. If the player is online, it will instead be returned as a implementation of a {@link io.ibj.onevone.persistance.player.DPlayer}. In addition, the isOnline() method of each player will return true.
     * @return All members of the team.
     */
    public Set<DOfflinePlayer> getMembers();

    /**
     * Adds a player to the team
     * @param toAdd Player to add
     */
    public void addMember(DOfflinePlayer toAdd);

    /**
     * Removes a player from the team
     * @param toRemove  Player to remove
     */
    public void removeMember(DOfflinePlayer toRemove);

    /**
     * Destroys this team from the database.
     */
    public void destroy();
}
