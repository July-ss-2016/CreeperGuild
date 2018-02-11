package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;

/**
 * Created by July on 2018/1/20.
 */
public class GuildLeaveCommand implements GuildCommand {


    public boolean onCommand(CommandSender cs, String[] args) {
        return false;
    }

    public String getUsage() {
        return "quit";
    }

    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
