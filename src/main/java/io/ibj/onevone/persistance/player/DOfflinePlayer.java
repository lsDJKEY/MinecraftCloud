package io.ibj.onevone.persistance.player;

import io.ibj.onevone.persistance.DRankedEntity;

/**
 * Represents a player that may or may not be online.
 */
public interface DOfflinePlayer extends DRankedEntity {

    /**
     * NOTICE: May be in the future switched to an enum for safety, and readability
     * @return Rank level of the player
     */

    public Integer getRankLevel();


    /**
     * Sets the new donation ranking of the player
     * @param rank  Rank to set
     */
    public void setRankLevel(Integer rank);

    /**
     * Calls underlying Bukkit API to determine if the player is online. SHOULD be a regular old
     * @return If the player is online
     */
    public boolean isOnline();

}
