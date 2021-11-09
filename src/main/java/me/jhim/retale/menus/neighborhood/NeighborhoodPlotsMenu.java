package me.jhim.retale.menus.neighborhood;

import com.google.common.primitives.Ints;
import me.jhim.retale.Retale;
import me.jhim.retale.menus.Menu;
import me.jhim.retale.menus.PlayerMenuUtility;
import me.jhim.retale.neighborhoods.Neighborhood;
import me.jhim.retale.neighborhoods.Plot;
import me.jhim.retale.stores.Store;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class NeighborhoodPlotsMenu extends Menu {

    Retale retale;
    Neighborhood neighborhood;
    int[] plotSlots = {10, 12, 14, 16, 19, 21, 23, 25, 28, 30, 32, 34, 37, 39, 41, 43};
    public NeighborhoodPlotsMenu(Retale retale, PlayerMenuUtility playerMenuUtility, Neighborhood neighborhood) {
        super(playerMenuUtility);
        this.retale = retale;
        this.neighborhood = neighborhood;
    }

    @Override
    public String getMenuName() {
        return "&7&l" + neighborhood.getName() + "'s Plots";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch (e.getCurrentItem().getType()) {
            case CLAY:
                Player player = (Player) e.getWhoClicked();
                Plot plot = neighborhood.getPlots().get(Ints.indexOf(plotSlots, e.getSlot()));
                Store playerStore = retale.getStoreManager().getStores().get(player.getUniqueId());
                if(playerStore == null) {
                    player.sendMessage(retale.formatInfo("&cYou do not have a store to put on this plot! Create one by doing &a/store"));
                } else if(playerStore.isLoaded()) {
                    player.sendMessage(retale.formatInfo("&cYour store is already loaded! Unload your store by doing &a/store"));
                } else {
                    plot.setStore(playerStore);
                    retale.getStoreManager().loadStorePlot(playerStore).thenAccept(consumer -> {
                        Bukkit.getScheduler().runTask(retale, () -> {
                            // Run Sync
                            e.getWhoClicked().sendMessage(retale.formatInfo("&e&l" + playerStore.getName() + " &r&ahas been loaded!"));
                            e.getWhoClicked().teleport(retale.getStoreManager().getStorePlot(playerStore).getLocation());
                        });
                    });
                    playerStore.setLoaded(true);
                }
                player.closeInventory();
            default:
                break;
        }
    }

    @Override
    public void setMenuItems() { // dia @ 49
        retale.populateAllGlass(this.getInventory());
        ItemStack emptyPlot = new ItemStack(Material.CLAY);
        ItemMeta meta = emptyPlot.getItemMeta();
        meta.setDisplayName(retale.format("&7Empty Plot"));
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(retale.format("&a&lLEFT CLICK TO CLAIM"));
        meta.setLore(lore);
        emptyPlot.setItemMeta(meta);

        for(int i = 0; i < 16; i++) {
            Plot plot = neighborhood.getPlots().get(i);
            if(plot.isEmpty()) {
                this.getInventory().setItem(plotSlots[i], emptyPlot);
            } else {
                ItemStack store = new ItemStack(Material.BRICK_SLAB);
                meta = store.getItemMeta();
                meta.setDisplayName(retale.format("&a&l" + plot.getStore().getName()));
                lore.clear();
                lore.add(retale.format("&7Owner: &e" + plot.getStore().getOwner().getName()));
                lore.add("");
                lore.add(retale.format("&a&lLEFT CLICK TO VISIT"));
                meta.setLore(lore);
                store.setItemMeta(meta);
                this.getInventory().setItem(plotSlots[i], store);
            }
        }

        this.getInventory().setItem(49, retale.basicItem(Material.BARREL, retale.format("&a&lWAREHOUSE")));
    }
}