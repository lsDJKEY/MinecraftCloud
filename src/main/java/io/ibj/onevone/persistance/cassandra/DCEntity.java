package io.ibj.onevone.persistance.cassandra;

import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update;
import io.ibj.onevone.persistance.DEntity;
import io.ibj.onevone.persistance.TableDefinition;
import lombok.NonNull;

import java.util.UUID;

/**
 * Cassandra implementation of {@link io.ibj.onevone.persistance.DEntity}. Stores basic object details such as the table definition, database session, and the object ID. The ID cannot be changed.
 */
public class DCEntity implements DEntity {

    protected DCEntity(@NonNull UUID uuid, @NonNull TableDefinition table, @NonNull CInst session){
        this.id = uuid;
        this.table = table;
        this.session = session;
    }

    @NonNull
    private UUID id;

    @NonNull
    private TableDefinition table;

    @NonNull
    private CInst session;

    @Override
    @NonNull
    public UUID getId() {
        return id;
    }

    @NonNull
    protected TableDefinition getTable(){
        return table;
    }

    @NonNull
    protected CInst getSession(){
        return session;
    }

    protected Update updateOnTable(){
        return QueryBuilder.update(getTable().getKeyspace(),getTable().getTable());
    }

    protected Clause eqId(){
        return QueryBuilder.eq(Def.ID.i,getId());
    }

    public static enum Def{
        ID;

        Def(){
            i = this.name().toLowerCase();
        }

        Def(String altName){
            i = altName;
        }

        public String i;

        public String toString(){
            return i;
        }
    }
}
