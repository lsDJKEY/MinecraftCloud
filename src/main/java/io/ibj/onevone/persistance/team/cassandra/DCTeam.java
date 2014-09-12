package io.ibj.onevone.persistance.team.cassandra;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.collect.ImmutableSet;
import io.ibj.onevone.OvO;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.CInst;
import io.ibj.onevone.persistance.cassandra.DCEntity;
import io.ibj.onevone.persistance.cassandra.DCRankedEntity;
import io.ibj.onevone.persistance.match.MatchType;
import io.ibj.onevone.persistance.player.DOfflinePlayer;
import io.ibj.onevone.persistance.team.DTeam;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Joe on 9/2/2014.
 */
public class DCTeam extends DCRankedEntity implements DTeam {
    protected DCTeam(@NonNull UUID uuid, @NonNull TableDefinition table, @NonNull CInst session, @NonNull String name, @NonNull Map<MatchType, Integer> ratingsMap, @NonNull Set<UUID> members) {
        super(uuid, table, session, name, ratingsMap);
        this.members = members;
    }

    Set<UUID> members;

    @Override
    public Set<DOfflinePlayer> getMembers() {
        Set<DOfflinePlayer> memberSet = new HashSet<>();
        for(UUID member : members){
            memberSet.add(OvO.getI().getPlayers().getFromId(member));
        }
        return ImmutableSet.copyOf(memberSet);
    }

    @Override
    public void addMember(DOfflinePlayer toAdd) {
        getSession().execute(QueryBuilder.update(getTable().getKeyspace(),getTable().getTable())
            .with(QueryBuilder.add(Def.MEMBERS.i,QueryBuilder.bindMarker()))
            .where(QueryBuilder.eq(DCEntity.Def.ID.i,QueryBuilder.bindMarker())),
            toAdd.getId(),getId());
        members.add(toAdd.getId());
    }

    @Override
    public void removeMember(DOfflinePlayer toRemove) {
        getSession().execute(QueryBuilder.update(getTable().getKeyspace(),getTable().getTable())
            .where(QueryBuilder.eq(DCEntity.Def.ID.i,QueryBuilder.bindMarker()))
            .with(QueryBuilder.remove(Def.MEMBERS.i,QueryBuilder.bindMarker())),getId(),toRemove.getId());
        members.remove(toRemove.getId());
    }

    @Override
    public void destroy() {
        getSession().execute(QueryBuilder.delete().from(getTable().getKeyspace(),getTable().getTable())
            .where(QueryBuilder.eq(DCEntity.Def.ID.i,QueryBuilder.bindMarker())),getId());
    }

    public static enum Def{
        MEMBERS;

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
