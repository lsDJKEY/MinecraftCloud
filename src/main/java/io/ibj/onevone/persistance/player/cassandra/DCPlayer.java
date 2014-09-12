package io.ibj.onevone.persistance.player.cassandra;

import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.CInst;
import io.ibj.onevone.persistance.match.MatchType;
import io.ibj.onevone.persistance.player.DPlayer;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Created by Joe on 8/25/2014.
 */
public class DCPlayer extends DCOfflinePlayer implements DPlayer {
    protected DCPlayer(@NonNull Player p, @NonNull TableDefinition table, @NonNull CInst session, @NonNull Map<MatchType, Integer> ratingsMap, @NonNull Integer ranking) {
        super(p.getUniqueId(), table, session, p.getName(), ratingsMap, ranking);
        this.p = p;
    }

    Player p;

    @Override
    public Player getPlayer() {
        return p;
    }
}
