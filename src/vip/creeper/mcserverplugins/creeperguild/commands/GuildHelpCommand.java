package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

/**
 * Created by July on 2018/2/10.
 */
public class GuildHelpCommand implements GuildCommand {
    public boolean onCommand(CommandSender cs, String[] args) {
        MsgUtil.sendMsgsWithoutPrefix(cs, MsgUtil.CMD_HELP_MSGS);
        return true;
    }

    public String getUsage() {
        return "help";
    }

    public boolean isOnlyPlayerCanExecute() {
        return false;
    }
}
