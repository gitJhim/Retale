package me.jhim.retale.stores;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.adapter.impl.fawe.BukkitAdapter_1_17_1;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import me.jhim.retale.Retale;
import me.jhim.retale.menus.store.ConfirmStoreCreation;
import me.jhim.retale.neighborhoods.Neighborhood;
import me.jhim.retale.neighborhoods.Plot;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
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

    public void unLoadPlayer(Player p) {
        World world = Bukkit.getWorld("stores");
        Store store = getStores().get(p.getUniqueId());
        if(store == null) return;
        Plot storePlot = getStorePlot(store);
        saveStorePlot(store);

        final File storeSchematic = new File(retale.getDataFolder(), "\\storeSchems\\plainplot2.schem");

        try {
            Clipboard clipboard = ClipboardFormats.findByFile(storeSchematic).getReader(new FileInputStream(storeSchematic)).read();
            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(world), -1)) {

                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(((storePlot.getChunks().get(4)[0] * 16) + 7), 4, (storePlot.getChunks().get(4)[1] * 16) + 8))
                        .ignoreAirBlocks(false)
                        .build();

                Operations.complete(operation);
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to paste schematic!");
        }

        storePlot.setStore(null);
        store.setLoaded(false);
        stores.remove(p.getUniqueId());
    }

    private void saveStorePlot(Store store) {
        World world = Bukkit.getWorld("stores");

        Plot storePlot = getStorePlot(store);

        CuboidRegion region = new CuboidRegion(BukkitAdapter.adapt(world), BlockVector3.at(storePlot.getChunks().get(0)[0] * 16, 1, (storePlot.getChunks().get(0)[1] * 16) + 15),
                BlockVector3.at((storePlot.getChunks().get(8)[0] * 16) + 15, 255, storePlot.getChunks().get(8)[1] * 16));

        File file = new File(retale.getDataFolder(), "\\storeSchems\\" + store.getOwner().getUniqueId() + ".schem");

        BlockArrayClipboard clipboard = new BlockArrayClipboard(region);

        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(region.getWorld(), -1);

        ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(editSession, region, clipboard, region.getMinimumPoint());
        forwardExtentCopy.setRemovingEntities(true);
        Operations.complete(forwardExtentCopy);

        try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))) {
            writer.write(clipboard);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public CompletableFuture<Boolean> loadStorePlot(Store store) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        World world = Bukkit.getWorld("stores");

        Plot storePlot = getStorePlot(store);

        final File storeSchematic = new File(retale.getDataFolder(), "\\storeSchems\\" + store.getOwner().getUniqueId() + ".schem");

        if(storeSchematic == null) completableFuture.complete(false);

        try {
            Clipboard clipboard = ClipboardFormats.findByFile(storeSchematic).getReader(new FileInputStream(storeSchematic)).read();
            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(world), -1)) {
                editSession.setFastMode(true);

                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(storePlot.getChunks().get(2)[0] * 16, 1, storePlot.getChunks().get(2)[1] * 16))
                        .ignoreAirBlocks(false)
                        .copyEntities(true)
                        .build();

                Operations.complete(operation);
                completableFuture.complete(true);
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to paste schematic!");
        }
        return completableFuture;
    }

    public Plot getStorePlot(Store store) {
        for(Neighborhood n : retale.getNeighborhoodManager().getNeighborhoods()) {
            for(int i = 0; i < n.getPlots().size(); i++) {
                Plot p = n.getPlots().get(i);
                if(p.getStore() == null) continue;
                if(p.getStore().getName() == store.getName()) {
                    return p;
                }
            }
        }
        return null;
    }

}
