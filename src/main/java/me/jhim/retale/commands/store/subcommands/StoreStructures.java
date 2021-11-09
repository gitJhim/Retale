package me.jhim.retale.commands.store.subcommands;

import me.jhim.retale.Retale;
import me.jhim.retale.commands.SubCommand;
import org.bukkit.entity.Player;


import java.util.List;

public class StoreStructures extends SubCommand {

    Retale retale;
    public StoreStructures(Retale retale) {
        this.retale = retale;
    }

    @Override
    public String getName() {
        return "structures";
    }

    @Override
    public String getDescription() {
        return "View all of the structures in your store.";
    }

    @Override
    public String getSyntax() {
        return "/store structures";
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }

    @Override
    public void execute(Player player, String[] args) {

    }
}
