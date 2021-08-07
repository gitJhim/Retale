package me.jhim.retale.neighborhoods;

import org.bukkit.Location;
import me.jhim.retale.stores.Store;
import java.util.ArrayList;

public class Neighborhood {

    private String name;
    private Location spawn;
    private ArrayList<Plot> plots = new ArrayList<Plot>(12);

    public Neighborhood(String name) {
        for(int i = 0; i < 12; i++) {
            Plot plot = new Plot();
            plots.add(plot);
        }

        this.name = name;
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

    public void addStore(Store store) {
        for(Plot p : plots) {
            if(p.isEmpty()) {
                p.setStore(store);
                return;
            }
        }
    }

    public void removeStore(Store store) {
        for(Plot p : plots) {
            if(p.getStore().equals(store)) {
                p.setStore(null);
            }
        }
    }

}
