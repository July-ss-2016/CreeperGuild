package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperguild.*;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildPlayerManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

/**
 * Created by July on 2018/1/20.
 */
public class GuildKickCommand implements GuildCommand {
    private GuildPlayerManager guildPlayerManager;

    public GuildKickCommand(CreeperGuild plugin) {
        this.guildPlayerManager = plugin.getGuildPlayerManager();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        String csName = cs.getName();

        if (args.length == 2) {
            String targetName = args[1];

            if (!guildPlayerManager.isExistsGuildPlayer(targetName)) {
                MsgUtil.sendMsg(cs, "&c踢人失败: 您还没有加入公会.");
                return true;
            }

            GuildPlayer csGuildPlayer = guildPlayerManager.getOrLoadGuildPlayer(csName);
            Guild guild = csGuildPlayer.getGuild();

            if (!(csGuildPlayer instanceof GuildOwner) && !(csGuildPlayer instanceof GuildAdmin)) {
                MsgUtil.sendMsg(cs, "&c踢人失败: 您不是管理员或会主.");
                return true;
            }

            if (!guild.isMember(targetName)) {
                MsgUtil.sendMsg(cs, "&c踢人失败: &e" + targetName + " &c不是您公会的成员!");
                return true;
            }

            MsgUtil.sendMsg(cs, guild.removeMember(targetName) ? "&d踢人成功!" : "&c踢人失败: 系统错误, 请联系管理员.");
            return true;
        }

        return false;
    }

    public String getUsage() {
        return "kick <玩家名>";
    }

    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
