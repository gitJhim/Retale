package me.jhim.retale.structure;

import me.jhim.retale.structure.structures.ParkingSpaceStructure;

import java.util.ArrayList;
import java.util.List;

public class StructureManager {
    private List<Structure> allStructures = new ArrayList<Structure>();

    public StructureManager() {
        allStructures.add(new ParkingSpaceStructure());
    }

    public List<Structure> getAllStructures() {
        return allStructures;
    }

}
