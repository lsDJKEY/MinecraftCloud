package io.ibj.onevone.persistance.player.cassandra;

import com.datastax.driver.core.Row;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.*;
import io.ibj.onevone.persistance.match.MatchType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Joe on 8/31/2014.
 */
public class DCOfflinePlayerDeserializer implements CQLDeserializer<DCOfflinePlayer> {

    @Override
    public DCOfflinePlayer deserialize(Row row, TableDefinition table, CInst session) {
        UUID id = row.getUUID(DCEntity.Def.ID.i);
        Player p = Bukkit.getPlayer(id);
        Map<String, Integer> rankings = row.getMap(DCRankedEntity.Def.RANKINGS.i,String.class,Integer.class);
        Map<MatchType, Integer> rankingTranslaged = new HashMap<>();
        for(Map.Entry<String,Integer> rankingEntry : rankings.entrySet()){
            rankingTranslaged.put(MatchType.fromName(rankingEntry.getKey()),rankingEntry.getValue());
        }
        if(p != null){
            return new DCPlayer(p,table,session,rankingTranslaged,row.getInt(DCOfflinePlayer.Def.LEVEL.i));
        }
        else{
            return new DCOfflinePlayer(id,table,session,row.getString(DCNamedEntity.Def.NAME.i), rankingTranslaged,row.getInt(DCOfflinePlayer.Def.LEVEL.i));
        }
    }
}
