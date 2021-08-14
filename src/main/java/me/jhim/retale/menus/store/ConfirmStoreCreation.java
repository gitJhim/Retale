package me.jhim.retale.menus.store;

import me.jhim.retale.Retale;
import me.jhim.retale.menus.Menu;
import me.jhim.retale.menus.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ConfirmStoreCreation extends Menu {
    Retale retale;
    private String newStoreName;

    public ConfirmStoreCreation(Retale retale, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        newStoreName = playerMenuUtility.getCreateStoreName();
        this.retale = retale;
    }

    @Override
    public String getMenuName() {
        return "&aConfirm Isle Name";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        switch(e.getCurrentItem().getType()) {
            case GREEN_TERRACOTTA:
                p.closeInventory();
                p.sendMessage(retale.formatInfo("&7Creating &a&l" + newStoreName + "&r&7..."));
                retale.getStoreManager().createStore(newStoreName, p);
                break;
            case RED_TERRACOTTA:
                p.closeInventory();
                p.sendMessage(retale.formatInfo("&c&lStore Creation Cancelled"));
                break;
            default:
                break;
        }
    }

    @Override
    public void setMenuItems() {
        retale.populateAllGlass(inventory);

        ItemStack yes = retale.basicItem(Material.GREEN_TERRACOTTA, "&a&lYES");
        ItemStack no = retale.basicItem(Material.RED_TERRACOTTA, "&c&lNO");

        inventory.setItem(4, retale.basicItem(Material.OAK_SIGN, "&7Create store named &a&l" + newStoreName + "&r&7?"));

        inventory.setItem(10, yes); inventory.setItem(11, yes); inventory.setItem(12, yes);
        inventory.setItem(14, no); inventory.setItem(15, no); inventory.setItem(16, no);
    }
}
