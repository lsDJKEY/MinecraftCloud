package io.ibj.onevone.persistance.team.cassandra;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.CInst;
import io.ibj.onevone.persistance.cassandra.DCEntityDirectStore;
import io.ibj.onevone.persistance.cassandra.DCNamedEntity;
import io.ibj.onevone.persistance.team.DTeam;
import io.ibj.onevone.persistance.team.DTeamStore;

/**
 * Created by Joe on 9/8/2014.
 */
public class DCTeamStore extends DCEntityDirectStore<DTeam> implements DTeamStore {
    public DCTeamStore(CInst session, TableDefinition table) {
        super(session, table, new DCTeamDeserializer());
    }

    @Override
    public DTeam getByName(String name) {
        return getOne(getSession().execute(QueryBuilder.select().from(getTable().getKeyspace(),getTable().getTable()).where(QueryBuilder.eq(DCNamedEntity.Def.NAME.i,QueryBuilder.bindMarker())),
                name));
    }
}
