package io.ibj.onevone.persistance.player;

import io.ibj.onevone.persistance.DEntityStore;
import org.bukkit.entity.Player;

/**
 * Represents a collection of players. If the player is online, the store will return an instance of {@link DPlayer}, instead of {@link DOfflinePlayer}. When passed
 * a player, if will always return a player.
 */
public interface DPlayerStore extends DEntityStore<DOfflinePlayer> {
    /**
     * Returns the player object associated with the passed player
     * @param p Base player
     * @return  DPlayer implementation
     */
    public DPlayer get(Player p);
}
