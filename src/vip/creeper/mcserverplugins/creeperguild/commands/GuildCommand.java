package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface GuildCommand {
    boolean onCommand(final CommandSender cs, final Command cmd, final String lable, final String[] args);
}
