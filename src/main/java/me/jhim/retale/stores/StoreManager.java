package me.jhim.retale.stores;

import me.jhim.retale.Retale;
import me.jhim.retale.menus.store.ConfirmStoreCreation;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoreManager {

    Retale retale;

    HashMap<UUID, Store> stores = new HashMap<UUID, Store>();

    public StoreManager(Retale retale) {
        this.retale = retale;
    }

    public void createStore(String name, Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    String query = "INSERT INTO stores (name, owner, filepath) VALUES (?,?,?)";
                    PreparedStatement stmt = retale.getSQL().getConnection().prepareStatement(query);

                    stmt.setString(1, name);
                    stmt.setString(2, p.getUniqueId().toString());
                    stmt.setString(3, "WIP");

                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(retale);

        Store store = new Store(name, p);
        stores.put(p.getUniqueId(), store);
        p.sendMessage(retale.formatInfo("&7Crated store &a&l" + name + "&r&7! Load it in using &a/neighborhood&7!"));
    }

    public void loadStore(UUID uuid) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    String query = "SELECT * FROM stores WHERE owner = ?";
                    PreparedStatement stmt = retale.getSQL().getConnection().prepareStatement(query);

                    stmt.setString(1, uuid.toString());

                    ResultSet results = stmt.executeQuery();
                    if(results.next()) {
                        Store store = new Store(results.getString("name"), Bukkit.getPlayer(uuid));
                        stores.put(uuid, store);
                    } else {
                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(retale);
    }

    private boolean storeNameExists(String name) {
        try {
            String query = "SELECT * FROM stores WHERE name = ?";
            PreparedStatement stmt = retale.getSQL().getConnection().prepareStatement(query);
            stmt.setString(1, name);
            ResultSet results = stmt.executeQuery();
            return results.next() ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean playerHasStore(Player p) {
        return stores.containsKey(p.getUniqueId());
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
            } else if(storeNameExists(text)) {
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

    public HashMap<UUID, Store> getStores() {
        return stores;
    }
}
