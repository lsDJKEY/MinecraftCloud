package io.ibj.onevone.persistance.match.cassandra;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.ibj.onevone.persistance.DRankedEntity;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.CInst;
import io.ibj.onevone.persistance.cassandra.DCEntityDirectStore;
import io.ibj.onevone.persistance.match.Match;
import io.ibj.onevone.persistance.match.MatchResult;
import io.ibj.onevone.persistance.match.MatchResultSerializer;
import io.ibj.onevone.persistance.match.MatchStore;

import java.util.List;

/**
 * Created by Joe on 9/1/2014.
 */
public class DCMatchStore extends DCEntityDirectStore<Match> implements MatchStore {

    Gson gson;

    public DCMatchStore(CInst session, TableDefinition table) {
        super(session, table, new DCMatchDeserializer(null));
        Gson gson = new GsonBuilder().registerTypeAdapter(MatchResult.class,new MatchResultSerializer()).create();

    }

    MatchResultSerializer serializer;

    public MatchResultSerializer getMatchResultSerializer(){
        return serializer;
    }

    @Override
    public List<Match> getMatchesFromParticipant(DRankedEntity player) {
        return get(getSession().execute(QueryBuilder.select().from(getTable().getKeyspace(),getTable().getTable())
            .where(QueryBuilder.in(DCMatch.Def.PARTICIPANTS.i,QueryBuilder.bindMarker())),player.getId()));
    }

}
