package io.ibj.onevone.persistance;

import java.util.UUID;

/**
 * Represents an interface that can interact with
 */
public interface DEntityStore<T extends DEntity> {

    /**
     * Queries the database by the object's ID.
     * @param id    ID to query for
     * @return  Returns the found object
     */
    public T getFromId(UUID id);
}
