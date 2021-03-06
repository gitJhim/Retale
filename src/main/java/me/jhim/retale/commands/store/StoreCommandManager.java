package me.jhim.retale.commands.store;

import me.jhim.retale.Retale;
import me.jhim.retale.commands.SubCommand;
import me.jhim.retale.commands.store.subcommands.StoreTeleport;
import me.jhim.retale.menus.store.StoreGeneralMenu;
import me.jhim.retale.stores.Store;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StoreCommandManager implements TabExecutor {

    Retale retale;
    private ArrayList<SubCommand> subCommands = new ArrayList<SubCommand>();

    public StoreCommandManager(Retale retale) {
        this.retale = retale;

        subCommands.add(new StoreTeleport(retale));
        retale.getLogger().info("STORE COMMANDS LOADED");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player p = (Player) sender;
        if(!p.hasPermission("retale.commands.store")) {
            p.sendMessage(retale.format("&4You do not have permission for that!"));
            return false;
        }

        if(args.length > 0) {
            for (int i = 0; i < subCommands.size(); i++) {
                if(args[0].equalsIgnoreCase(subCommands.get(i).getName())) {
                    subCommands.get(i).execute(p, args);
                }
            }
        } else {
            if(retale.getStoreManager().playerHasStore(p)) {
                Store store = retale.getStoreManager().getStores().get(p.getUniqueId());
                if(store.isLoaded()) {
                    // manage store
                } else {
                    p.sendMessage(retale.formatInfo("&4Please load your store into a /neighborhood to manage it!"));
                }
            } else {
                new StoreGeneralMenu(retale, retale.getPlayerMenuUtility(p)).open();
            }
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> commands = new ArrayList<String>();
        if(args.length == 1) {
            for(int i = 0; i < subCommands.size(); i++) {
                commands.add(subCommands.get(i).getName());
            }
        } else if (args.length == 2) {
            for(int i = 0; i < subCommands.size(); i++) {
                if(args[0].equalsIgnoreCase(subCommands.get(i).getName())) {
                    return subCommands.get(i).getSubCommandArguments((Player) sender, args);
                }
            }
        }
        return commands;
    }

}
