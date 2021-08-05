package me.jhim.retale;

import me.jhim.retale.commands.store.StoreCommandManager;
import me.jhim.retale.managers.StoreManager;
import me.jhim.retale.menus.MenuEvents;
import me.jhim.retale.menus.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Retale extends JavaPlugin {

    private final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<Player, PlayerMenuUtility>();

    private StoreManager storeManager;

    @Override
    public void onEnable() {
        loadManagers();
        registerCommands();
        registerEvents();
        getLogger().info("Retale up for sale!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Retale sale closed!");
    }

    private void loadManagers() {
        storeManager = new StoreManager(this);
    }
    private void registerCommands() {
        getCommand("store").setExecutor(new StoreCommandManager(this));
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new MenuEvents(this), this);
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String formatInfo(String message) {
        return ChatColor.translateAlternateColorCodes('&', "&7[&aRetale&7] &r" + message);
    }

    public PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if(playerMenuUtilityMap.containsKey(p)){
            return playerMenuUtilityMap.get(p);
        } else {
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);

            return playerMenuUtility;
        }
    }

    public ItemStack basicItem(Material type, String displayName) {
        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(format(displayName));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack basicMenuItem(Material type, String displayName, String description) {
        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(format("&7"));
        lore.add(format("&7- &o" + description));
        meta.setDisplayName(format(displayName));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public void populateAllGlass(Inventory inv) {
        int slots = inv.getSize();
        ItemStack item = basicItem(Material.BLACK_STAINED_GLASS_PANE, " ");
        for(int i = 0; i < slots; i++) {
            inv.setItem(i, item);
        }
    }

    public StoreManager getStoreManager() {
        return this.storeManager;
    }
}
