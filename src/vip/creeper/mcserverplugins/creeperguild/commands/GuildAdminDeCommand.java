package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperguild.*;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildPlayerManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

/**
 * Created by July on 2018/1/20.
 */
public class GuildAdminDeCommand implements GuildCommand {
    private GuildPlayerManager guildPlayerManager;

    public GuildAdminDeCommand(CreeperGuild plugin) {
        this.guildPlayerManager = plugin.getGuildPlayerManager();
    }

    @Override
    public boolean onCommand(CommandSender cs, String[] args) {
        String csName = cs.getName();

        if (args.length == 2) {
            String targetName = args[1];

            if (!guildPlayerManager.isExistsGuildPlayer(csName)) {
                MsgUtil.sendMsg(cs, "&c删除管理员失败: 您还没有加入公会.");
                return true;
            }

            GuildPlayer csGuildPlayer = guildPlayerManager.getOrLoadGuildPlayer(csName); // 命令执行者的GP
            Guild guild = csGuildPlayer.getGuild(); // 命令执行者的Guild


            if (!(csGuildPlayer instanceof GuildOwner)) {
                MsgUtil.sendMsg(cs, "&c删除管理员失败: 您不是会主.");
                return true;
            }

            if (!guild.isAdmin(targetName)) {
                MsgUtil.sendMsg(cs, "&c删除管理员失败: &e" + targetName + " &c不是管理员.");
                return true;
            }

            GuildAdmin targetAdmin = (GuildAdmin) guildPlayerManager.getOrLoadGuildPlayer(targetName);

            if (guild.deAdmin(targetAdmin)) {
                MsgUtil.sendMsg(cs, "&d删除管理员成功.");
                return true;
            } else {
                MsgUtil.sendMsg(cs, "&c删除管理员失败: 系统错误, 请联系管理员!");
                return true;
            }
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "deAdmin <玩家名>";
    }

    @Override
    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
