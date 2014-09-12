package io.ibj.onevone.persistance;

/**
 * Represents a entity that is named, also paired with a unique id
 */
public interface DNamedEntity extends DEntity {

    /**
     * Returns the name of the entity
     * @return  Name of the entity
     */
    public String getName();

    /**
     * Sets a new name to the entity
     * @param name  Name to set to the entity
     */
    public void setName(String name);

}
