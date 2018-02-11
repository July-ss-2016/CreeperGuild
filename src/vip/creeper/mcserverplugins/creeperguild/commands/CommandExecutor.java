package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.Guild;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

import java.util.HashMap;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {
    private HashMap<String, GuildCommand> commands;

    public CommandExecutor() {
        this.commands = new HashMap<>();
    }

    // 各个子命令都在这进行分配处理
    public boolean onCommand(final CommandSender cs, final Command cmd, final String lable, final String[] args) {
        if (!cs.hasPermission("CreeperGuild.use")) {
            MsgUtil.sendMsg(cs, "&c您没有使用权限!");
            return true;
        }

        if (args.length >= 1) {
            String firstArg = args[0].toLowerCase();

            if (commands.containsKey(firstArg)) {
                GuildCommand guildCmd = commands.get(firstArg);

                // 判断该命令是否必须由玩家来执行
                if (guildCmd.isOnlyPlayerCanExecute()) {
                    if (!(cs instanceof Player)) {
                        MsgUtil.sendMsg(cs, MsgUtil.ONLY_PLAYER_CAN_EXECUTE_CMD);
                        return true;
                    }
                }

                if (!guildCmd.onCommand(cs, args)) {
                    sendUsageMsg(cs, guildCmd);
                }

                return true;
            }
        }

        MsgUtil.sendMsg(cs, "&c指令错误!");
        MsgUtil.sendMsgsWithoutPrefix(cs, MsgUtil.CMD_HELP_MSGS);
        return true;
    }

    private void sendUsageMsg(CommandSender cs, GuildCommand cmd) {
        MsgUtil.sendMsg(cs, "&c指令错误: &e正确格式: /g " + cmd.getUsage() + "&c.");
    }

    //注册命令
    public void registerCommand(final String firstArg, final GuildCommand cmd) {
        commands.put(firstArg.toLowerCase(), cmd);
    }
}
