package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;

public interface GuildCommand {
    boolean onCommand(final CommandSender cs, final String[] args);

    String getUsage();

    boolean isOnlyPlayerCanExecute();
}
