package io.ibj.onevone.persistance.player.inv;

import io.ibj.onevone.persistance.DEntity;
import io.ibj.onevone.persistance.match.MatchType;
import io.ibj.onevone.persistance.player.DOfflinePlayer;
import org.bukkit.inventory.PlayerInventory;

/**
 * Represents a preconfigured PVP inventory for a player.
 */
public interface PvpInventory extends DEntity {

    /**
     *
     * @return The player that this inventory is configured for
     */
    public DOfflinePlayer getPlayer();

    /**
     *
     * @return The match type this inventory was designed for
     */
    public MatchType getMatchType();

    /**
     * Returns an individualized name for the owning player. This allows for multiple kits per
     * match type.
     * @return  PvpInventory name
     */
    public String getName();

    /**
     * Sets the individualized name for the owning player. Allows for multiple kits.
     * @param name  New name of the kit.
     */
    public void setName(String name);

    /**
     * Loads the inventory into the passed inventory
     * @param inventory Inventory to load
     */
    public void loadIntoInventory(PlayerInventory inventory);

    /**
     * Saves inventory into the DB for future use
     * @param inventory Inventory to save
     */
    public void saveFromInventory(PlayerInventory inventory);

    /**
     * Deletes this Inventory from the store
     */
    public void delete();
}
