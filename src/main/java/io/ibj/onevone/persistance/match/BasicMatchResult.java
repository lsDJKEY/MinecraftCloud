package io.ibj.onevone.persistance.match;

import io.ibj.onevone.OvO;
import io.ibj.onevone.persistance.DRankedEntity;
import io.ibj.onevone.persistance.player.DOfflinePlayer;

import java.util.UUID;

/**
 * Created by Joe on 9/1/2014.
 */
public class BasicMatchResult implements MatchResult{

    public BasicMatchResult(UUID victor){
        this.victor = victor;
    }

    private UUID victor;

    @Override
    public DRankedEntity getVictor() {
        DOfflinePlayer p = OvO.getI().getPlayers().getFromId(victor);
        if(p != null){
            return p;
        }
        else
        {
            return OvO.getI().getTeamStore().getFromId(victor);
        }
    }
}
