package me.jhim.retale.menus.admin;

import me.jhim.retale.Retale;
import me.jhim.retale.menus.Menu;
import me.jhim.retale.menus.PlayerMenuUtility;
import me.jhim.retale.structure.Structure;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AdminStructuresMenu extends Menu {

    Retale retale;
    public AdminStructuresMenu(Retale retale, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.retale = retale;
    }

    @Override
    public String getMenuName() {
        return "&7&lAdmin Structures";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        p.getInventory().addItem(item);
    }

    @Override
    public void setMenuItems() {

        for(Structure structure : retale.getStructureManager().getAllStructures()) {
            this.getInventory().addItem(structureItem(structure));
        }

    }

    private ItemStack structureItem(Structure structure) {

        ItemStack item = new ItemStack(structure.getStructureItemType());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(retale.format(structure.getStructureLore()));
        meta.setLore(lore);
        meta.setDisplayName(retale.format(structure.getStructureName()));
        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }
}
