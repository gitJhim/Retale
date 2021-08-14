package me.jhim.retale.events;

import me.jhim.retale.Retale;
import me.jhim.retale.neighborhoods.Neighborhood;
import me.jhim.retale.stores.Store;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class NeighborhoodEvents implements Listener {

    Retale retale;
    public NeighborhoodEvents(Retale retale) {
        this.retale = retale;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Store chunkStore = null;
        if(e.getClickedBlock() == null) {
            return;
        }
        for(Neighborhood n : retale.getNeighborhoodManager().getNeighborhoods()) {
            chunkStore = n.getStoreOfChunk(e.getClickedBlock().getChunk());
            if(chunkStore != null) break;
        }
        if(chunkStore == null) {
            if(retale.getNeighborhoodManager().chunkPartOfPlot(e.getClickedBlock().getChunk())) {
                p.sendMessage(retale.formatInfo("&7This plot is unclaimed, to claim it please type &a/plot claim&7! &4(W.I.P)"));
                e.setCancelled(true);
            }
        } else if(chunkStore.getOwner().getUniqueId().toString() != e.getPlayer().getUniqueId().toString()) {
            p.sendMessage(retale.formatInfo("&cThis plot is already claimed by &a" + chunkStore.getName()));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        retale.getStoreManager().loadStore(e.getPlayer().getUniqueId());
    }
}
