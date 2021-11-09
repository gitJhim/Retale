package me.jhim.retale.commands.neighborhood.subcommands;

import me.jhim.retale.Retale;
import me.jhim.retale.commands.SubCommand;
import me.jhim.retale.menus.neighborhood.ConfirmNeighborhoodCreation;
import me.jhim.retale.neighborhoods.Neighborhood;
import me.jhim.retale.neighborhoods.Plot;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SetPlotChunks extends SubCommand {

    Retale retale;

    public SetPlotChunks(Retale retale) {
        this.retale = retale;
    }

    @Override
    public String getName() {
        return "setplotchunks";
    }

    @Override
    public String getDescription() {
        return "Set the chunks for a plot in a neighborhood.";
    }

    @Override
    public String getSyntax() {
        return "/neighborhood setplotchunks";
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length > 2) {
            int neighborhoodNumber = Integer.parseInt(args[1]);
            Neighborhood n = retale.getNeighborhoodManager().getNeighborhoods().get(neighborhoodNumber);

            int plotNumber = Integer.parseInt(args[2]);
            Plot p = n.getPlots().get(plotNumber);

            for(int i = 0; i < around(player.getLocation().getChunk()).size(); i++) {
                p.addChunk(new int[]{around(player.getLocation().getChunk()).get(i).getX(), around(player.getLocation().getChunk()).get(i).getZ()});
            }

            for(int i = 0; i < p.getChunks().size(); i++) {
                int[] coords = p.getChunks().get(i);
                FileConfiguration neighborhoodConfig = retale.getNeighborhoodManager().getConfig();
                neighborhoodConfig.set("neighborhoods." + n.getName() + ".plots." + plotNumber + "." + i + ".X", coords[0]);
                neighborhoodConfig.set("neighborhoods." + n.getName() + ".plots." + plotNumber + "." + i + ".Z", coords[1]);
            }
            player.sendMessage(retale.formatInfo("&aSuccessfully set plot chunks for plot #" + plotNumber + " in " + n.getName()));
            retale.getNeighborhoodManager().saveConfig();
        }
    }

    private List<Chunk> around(Chunk origin) {
        World world = origin.getWorld();


        List<Chunk> chunks = new ArrayList<Chunk>();

        int oX = origin.getX();
        int oZ = origin.getZ();

        chunks.add(world.getChunkAt(oX - 1, oZ + 1)); // Top Left
        chunks.add(world.getChunkAt(oX - 1, oZ));        // Top Middle
        chunks.add(world.getChunkAt(oX - 1, oZ - 1)); // Top Right
        chunks.add(world.getChunkAt(oX,oZ + 1));        // Middle Left
        chunks.add(origin);                                // Middle
        chunks.add(world.getChunkAt(oX,oZ - 1));        // Middle Right
        chunks.add(world.getChunkAt(oX + 1, oZ + 1)); // Bottom Left
        chunks.add(world.getChunkAt(oX + 1, oZ));        // Bottom Middle
        chunks.add(world.getChunkAt(oX + 1, oZ - 1)); // Bottom Right

        return chunks;
    }

}
