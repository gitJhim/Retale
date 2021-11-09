package me.jhim.retale.menus.store;

import me.jhim.retale.Retale;
import me.jhim.retale.menus.Menu;
import me.jhim.retale.menus.PlayerMenuUtility;
import me.jhim.retale.stores.Store;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import me.jhim.retale.structure.Structure;


public class StoreStructureMenu extends Menu {

    Retale retale;
    public StoreStructureMenu(Retale retale, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.retale = retale;
    }

    @Override
    public String getMenuName() {
        return "&7&lStructures";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

    }

    @Override
    public void setMenuItems() {
        Player player = playerMenuUtility.getOwner();

        Store playerStore = retale.getStoreManager().getStores().get(player.getUniqueId());

        for(Structure structure : playerStore.getStructures()) {
            
        }
    }
}
