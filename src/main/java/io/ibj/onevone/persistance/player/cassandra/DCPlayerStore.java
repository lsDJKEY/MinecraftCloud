package io.ibj.onevone.persistance.player.cassandra;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import io.ibj.MattLib.ThreadLevel;
import io.ibj.onevone.OvO;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.*;
import io.ibj.onevone.persistance.player.DOfflinePlayer;
import io.ibj.onevone.persistance.player.DPlayer;
import io.ibj.onevone.persistance.player.DPlayerStore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Joe on 8/25/2014.
 */
public class DCPlayerStore extends DCEntityDirectStore<DOfflinePlayer> implements DPlayerStore, Listener {

    Map<UUID, DCPlayer> onlinePlayers = new HashMap<>();

    public DCPlayerStore(CInst session, TableDefinition table) {
        super(session, table, (CQLDeserializer<DOfflinePlayer>)((CQLDeserializer) new DCOfflinePlayerDeserializer()));
    }

    @Override
    public DPlayer get(Player p) {
        return onlinePlayers.get(p.getUniqueId());
    }

    @Override
    public DOfflinePlayer getFromId(UUID id) {
        DCPlayer p = onlinePlayers.get(id);
        if(p == null){
            return getOne(getSession().execute(QueryBuilder.select()
                .from(getTable().getKeyspace(),getTable().getTable())
                .where(QueryBuilder.eq(DCEntity.Def.ID.i,QueryBuilder.bindMarker())),id));
        }
        return p;
    }

    /**
     * Handle when the player joins the server. This will handle all ban catches, name changes, and new players into the database.
     * @param e Pre login event to process
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncJoin(AsyncPlayerPreLoginEvent e){

        //TODO: Ban stuff
        DOfflinePlayer p = getFromId(e.getUniqueId());
        if(p != null){
            if(p.getName() != e.getName()){
                p.setName(e.getName());
            }
        }
        else
        {
            getSession().execute(QueryBuilder.insertInto(getTable().getKeyspace(),getTable().getTable())
                .value(DCEntity.Def.ID.i, QueryBuilder.bindMarker())
                .value(DCNamedEntity.Def.NAME.i, QueryBuilder.bindMarker())
                .value(DCPlayer.Def.LEVEL.i, QueryBuilder.bindMarker()),
                    e.getUniqueId(),e.getName(),0);
        }
    }

    /**
     * Cache player into the local UUID map
     * @param e PlayerJoinEvent to handle
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(final PlayerJoinEvent e){
        OvO.getI().runImmediatly(new Runnable() {
            @Override
            public void run() {
                DCPlayer p = (DCPlayer)getFromId(e.getPlayer().getUniqueId());    //Player is known to be online
                onlinePlayers.put(p.getId(),p);
            }
        }, ThreadLevel.ASYNC);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent e){
        onlinePlayers.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onKicked(PlayerKickEvent e){
        onlinePlayers.remove(e.getPlayer().getUniqueId());
    }

}
