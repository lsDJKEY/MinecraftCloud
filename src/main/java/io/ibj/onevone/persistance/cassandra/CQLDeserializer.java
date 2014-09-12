package io.ibj.onevone.persistance.cassandra;

import com.datastax.driver.core.Row;
import io.ibj.onevone.persistance.DEntity;
import io.ibj.onevone.persistance.TableDefinition;

/**
 * Represents a class that deserializes Cassandra rows back into Java objects.
 */
public interface CQLDeserializer<T extends DEntity> {

    /**
     * Deserializes a row from the row passed. If the row cannot be serialized, the implementing class should throw a runtime exception.
     * The calling class should catch this exception and handle it accordingly.
     * @param row   Row to deserialize
     * @param table Table to use
     * @param session Session to use
     * @return  The deserialized object
     */
    public T deserialize(Row row, TableDefinition table, CInst session);
}
