package io.ibj.onevone.persistance.map.mongo;

import io.ibj.onevone.persistance.map.GameMap;
import io.ibj.onevone.persistance.map.MapStore;
import io.ibj.onevone.persistance.player.DOfflinePlayer;

import java.util.Set;
import java.util.UUID;

/**
 * Created by Joe on 9/1/2014.
 */
public class MongoMapStore implements MapStore {
    @Override
    public GameMap getFromName(String name) {
        return null;
    }

    @Override
    public Set<GameMap> getFromCreator(DOfflinePlayer player) {
        return null;
    }

    @Override
    public GameMap getFromId(UUID id) {
        return null;
    }
}
