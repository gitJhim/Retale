package me.jhim.retale.neighborhoods;

import java.util.ArrayList;
import java.util.List;

public class NeighborhoodManager {
    private List<Neighborhood> neighborhoods = new ArrayList<Neighborhood>();

    public List<Neighborhood> getNeighborhoods() {
        return this.neighborhoods;
    }

    public void createNeighborhood(String name) {
        Neighborhood n = new Neighborhood(name);
        neighborhoods.add(n);
    }
}
