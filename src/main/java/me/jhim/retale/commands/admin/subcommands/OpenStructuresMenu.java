package me.jhim.retale.commands.admin.subcommands;

import me.jhim.retale.Retale;
import me.jhim.retale.commands.SubCommand;
import me.jhim.retale.menus.admin.AdminStructuresMenu;
import org.bukkit.entity.Player;

import java.util.List;

public class OpenStructuresMenu extends SubCommand {

    Retale retale;
    public OpenStructuresMenu(Retale retale) {
        this.retale = retale;
    }

    @Override
    public String getName() {
        return "structures";
    }

    @Override
    public String getDescription() {
        return "Admin structures menu";
    }

    @Override
    public String getSyntax() {
        return "/admin structures";
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }

    @Override
    public void execute(Player player, String[] args) {
        new AdminStructuresMenu(retale, retale.getPlayerMenuUtility(player)).open();
    }
}
