package io.ibj.onevone.persistance.map;

import io.ibj.onevone.persistance.DEntityStore;
import io.ibj.onevone.persistance.player.DOfflinePlayer;

import java.util.Set;

/**
 * Interface that allows for the retrieval of Map elements
 */
public interface MapStore extends DEntityStore<GameMap> {
    /**
     * Retrieves a map based on its common name. If no map is found, return null
     * @param name  Name to search for
     * @return Found map
     */
    public GameMap getFromName(String name);

    /**
     * Retrieves all maps made by a creator. If the creator has created no maps, an empty set will be returned
     * @param player    Creator of map
     * @return  Set of all maps found with that creator
     */
    public Set<GameMap> getFromCreator(DOfflinePlayer player);

}
