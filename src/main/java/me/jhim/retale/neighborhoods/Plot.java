package me.jhim.retale.neighborhoods;

import me.jhim.retale.stores.Store;
import org.bukkit.Chunk;

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
}
