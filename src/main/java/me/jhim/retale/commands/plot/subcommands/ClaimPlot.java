package me.jhim.retale.commands.plot.subcommands;

import me.jhim.retale.Retale;
import me.jhim.retale.commands.SubCommand;
import me.jhim.retale.menus.neighborhood.ConfirmNeighborhoodCreation;
import me.jhim.retale.neighborhoods.Plot;
import me.jhim.retale.stores.Store;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ClaimPlot extends SubCommand {

    Retale retale;
    public ClaimPlot(Retale retale) {
        this.retale = retale;
    }

    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public String getDescription() {
        return "Claim plot";
    }

    @Override
    public String getSyntax() {
        return "/plot claim";
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }

    @Override
    public void execute(Player player, String[] args) {
        createNeighborhoodNameAnvil(player);
    }

    private void createNeighborhoodNameAnvil(Player p) {

        if (retale.getStoreManager().playerHasStore(p)) {
            Store store = retale.getStoreManager().getStores().get(p.getUniqueId());

            if (store.isLoaded()) {
                p.sendMessage(retale.formatInfo("&4Your store is already loaded!"));

            } else {
                Plot plot = retale.getNeighborhoodManager().getPlotAtLocation(p.getLocation().getChunk());
                retale.getNeighborhoodManager().claimPlot(p, plot);
            }

            } else {
                p.sendMessage(retale.formatInfo("&4You cannot claim a plot without making a store with &a/store&4!"));
            }
    }
}
