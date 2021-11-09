package me.jhim.retale.structure;

import org.bukkit.Material;

public class StructureBlock {

    private int x;
    private int y;
    private int z;
    private Material type;

    public StructureBlock(int x, int y, int z, Material type) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Material getType() {
        return type;
    }
}
