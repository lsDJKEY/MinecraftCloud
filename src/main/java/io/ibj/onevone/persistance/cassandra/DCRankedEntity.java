package io.ibj.onevone.persistance.cassandra;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import io.ibj.onevone.persistance.DRankedEntity;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.match.MatchType;
import lombok.NonNull;

import java.util.Map;
import java.util.UUID;

/**
 * Cassandra implementation of {@link io.ibj.onevone.persistance.DRankedEntity}. Extends {@link DCNamedEntity} in order to cover lower level rankings.
 * Rankings are stored inside of the cassandra database using a mapping inside the "rankings" column.
 */
public class DCRankedEntity extends DCNamedEntity implements DRankedEntity {
    protected DCRankedEntity(@NonNull UUID uuid, @NonNull TableDefinition table, @NonNull CInst session, @NonNull String name, @NonNull Map<MatchType, Integer> ratingsMap) {
        super(uuid, table, session, name);
        this.ratingsMap = ratingsMap;
    }

    private Map<MatchType, Integer> ratingsMap;

    @Override
    public Integer getRating(MatchType type) {
        return ratingsMap.get(type);
    }


    @Override
    public void setRanking(MatchType type, Integer newRanking) {
        getSession().execute(QueryBuilder.update(getTable().getKeyspace(),getTable().getTable()).with(QueryBuilder.set(Def.RANKINGS.i+"["+QueryBuilder.bindMarker()+"]",QueryBuilder.bindMarker()))
                .where(QueryBuilder.eq(DCEntity.Def.ID.i,QueryBuilder.bindMarker())),type.getName(),newRanking,getId());
        ratingsMap.put(type,newRanking);
    }

    public static enum Def{
        RANKINGS;

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
