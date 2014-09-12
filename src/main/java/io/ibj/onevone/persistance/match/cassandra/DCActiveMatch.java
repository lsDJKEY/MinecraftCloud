package io.ibj.onevone.persistance.match.cassandra;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import io.ibj.onevone.persistance.DRankedEntity;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.DCEntity;
import io.ibj.onevone.persistance.match.ActiveMatch;
import io.ibj.onevone.persistance.match.MatchResult;
import io.ibj.onevone.persistance.match.MatchState;
import io.ibj.onevone.persistance.match.MatchType;
import lombok.NonNull;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Joe on 8/29/2014.
 */
public abstract class DCActiveMatch extends DCMatch implements ActiveMatch {

    protected DCActiveMatch(@NonNull UUID uuid, @NonNull TableDefinition table, @NonNull Session session, @NonNull Long timestamp, Set<UUID> participants, @NonNull UUID mapId, Map<UUID, Integer> previous, Map<UUID, Integer> resultant, @NonNull MatchType type, @NonNull MatchState state, MatchResult result, @NonNull Boolean exhibition) {
        super(uuid, table, session, timestamp, participants, mapId, previous, resultant, type, state, result, exhibition);
    }


    @Override
    public void addParticipant(DRankedEntity participant) {
        getSession().execute(QueryBuilder.update(getTable().getKeyspace(),getTable().getTable()).where(QueryBuilder.eq(DCEntity.Def.ID.i,QueryBuilder.bindMarker())).with(QueryBuilder.append(Def.PARTICIPANTS.i,QueryBuilder.bindMarker())),getId(),participant.getId());
        participants.add(participant.getId());
    }

    @Override
    public void removeParticipant(DRankedEntity participant) {
        getSession().execute(QueryBuilder.update(getTable().getKeyspace(),getTable().getTable()).where(QueryBuilder.eq(DCEntity.Def.ID.i,QueryBuilder.bindMarker())).with(QueryBuilder.remove(Def.PARTICIPANTS.i,QueryBuilder.bindMarker())),
                getId(),participant.getId());
        participants.remove(participant.getId());
    }

    @Override
    public void setState(MatchState state) {
        if(state.getLevel() <= matchState.getLevel()){
            throw new IllegalArgumentException();
        }
        getSession().execute(QueryBuilder.update(getTable().getKeyspace(),getTable().getTable()).where(QueryBuilder.eq(DCEntity.Def.ID.i,QueryBuilder.bindMarker()))
                .with(QueryBuilder.set(Def.MATCH_STATE.i,QueryBuilder.bindMarker())),getId(),state.getLevel());
        matchState = state;
    }

    @Override
    public void setResult(MatchResult result) {
        if(matchState == MatchState.ENDED){
            throw new IllegalStateException();
        }
        getSession().execute(QueryBuilder.update(getTable().getKeyspace(),getTable().getTable())
            .where(QueryBuilder.eq(DCEntity.Def.ID.i,QueryBuilder.bindMarker()))
            .with(QueryBuilder.set(Def.MATCH_RESULT.i,QueryBuilder.bindMarker())),getId(),result.toString());
        this.matchResult = result;
    }
}
