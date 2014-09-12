package io.ibj.onevone;

import com.datastax.driver.core.Cluster;
import com.mongodb.Mongo;
import io.ibj.MattLib.JoePlugin;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.cassandra.CInst;
import io.ibj.onevone.persistance.map.MapStore;
import io.ibj.onevone.persistance.match.MatchStore;
import io.ibj.onevone.persistance.match.cassandra.DCMatchStore;
import io.ibj.onevone.persistance.player.DPlayerStore;
import io.ibj.onevone.persistance.player.cassandra.DCPlayerStore;
import io.ibj.onevone.persistance.player.inv.PvpInventoryStore;
import io.ibj.onevone.persistance.player.inv.cassandra.DCPvpInventoryStore;
import io.ibj.onevone.persistance.team.DTeamStore;
import io.ibj.onevone.persistance.team.cassandra.DCTeamStore;
import io.ibj.onevone.server.ServerPool;
import lombok.Getter;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Main plugin for 1v1/Duel server
 */
public class OvO extends JoePlugin {

    @Getter
    private static OvO i;

    @Getter private DPlayerStore players;

    @Getter private MatchStore matches;

    @Getter private PvpInventoryStore pvpInventories;

    @Getter private MapStore mapStore;

    @Getter private DTeamStore teamStore;

    @Getter private ServerPool serverPool;

    private Cluster cluster;

    private CInst session;

    private Mongo mongo;

    public void onEnable(){
        i = this;
        try {
            getFormatFile().loadFormats();
        } catch (IOException e) {
            handleError(e);
        }

        cluster = Cluster.builder()
                .addContactPoint("localhost")
                .build();
        session = new CInst();

        try {
            mongo = new Mongo("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        players = new DCPlayerStore(session,new TableDefinition("onevone","players"));
        matches = new DCMatchStore(session,new TableDefinition("onevone","matches"));
        pvpInventories = new DCPvpInventoryStore(session,new TableDefinition("onevone","pvpinventories"));
        teamStore = new DCTeamStore(session,new TableDefinition("onevone","teams"));
    }
}
