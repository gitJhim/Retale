package me.jhim.retale.commands.store.subcommands;

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

public class StoreTeleport extends SubCommand {

    Retale retale;
    public StoreTeleport(Retale retale) {
        this.retale = retale;
    }

    @Override
    public String getName() {
        return "tp";
    }

    @Override
    public String getDescription() {
        return "Teleport to store";
    }

    @Override
    public String getSyntax() {
        return "/store tp";
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }

    @Override
    public void execute(Player p, String[] args) {
        if(!retale.getStoreManager().playerHasStore(p)) { p.sendMessage(retale.formatInfo("&4You do not have a store to teleport to!")); }
        if(!retale.getStoreManager().getStores().get(p.getUniqueId()).isLoaded()) { p.sendMessage(retale.formatInfo("&4Please load your store to teleport to it!")); }

        Plot playerPlot = retale.getStoreManager().getStorePlot(retale.getStoreManager().getStores().get(p.getUniqueId()));

        p.teleport(playerPlot.getLocation());
    }

}
