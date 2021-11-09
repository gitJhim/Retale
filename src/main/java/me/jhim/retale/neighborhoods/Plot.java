package me.jhim.retale.neighborhoods;

import me.jhim.retale.stores.Store;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Plot {

    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public boolean isEmpty() {
        return store == null;
    }

    private List<int[]> chunks = new ArrayList<int[]>();

    public void addChunk(int[] chunk) {
        chunks.add(chunk);
    }

    public List<int[]> getChunks() {
        return chunks;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld("stores"), getChunks().get(2)[0] * 16, 5, getChunks().get(2)[1] * 16);
    }
}
