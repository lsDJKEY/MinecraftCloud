package io.ibj.onevone.match;

import com.datastax.driver.core.Session;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import io.ibj.onevone.OvO;
import io.ibj.onevone.generation.SchematicChunkGenerator;
import io.ibj.onevone.persistance.TableDefinition;
import io.ibj.onevone.persistance.match.ActiveMatch;
import io.ibj.onevone.persistance.match.MatchResult;
import io.ibj.onevone.persistance.match.MatchState;
import io.ibj.onevone.persistance.match.MatchType;
import io.ibj.onevone.persistance.match.cassandra.DCActiveMatch;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Joe on 8/31/2014.
 */
public abstract class LocalMatch extends DCActiveMatch implements ActiveMatch, io.ibj.onevone.server.LocalProcess{

    World myWorld;

    CuboidRegion clipRegion;

    SaveHaltListener listener;

    protected LocalMatch(@NonNull UUID uuid, @NonNull TableDefinition table, @NonNull Session session, @NonNull Long timestamp, Set<UUID> participants, @NonNull UUID mapId, Map<UUID, Integer> previous, Map<UUID, Integer> resultant, @NonNull MatchType type, @NonNull MatchState state, MatchResult result, @NonNull Boolean exhibition) {
        super(uuid, table, session, timestamp, participants, mapId, previous, resultant, type, state, result, exhibition);
    }

    @Override
    public void start() {
        CuboidClipboard clip = getMap().getClip();
        clipRegion = new CuboidRegion(new Vector(),clip.getSize());
        myWorld = Bukkit.createWorld(new WorldCreator(getId().toString()).generator(new SchematicChunkGenerator(clip)));
        listener = new SaveHaltListener(this);
        OvO.getI().registerEvents(listener);
    }

    @Override
    public void stop() {
        if(Bukkit.getWorld(myWorld.getUID()) != null){
            Bukkit.unloadWorld(myWorld,false);
        }
        ChunkUnloadEvent.getHandlerList().unregister(listener);
    }

    @Override
    public void startFallback() {

    }

    @Override
    public void stopFallback() {

    }

    public boolean isInRegion(Location l){
        return clipRegion.contains(new Vector(l.getX(),l.getY(),l.getZ())) && myWorld == l.getWorld();
    }

}
