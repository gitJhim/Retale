package me.jhim.retale.menus;

import me.jhim.retale.Retale;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuEvents implements Listener {

    Retale retale;

    public MenuEvents(Retale retale) {
        this.retale = retale;
        retale.getLogger().info("Menu Events LOADED");
    }

    @EventHandler
    public void onMenuClicked(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;

        Player p = (Player) e.getWhoClicked();

        InventoryHolder holder = e.getClickedInventory().getHolder();

        // Checks if it is a custom menu
        if (holder instanceof Menu) {
            e.setCancelled(true);

            Menu menu = (Menu) holder;
            menu.handleMenu(e);
        }
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof Menu) {
            retale.getPlayerMenuUtility((Player) e.getPlayer()).setLastMenu(e.getInventory());
        }
    }
}
