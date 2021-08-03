package me.jhim.retale;

import org.bukkit.plugin.java.JavaPlugin;

public class Retale extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Retale up for sale!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Retale sale closed!");
    }
}
