package me.jhim.retale.menus.neighborhood;

import me.jhim.retale.Retale;
import me.jhim.retale.menus.Menu;
import me.jhim.retale.menus.PlayerMenuUtility;
import me.jhim.retale.neighborhoods.Neighborhood;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class NeighborhoodGeneralMenu extends Menu {

    Retale retale;
    public NeighborhoodGeneralMenu(Retale retale, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.retale = retale;
    }

    @Override
    public String getMenuName() {
        return "&7&lNeighborhoods";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) return;
        new NeighborhoodPlotsMenu(retale, playerMenuUtility, retale.getNeighborhoodManager().getNeighborhoods().get(e.getSlot())).open();
    }

    @Override
    public void setMenuItems() {
        for(Neighborhood n : retale.getNeighborhoodManager().getNeighborhoods()) {
            this.getInventory()
                    .setItem(retale.getNeighborhoodManager().getNeighborhoods().indexOf(n),
                            retale.basicMenuItem(Material.GRASS_BLOCK, n.getName(), "Come settle in " + n.getName() + "!"));
        }
    }
}
