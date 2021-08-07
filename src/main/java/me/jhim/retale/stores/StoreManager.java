package me.jhim.retale.stores;

import me.jhim.retale.Retale;
import me.jhim.retale.menus.store.ConfirmStoreCreation;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoreManager {

    Retale retale;

    public StoreManager(Retale retale) {
        this.retale = retale;
    }

    public void createStore(String name, Player p) {
        p.sendMessage(retale.formatInfo("&7Crated store &a&l" + name + "&r&7!"));
    }

    public void createStoreNameAnvil(Player p) {
        new AnvilGUI.Builder().onComplete((player, text) -> {
            Pattern pattern = Pattern.compile("([0-9])");
            Matcher m = pattern.matcher(text);

            if(m.find()) {
                return AnvilGUI.Response.text("Name cannot include numbers!");
            } else if(text.contains(" ")) {
                return AnvilGUI.Response.text("Name cannot include spaces!");
            } else if(text.length() < 3) {
                return AnvilGUI.Response.text("Name too short!");
            } else if(text.length() > 16) {
                return AnvilGUI.Response.text("Name too long!");
            } else if(false) {
                return AnvilGUI.Response.text("That name is already in use!");
            }
            retale.getPlayerMenuUtility(p).setCreateStoreName(text);
            new ConfirmStoreCreation(retale, retale.getPlayerMenuUtility(p)).open();
            return AnvilGUI.Response.close();
                })
                .text("Store Name")
                .itemLeft(new ItemStack(Material.BRICKS))
                .title(retale.format("&7&lCreate store Name"))
                .plugin(retale)
                .open(p);
    }
}
