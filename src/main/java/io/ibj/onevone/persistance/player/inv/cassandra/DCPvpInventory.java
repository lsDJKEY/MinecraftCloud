package io.ibj.onevone.persistance.player.inv.cassandra;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update;
import io.ibj.MattLib.utils.ItemStackUtils;
import io.ibj.onevone.OvO;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.CInst;
import io.ibj.onevone.persistance.cassandra.DCEntity;
import io.ibj.onevone.persistance.match.MatchType;
import io.ibj.onevone.persistance.player.DOfflinePlayer;
import io.ibj.onevone.persistance.player.inv.PvpInventory;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Cassandra implementation of {@link io.ibj.onevone.persistance.player.inv.PvpInventory}.
 */
public class DCPvpInventory extends DCEntity implements PvpInventory {
    protected DCPvpInventory(@NonNull UUID uuid, @NonNull TableDefinition table, @NonNull CInst session, @NonNull UUID player, @NonNull MatchType match, @NonNull String name, @NonNull Map<Integer, ItemStack> virtualInventory) {
        super(uuid, table, session);
        this.player = player;
        this.matchType = match;
        this.name = name;
        this.virtInv = virtualInventory;
    }

    private UUID player;

    @Override
    public DOfflinePlayer getPlayer() {
        return OvO.getI().getPlayers().getFromId(player);
    }

    private MatchType matchType;

    @Override
    public MatchType getMatchType() {
        return matchType;
    }

    private String name;

    @Override
    public String getName() {
        return name;
    }

    private PreparedStatement setNameStatement = null;

    @Override
    public void setName(String name) {
        getSession().execute(QueryBuilder.update(getTable().getKeyspace(),getTable().getTable())
            .with(QueryBuilder.set(Def.NAME.i,QueryBuilder.bindMarker()))
            .where(QueryBuilder.eq(DCEntity.Def.ID.i,QueryBuilder.bindMarker())),
                name,getId());
        this.name = name;
    }

    Map<Integer, ItemStack> virtInv = new HashMap<>();

    @Override
    public void loadIntoInventory(PlayerInventory inventory) {

        ItemStack[] regContents = new ItemStack[36];
        ItemStack[] armorContents = new ItemStack[4];

        for(Map.Entry<Integer,ItemStack> entry : virtInv.entrySet()){
            if(entry.getKey() < 36){
                regContents[entry.getKey()] = entry.getValue();
            }
            else{
                armorContents[entry.getKey()-36] = entry.getValue();
            }
        }

        inventory.setContents(regContents);
        inventory.setArmorContents(armorContents);
    }

    @Override
    public void saveFromInventory(PlayerInventory inventory) {
        ItemStack[] contents = inventory.getContents();
        for(int i = 0; i < contents.length; i++){
            if(contents[i] != null){
                virtInv.put(i,contents[i]);
            }
        }
        contents = inventory.getArmorContents();
        for(int i = 0; i < contents.length; i++){
            if(contents[i] != null){
                virtInv.put(i+36,contents[i]);
            }
        }

        Update update = QueryBuilder.update(getTable().getKeyspace(),getTable().getTable());
        Update.Assignments assignments = update.with();
        for(Map.Entry<Integer,ItemStack> entry : virtInv.entrySet()){
            assignments.and(QueryBuilder.set(Def.INVENTORY.i+"["+entry.getKey()+"]", ItemStackUtils.toString(entry.getValue(), OvO.getI().getGson())));
        }
        update.where(QueryBuilder.eq(DCEntity.Def.ID.i,getId()));
        getSession().execute(update);
    }

    @Override
    public void delete() {
        getSession().execute(QueryBuilder.delete().from(getTable().getKeyspace(),getTable().getTable())
                .where(QueryBuilder.eq(DCEntity.Def.ID.i,QueryBuilder.bindMarker())),getId());
    }

    public static enum Def{
        NAME,
        INVENTORY,
        MATCH_TYPE,
        OWNER;

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
