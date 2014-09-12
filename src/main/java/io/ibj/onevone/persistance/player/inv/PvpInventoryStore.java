package io.ibj.onevone.persistance.player.inv;

import io.ibj.onevone.persistance.DEntityStore;
import io.ibj.onevone.persistance.match.MatchType;
import io.ibj.onevone.persistance.player.DOfflinePlayer;

import java.util.List;

/**
 * A basic interface that retrieves PvpInventories
 */
public interface PvpInventoryStore extends DEntityStore<PvpInventory> {
    /**
     * Returns a PvpInventory for the passed player with the selected game mode. If one is not found, a blank model pvpinventory should be
     * returned that inserts a configuration into the database if changed.
     * @param player    Player to retrieve for
     * @param type  Type of Match to retrieve for
     * @param name  Name of the inventory to retrieve
     * @return  PvpInventory of player for type
     */
    public PvpInventory get(DOfflinePlayer player, MatchType type, String name);

    /**
     * Returns all PvpInventories for the player with the passed match type. Will not include the default inventory configurations.
     * @param player    Player to retrieve for
     * @param type  Type of Match to retrieve for
     * @return  Grouping of all pvpinventories found
     */
    public List<PvpInventory> get(DOfflinePlayer player, MatchType type);
}
