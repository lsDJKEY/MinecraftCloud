package io.ibj.onevone.persistance.player.inv.cassandra;

import com.datastax.driver.core.Row;
import io.ibj.MattLib.utils.ItemStackUtils;
import io.ibj.onevone.OvO;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.CInst;
import io.ibj.onevone.persistance.cassandra.CQLDeserializer;
import io.ibj.onevone.persistance.cassandra.DCEntity;
import io.ibj.onevone.persistance.match.MatchType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joe on 9/1/2014.
 */
public class DCPvpInventoryDeserializer implements CQLDeserializer<DCPvpInventory> {
    @Override
    public DCPvpInventory deserialize(Row row, TableDefinition table, CInst session) {
        MatchType type = MatchType.fromName(row.getString(DCPvpInventory.Def.MATCH_TYPE.i));
        Map<Integer,ItemStack> stackMap = new HashMap<>();
        Map<Integer, String> transitionMap = row.getMap(DCPvpInventory.Def.INVENTORY.i,Integer.class,String.class);
        for(Map.Entry<Integer,String> mapEntry : transitionMap.entrySet()){
            stackMap.put(mapEntry.getKey(),ItemStackUtils.toStack(mapEntry.getValue(), OvO.getI().getGson()));
        }
        return new DCPvpInventory(row.getUUID(DCEntity.Def.ID.i),
                table,
                session,
                row.getUUID(DCPvpInventory.Def.OWNER.i),
                type,
                row.getString(DCPvpInventory.Def.NAME.i),
                stackMap);
    }
}
