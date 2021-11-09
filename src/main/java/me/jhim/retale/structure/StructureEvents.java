package me.jhim.retale.structure;

import me.jhim.retale.Retale;

import me.jhim.retale.structure.structures.ParkingSpaceStructure;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;
import java.util.logging.Level;


public class StructureEvents implements Listener {

    Retale retale;

    public StructureEvents(Retale retale) {
        this.retale = retale;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onStructurePlaced(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        for (Structure structureBlock : retale.getStructureManager().getAllStructures()) {
            if (e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(structureBlock.getStructureName())) {

                Boolean vaildPlacement = true;
                Location blockLoc = e.getBlockPlaced().getLocation();
                blockLoc.getBlock().setType(Material.AIR);

                for(StructureBlock block : structureBlock.getBlocks()) {
                    Location pasteBlockLocation = new Location(blockLoc.getWorld(), blockLoc.getBlockX() + block.getX(), blockLoc.getBlockY() + block.getY(), blockLoc.getBlockZ() + block.getZ());
                    if(!pasteBlockLocation.getBlock().getType().equals(Material.AIR) || retale.getNeighborhoodManager().getPlotAtLocation(pasteBlockLocation.getChunk()) != retale.getStoreManager().getStorePlot(retale.getStoreManager().getStores().get(p.getUniqueId()))) {
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4 " + ChatColor.stripColor(structureBlock.getStructureName()) + " Structure could not be placed, please check the area around it!"));
                        vaildPlacement = false;
                        break;
                    }

                }

                if(vaildPlacement) {
                    for(StructureBlock block : structureBlock.getBlocks()) {
                        Location pasteBlockLocation = new Location(blockLoc.getWorld(), blockLoc.getBlockX() + block.getX(), blockLoc.getBlockY() + block.getY(), blockLoc.getBlockZ() + block.getZ());
                        pasteBlockLocation.getBlock().setType(block.getType());
                    }
                    retale.getStoreManager().getStores().get(p.getUniqueId()).addStructure(new ParkingSpaceStructure(blockLoc));
                }

            }
        }
    }
}

