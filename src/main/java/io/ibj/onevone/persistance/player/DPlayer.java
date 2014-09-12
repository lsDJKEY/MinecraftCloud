package io.ibj.onevone.persistance.player;

import org.bukkit.entity.Player;

/**
 * Represents a player that is online, or at least at time of creation
 */
public interface DPlayer extends DOfflinePlayer {
    /**
     * Returns the Bukkit instance of the player
     * @return  Returns a Bukkit player
     */
    public Player getPlayer();
}
