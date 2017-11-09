package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

import java.util.HashMap;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {
    private HashMap<String, GuildCommand> commands;

    public CommandExecutor() {
        this.commands = new HashMap<>();
    }

    //各个子命令都在这进行分配处理
    public boolean onCommand(final CommandSender cs, final Command cmd, final String lable, final String[] args) {
        if (args.length >= 1) {
            String firstArg = args[0].toLowerCase();

            if (commands.containsKey(firstArg)) {
                commands.get(firstArg).onCommand(cs, cmd, lable, args);
                return true;
            }

            MsgUtil.sendMsgToCsWithPrefix(cs, "&c指令错误!");
            return true;
        }

        return  false;
    }

    //注册命令
    public void registerCommand(final String firstArg, final GuildCommand cmd) {
        commands.put(firstArg.toLowerCase(), cmd);
    }
}
