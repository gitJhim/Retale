package me.jhim.retale.structure;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Structure {

    private Location location;
    private List<StructureBlock> blocks = new ArrayList<StructureBlock>();

    public Structure(Location location) {
        this.location = location;
    }

    public abstract void initBlocks();

    public abstract Location getOrginalLocation();
    // Relative to the original location that it was placed on

    public List<StructureBlock> getBlocks() {
        return blocks;
    };

    public abstract String getStructureName();

    public abstract String getStructureLore();

    public abstract Material getStructureItemType();
}
