package io.ibj.onevone.match;

import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

/**
 * Created by Joe on 8/31/2014.
 */
@AllArgsConstructor
public class SaveHaltListener implements Listener {

    LocalMatch match;

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e){
        if(match.isActive()){
            if(e.getWorld().equals(match.myWorld)){
                e.setCancelled(true);
            }
        }
    }
}
