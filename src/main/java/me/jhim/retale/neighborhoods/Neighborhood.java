package me.jhim.retale.neighborhoods;

import org.bukkit.Chunk;
import org.bukkit.Location;
import me.jhim.retale.stores.Store;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class Neighborhood {

    private String name;
    private Location spawn;
    private ArrayList<Plot> plots = new ArrayList<Plot>(16);

    public Neighborhood(String name) {
        for(int i = 0; i < 16; i++) {
            Plot plot = new Plot();
            plots.add(plot);
        }

        this.name = name;
    }

    public ArrayList<Plot> getPlots() {
        return plots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public Store getStoreOfChunk(Chunk c) {
        int[] coords = {c.getX(), c.getZ()};
        for(int i = 0; i < plots.size(); i++) {
            Plot p = plots.get(i);
            if(p.getChunks().isEmpty()) continue;
            for(int[] j : p.getChunks()) {
                if(Arrays.equals(j, coords)) {
                    return p.getStore();
                }
            }
        }
        return null;
    }

}
