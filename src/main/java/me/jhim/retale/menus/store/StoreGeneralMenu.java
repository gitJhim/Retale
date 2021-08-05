package me.jhim.retale.menus.store;

import me.jhim.retale.Retale;
import me.jhim.retale.menus.Menu;
import me.jhim.retale.menus.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class StoreGeneralMenu extends Menu {

    Retale retale;
    public StoreGeneralMenu(Retale retale, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.retale = retale;
    }

    @Override
    public String getMenuName() {
        return "&7&lCreate Store";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        switch (e.getCurrentItem().getType()) {
            case BRICK:
                retale.getStoreManager().createStoreNameAnvil(p);
            default:
                break;
        }
    }

    @Override
    public void setMenuItems() {
        retale.populateAllGlass(this.getInventory());

        this.getInventory().setItem(4, retale.basicMenuItem(Material.BRICK, "&c&lCreate Store", "Set up shop in your own store!"));
    }
}
