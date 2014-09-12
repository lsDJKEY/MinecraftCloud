package io.ibj.onevone.persistance.cassandra;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import io.ibj.onevone.persistance.DNamedEntity;
import io.ibj.onevone.persistance.TableDefinition;
import lombok.NonNull;

import java.util.UUID;

/**
 * Cassandra implementation of a {@link io.ibj.onevone.persistance.DEntity}. When setting a name, a prepared statement is made, making future changes to the name much faster.
 */
public class DCNamedEntity extends DCEntity implements DNamedEntity {
    protected DCNamedEntity(@NonNull UUID uuid, @NonNull TableDefinition table, @NonNull CInst session, @NonNull String name) {
        super(uuid, table, session);
        this.name = name;
    }

    String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(@NonNull String name) {
        getSession().execute(QueryBuilder.update(getTable().getKeyspace(),getTable().getTable())
                .with(QueryBuilder.set(Def.NAME.i, QueryBuilder.bindMarker()))
                .where(QueryBuilder.eq(DCEntity.Def.ID.i, QueryBuilder.bindMarker())),name,getId());
        this.name = name;
    }

    public static enum Def{
        NAME;

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
