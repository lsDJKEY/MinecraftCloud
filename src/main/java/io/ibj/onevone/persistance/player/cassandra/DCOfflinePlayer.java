package io.ibj.onevone.persistance.player.cassandra;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.CInst;
import io.ibj.onevone.persistance.cassandra.DCEntity;
import io.ibj.onevone.persistance.cassandra.DCRankedEntity;
import io.ibj.onevone.persistance.match.MatchType;
import io.ibj.onevone.persistance.player.DOfflinePlayer;
import lombok.NonNull;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;

public class DCOfflinePlayer extends DCRankedEntity implements DOfflinePlayer {
    protected DCOfflinePlayer(@NonNull UUID uuid, @NonNull TableDefinition table, @NonNull CInst session, @NonNull String name, @NonNull Map<MatchType, Integer> ratingsMap, @NonNull Integer level) {
        super(uuid, table, session, name, ratingsMap);
        this.level = level;
    }

    Integer level;

    @Override
    public Integer getRankLevel() {
        return level;
    }

    @Override
    public void setRankLevel(Integer rank) {
        getSession().execute(updateOnTable().where(QueryBuilder.eq(DCEntity.Def.ID.i, QueryBuilder.bindMarker()))
                .with(QueryBuilder.set(Def.LEVEL.i, QueryBuilder.bindMarker())), getId(), rank);
        level = rank;
    }

    @Override
    public boolean isOnline() {
        return Bukkit.getPlayer(getId()) != null;
    }

    public static enum Def{
        LEVEL;

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
