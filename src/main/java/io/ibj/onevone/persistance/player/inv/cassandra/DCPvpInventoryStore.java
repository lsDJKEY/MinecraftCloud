package io.ibj.onevone.persistance.player.inv.cassandra;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.CInst;
import io.ibj.onevone.persistance.cassandra.CQLDeserializer;
import io.ibj.onevone.persistance.cassandra.DCEntityDirectStore;
import io.ibj.onevone.persistance.match.MatchType;
import io.ibj.onevone.persistance.player.DOfflinePlayer;
import io.ibj.onevone.persistance.player.inv.PvpInventory;
import io.ibj.onevone.persistance.player.inv.PvpInventoryStore;

import java.util.List;

/**
 * Created by Joe on 9/1/2014.
 */
public class DCPvpInventoryStore extends DCEntityDirectStore<PvpInventory> implements PvpInventoryStore {
    public DCPvpInventoryStore(CInst session, TableDefinition table) {
        super(session, table, (CQLDeserializer<PvpInventory>)((CQLDeserializer) new DCPvpInventoryDeserializer()));
    }

    PreparedStatement findInventory = null;

    @Override
    public PvpInventory get(DOfflinePlayer player, MatchType type, String name) {
        return getOne(getSession().execute(QueryBuilder.select().from(getTable().getKeyspace(),getTable().getTable())
                .where(QueryBuilder.eq(DCPvpInventory.Def.NAME.i,QueryBuilder.bindMarker()))
                .and(QueryBuilder.eq(DCPvpInventory.Def.OWNER.i,QueryBuilder.bindMarker()))
                .and(QueryBuilder.eq(DCPvpInventory.Def.MATCH_TYPE.i,QueryBuilder.bindMarker())),
                name,player.getId(),type.getName()));
    }
    @Override
    public List<PvpInventory> get(DOfflinePlayer player, MatchType type) {
        return get(getSession().execute(QueryBuilder.select().from(getTable().getKeyspace(),getTable().getTable())
            .where(QueryBuilder.eq(DCPvpInventory.Def.OWNER.i,QueryBuilder.bindMarker()))
            .and(QueryBuilder.eq(DCPvpInventory.Def.MATCH_TYPE.i,QueryBuilder.bindMarker())),player.getId(),type.getName()));
    }

}
