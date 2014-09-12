package io.ibj.onevone.persistance.match.cassandra;

import com.datastax.driver.core.Row;
import com.google.gson.Gson;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.CInst;
import io.ibj.onevone.persistance.cassandra.CQLDeserializer;
import io.ibj.onevone.persistance.cassandra.DCEntity;
import io.ibj.onevone.persistance.match.Match;
import io.ibj.onevone.persistance.match.MatchResult;
import io.ibj.onevone.persistance.match.MatchState;
import io.ibj.onevone.persistance.match.MatchType;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Joe on 9/1/2014.
 */
public class DCMatchDeserializer implements CQLDeserializer<Match> {

    public DCMatchDeserializer(Gson gson){
        this.gson = gson;
    }

    Gson gson;

    @Override
    public Match deserialize(Row row, TableDefinition table, CInst session) {
        MatchState state = MatchState.fromLevel(row.getInt(DCMatch.Def.MATCH_STATE.i));
        UUID id = row.getUUID(DCEntity.Def.ID.i);
        Long timestamp = row.getLong(DCMatch.Def.TIMESTAMP.i);
        Set<UUID> participants = row.getSet(DCMatch.Def.PARTICIPANTS.i,UUID.class);
        UUID map = row.getUUID(DCMatch.Def.MAP_ID.i);
        Map<UUID,Integer> prevPow = row.getMap(DCMatch.Def.PREV_POWER.i,UUID.class,Integer.class);
        Map<UUID,Integer> resultPow = row.getMap(DCMatch.Def.RESULT_POWER.i,UUID.class,Integer.class);
        MatchType type = MatchType.fromName(row.getString(DCMatch.Def.MATCH_TYPE.i));
        MatchResult result = gson.fromJson(row.getString(DCMatch.Def.MATCH_RESULT.i),MatchResult.class);
        boolean exhibition = row.getBool(DCMatch.Def.EXHIBITION.i);
        if(state != MatchState.ENDED){
            //TODO: Actually figure out Match stuff
            return null;
        }
        else{
            return new DCMatch(id,table,session,timestamp,participants,map,prevPow,resultPow,type,state,result,exhibition);
        }
    }
}
