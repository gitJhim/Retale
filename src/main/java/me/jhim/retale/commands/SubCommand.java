package me.jhim.retale.commands;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract List<String> getSubCommandArguments(Player player, String[] args);

    public abstract void execute(Player player, String args[]);
}
