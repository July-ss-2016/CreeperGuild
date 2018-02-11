package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperguild.*;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildPlayerManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

/**
 * Created by July on 2018/1/20.
 */
public class GuildAdminSetCommand implements GuildCommand {
    private GuildPlayerManager guildPlayerManager;
    private Settings settings;

    public GuildAdminSetCommand(CreeperGuild plugin) {
        this.guildPlayerManager = plugin.getGuildPlayerManager();
        this.settings = plugin.getSettings();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        String playerName = cs.getName();

        if (args.length == 2) {
            String targetName = args[1];

            if (!guildPlayerManager.isExistsGuildPlayer(playerName)) {
                MsgUtil.sendMsg(cs, "&c添加管理员失败: 您还没有公会.");
                return true;
            }

            GuildPlayer csGuildPlayer = guildPlayerManager.getOrLoadGuildPlayer(playerName);
            Guild guild = csGuildPlayer.getGuild();

            if (!(csGuildPlayer instanceof GuildOwner)) {
                MsgUtil.sendMsg(cs, "&c添加管理员失败: 您不是会主.");
                return true;
            }

            if (playerName.equals(targetName)) {
                MsgUtil.sendMsg(cs, "&c添加管理员失败: 您不能添加您自己.");
                return true;
            }

            if (!guild.isMember(targetName)) {
                MsgUtil.sendMsg(cs, "&c添加管理员失败: &e" + targetName + " &c不在您的工会中.");
                return true;
            }

            if (guild.isAdmin(targetName)) {
                MsgUtil.sendMsg(cs, "&c添加管理员失败: &e" + targetName + " &c已是管理员.");
                return true;
            }

            if (guild.getAdminNum() >= settings.getMaxAdminNum()) {
                MsgUtil.sendMsg(cs, "&c添加管理员失败: 您最多只能添加 &e" + settings.getMaxAdminNum() + "个 &c管理员!");
                return true;
            }

            guild.setAdmin(guildPlayerManager.getOrLoadGuildPlayer(targetName));
            MsgUtil.sendMsg(cs, "&c添加管理员成功!");
            return true;
        }

        return false;
    }

    public String getUsage() {
        return "setAdmin <玩家名>";
    }

    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
