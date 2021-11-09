package me.jhim.retale.stores;

import me.jhim.retale.structure.Structure;
import org.bukkit.entity.Player;

import java.util.List;

public class Store {

    private String name;
    private Player owner;
    private boolean loaded;
    private List<Structure> structures;

    public Store(String name, Player owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public List<Structure> getStructures() {
        return structures;
    }

    public void addStructure(Structure structure) {
        this.structures.add(structure);
    }
}
