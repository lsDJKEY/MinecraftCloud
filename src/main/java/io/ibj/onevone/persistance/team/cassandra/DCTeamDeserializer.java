package io.ibj.onevone.persistance.team.cassandra;

import com.datastax.driver.core.Row;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.*;
import io.ibj.onevone.persistance.match.MatchType;
import io.ibj.onevone.persistance.team.DTeam;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Joe on 9/8/2014.
 */
public class DCTeamDeserializer implements CQLDeserializer<DTeam> {
    @Override
    public DCTeam deserialize(Row row, TableDefinition table, CInst session) {
        UUID id = row.getUUID(DCEntity.Def.ID.i);
        String name = row.getString(DCNamedEntity.Def.NAME.i);
        Map<String, Integer> rankings = row.getMap(DCRankedEntity.Def.RANKINGS.i,String.class,Integer.class);
        Map<MatchType, Integer> rankingTranslaged = new HashMap<>();
        for(Map.Entry<String,Integer> rankingEntry : rankings.entrySet()){
            rankingTranslaged.put(MatchType.fromName(rankingEntry.getKey()),rankingEntry.getValue());
        }
        Set<UUID> members = row.getSet(DCTeam.Def.MEMBERS.i,UUID.class);
        return new DCTeam(id,table,session,name,rankingTranslaged,members);
    }
}
