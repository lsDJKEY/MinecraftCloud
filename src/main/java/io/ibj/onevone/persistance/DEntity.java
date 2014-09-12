package io.ibj.onevone.persistance;

import java.util.UUID;

/**
 * Represents an entity of any type that can be tracked by a unique id
 */
public interface DEntity {

    /**
     * Returns the unique ID of this entity - this ID cannot be changed
     * @return  ID of the entity
     */
    public UUID getId();

}
