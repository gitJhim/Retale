package me.jhim.retale.structure.structures;

import me.jhim.retale.structure.Structure;
import me.jhim.retale.structure.StructureBlock;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpaceStructure extends Structure {

    public ParkingSpaceStructure(Location location) {
        super(location);
    }

    @Override
    public void initBlocks() {
        getBlocks().add(new StructureBlock(0, 0, 0, Material.BLACK_CONCRETE));
        getBlocks().add(new StructureBlock(1, 0, 0, Material.BLACK_CONCRETE));
        getBlocks().add(new StructureBlock(0, 0, 1, Material.BLACK_CONCRETE));
        getBlocks().add(new StructureBlock(1, 0, 1, Material.BLACK_CONCRETE));
        getBlocks().add(new StructureBlock(-1, 0, 0, Material.BLACK_CONCRETE));
        getBlocks().add(new StructureBlock(0, 0, -1, Material.BLACK_CONCRETE));
        getBlocks().add(new StructureBlock(1, 0, -1, Material.BLACK_CONCRETE));
        getBlocks().add(new StructureBlock(-1, 0, 1, Material.BLACK_CONCRETE));
        getBlocks().add(new StructureBlock(-1, 0, -1, Material.BLACK_CONCRETE));
    }

    @Override
    public Location getOrginalLocation() {
        return null;
    }

    @Override
    public String getStructureName() {
        return "Parking Space";
    }

    @Override
    public String getStructureLore() { return "&oPlace structure in the middle of a 4x3 hole."; }

    @Override
    public Material getStructureItemType() {
        return Material.BLACK_CONCRETE;
    }

}
