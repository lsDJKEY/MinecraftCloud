package io.ibj.onevone.persistance.match.cassandra;

import com.google.common.collect.ImmutableSet;
import io.ibj.onevone.OvO;
import io.ibj.onevone.persistance.DRankedEntity;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.CInst;
import io.ibj.onevone.persistance.cassandra.DCEntity;
import io.ibj.onevone.persistance.map.GameMap;
import io.ibj.onevone.persistance.match.Match;
import io.ibj.onevone.persistance.match.MatchResult;
import io.ibj.onevone.persistance.match.MatchState;
import io.ibj.onevone.persistance.match.MatchType;
import io.ibj.onevone.persistance.player.DOfflinePlayer;
import io.ibj.onevone.persistance.team.DTeam;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Models a Match within the Cassandra Database
 *
 */
public class DCMatch extends DCEntity implements Match {

    protected DCMatch(@NonNull UUID uuid, @NonNull TableDefinition table, @NonNull CInst session, @NonNull Long timestamp, Set<UUID> participants, @NonNull UUID mapId, Map<UUID, Integer> previous,
                      Map<UUID, Integer> resultant, @NonNull MatchType type, @NonNull MatchState state, MatchResult result, @NonNull Boolean exhibition ) {
        super(uuid, table, session);
        this.timestamp = timestamp;
        this.participants = participants;
        this.mapId = mapId;
        this.previous = previous;
        this.resultant = resultant;
        this.matchType = type;
        this.matchState = state;
        this.matchResult = result;
        this.exhibition = exhibition;
    }


    Long timestamp;
    Set<UUID> participants;
    UUID mapId;
    Map<UUID, Integer> previous;
    Map<UUID, Integer> resultant;
    MatchType matchType;
    MatchState matchState;
    MatchResult matchResult;
    boolean exhibition;

    @Override
    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public Set<DRankedEntity> getParticipants() {
        Set<DRankedEntity> ret = new HashSet<>();
        for(UUID uuid : participants){
            DOfflinePlayer p = OvO.getI().getPlayers().getFromId(uuid);
            if(p != null){
                ret.add(p);
            }
            else
            {
                DTeam t = OvO.getI().getTeamStore().getFromId(uuid);
                if(t != null){
                    ret.add(t);
                }
            }
        }
        return ImmutableSet.copyOf(ret);
    }

    @Override
    public Integer getPreviousPowers(DRankedEntity entity) {
        if(matchState == MatchState.FORMATION || matchState == MatchState.INITIATION){
            throw new IllegalStateException();
        }
        return previous.get(entity.getId());
    }

    @Override
    public Integer getResultantPowers(DRankedEntity entity) {
        if(matchState != MatchState.ENDED){
            throw new IllegalStateException();
        }
        return resultant.get(entity.getId());
    }

    @Override
    public GameMap getMap() {
        return OvO.getI().getMapStore().getFromId(mapId);
    }

    @Override
    public MatchType getMatchType() {
        return matchType;
    }

    @Override
    public MatchResult getResult() {
        if(matchState != MatchState.ENDED){
            throw new IllegalStateException();
        }
        return matchResult;
    }

    @Override
    public MatchState getState() {
        return matchState;
    }

    @Override
    public Boolean isExhibition() {
        return exhibition;
    }

    public static enum Def {

        TIMESTAMP,
        PARTICIPANTS,
        MAP_ID,
        PREV_POWER,
        RESULT_POWER,
        MATCH_TYPE,
        MATCH_STATE,
        MATCH_RESULT,
        EXHIBITION;

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
