   package io.ibj.onevone.persistance.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import io.ibj.onevone.persistance.DEntity;
import io.ibj.onevone.persistance.DEntityStore;
import io.ibj.onevone.persistance.TableDefinition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of an entity store. This store will not archive results, but will serve them up as they are found.
 */
public class DCEntityDirectStore<T extends DEntity> implements DEntityStore<T>{

    protected DCEntityDirectStore(CInst session, TableDefinition table, CQLDeserializer<T> deserializer){
        this.session = session;
        this.table = table;
        this.deserializer = deserializer;
    }

    @Getter
    @NonNull
    private CInst session;

    @Getter
    @NonNull
    private TableDefinition table;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    @NonNull
    protected CQLDeserializer<T> deserializer;

    @Override
    public T getFromId(UUID id) {
        return getOne(session.execute(QueryBuilder.select()
        .all()
        .from(table.getKeyspace(),table.getTable())
        .where(QueryBuilder.eq("id",id))));
    }

    protected List<T> get(ResultSet result) {
        List<Row> allRows = result.all();
        List<T> ret = new ArrayList<>();
        for(Row r : allRows){
            ret.add(deserializer.deserialize(r,getTable(),getSession()));
        }
        return ret;
    }

    protected T getOne(ResultSet result) {
        Row r = result.one();
        if(r == null){
            return null;
        }
        return deserializer.deserialize(r,getTable(),getSession());
    }
}
