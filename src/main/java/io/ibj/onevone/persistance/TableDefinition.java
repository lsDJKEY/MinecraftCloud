package io.ibj.onevone.persistance;

import lombok.Value;

/**
 * A @lombok.Value'd representation of a database keyspace and table pairing
 */
@Value
public class TableDefinition{
    String keyspace;
    String table;
}
