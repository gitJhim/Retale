package me.jhim.retale.menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerMenuUtility {

    private Player owner;

    private String createStoreName;

    private Inventory lastMenu;

    public PlayerMenuUtility(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Inventory getLastMenu() {
        return lastMenu;
    }

    public void setLastMenu(Inventory lastMenu) {
        this.lastMenu = lastMenu;
    }

    public void setCreateStoreName(String createStoreName) {
        this.createStoreName = createStoreName;
    }

    public String getCreateStoreName() { return this.createStoreName; }

}
