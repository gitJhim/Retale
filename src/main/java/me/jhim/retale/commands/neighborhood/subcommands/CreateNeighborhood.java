package me.jhim.retale.commands.neighborhood.subcommands;

import me.jhim.retale.Retale;
import me.jhim.retale.commands.SubCommand;
import me.jhim.retale.menus.neighborhood.ConfirmNeighborhoodCreation;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CreateNeighborhood extends SubCommand {

    Retale retale;
    public CreateNeighborhood(Retale retale) {
        this.retale = retale;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a neighborhood";
    }

    @Override
    public String getSyntax() {
        return "/neighborhood create";
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }

    @Override
    public void execute(Player player, String[] args) {
        createNeighborhoodNameAnvil(player);
    }

    private void createNeighborhoodNameAnvil(Player p) {
        new AnvilGUI.Builder().onComplete((player, text) -> {

            retale.getPlayerMenuUtility(p).setCreateNeighborhoodName(text);
            new ConfirmNeighborhoodCreation(retale, retale.getPlayerMenuUtility(p)).open();
            return AnvilGUI.Response.close();
        })
                .text("Neighborhood Name")
                .itemLeft(new ItemStack(Material.NETHER_STAR))
                .title(retale.format("&7&lCreate neighborhood name"))
                .plugin(retale)
                .open(p);
    }
}
