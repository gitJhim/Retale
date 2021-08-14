package me.jhim.retale.neighborhoods;

import me.jhim.retale.Retale;
import org.bukkit.Chunk;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class NeighborhoodManager {

    private Retale retale;
    private List<Neighborhood> neighborhoods = new ArrayList<Neighborhood>();
    private FileConfiguration neighborhoodConfig = null;
    private File configFile = null;
    public List<Neighborhood> getNeighborhoods() {
        return this.neighborhoods;
    }

    public NeighborhoodManager(Retale retale) {
        this.retale = retale;
        saveDefaultConfig();
        reloadConfig();
        loadNeighborhoods();
    }

    private void loadNeighborhoods() {
        ConfigurationSection sec = getConfig().getConfigurationSection("neighborhoods");
        if (sec == null) return;
        for(String key : sec.getKeys(false)){
            Neighborhood n = new Neighborhood(key);
            for(int j = 0; j < n.getPlots().size(); j++) {
                for(int k = 0; k < 9; k++) {
                    int x = neighborhoodConfig.getInt("neighborhoods." + n.getName() + ".plots." + j + "." + k + ".X");
                    int z = neighborhoodConfig.getInt("neighborhoods." + n.getName() + ".plots." + j + "." + k + ".Z");
                    n.getPlots().get(j).addChunk(new int[]{x, z});
                }
            }
            neighborhoods.add(n);
            retale.getLogger().log(Level.INFO, "LOADED NEIGHBORHOOD: " + key);
        }
    }

    public void reloadConfig() {
        if(configFile == null) {
            configFile = new File(retale.getDataFolder(), "neighborhoods.yml");
        }
        neighborhoodConfig = YamlConfiguration.loadConfiguration(configFile);

        InputStream defaultStream = retale.getResource("neighborhoods.yml");
        if(defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            neighborhoodConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if(neighborhoodConfig == null) {
            reloadConfig();
        }
        return neighborhoodConfig;
    }

    public void saveConfig() {
        if(neighborhoodConfig == null || configFile == null) {
            return;
        }

        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            retale.getLogger().log(Level.SEVERE, "Could not save config to  " + this.configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if(configFile == null) {
            configFile = new File(retale.getDataFolder(), "neighborhoods.yml");
        }

        if(!configFile.exists()) {
            retale.saveResource("neighborhoods.yml", false);
        }
    }

    public void createNeighborhood(String name) {
        Neighborhood n = new Neighborhood(name);
        neighborhoods.add(n);

        getConfig().set("neighborhoods." + name, "");
        saveConfig();
    }

    public boolean chunkPartOfPlot(Chunk c) {
        int[] coords = {c.getX(), c.getZ()};
        for(Neighborhood n : neighborhoods) {
            for(int i = 0; i < n.getPlots().size(); i++) {
                Plot p = n.getPlots().get(i);
                if(p.getChunks().isEmpty()) continue;
                for(int[] j : p.getChunks()) {
                    if(Arrays.equals(j, coords)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
