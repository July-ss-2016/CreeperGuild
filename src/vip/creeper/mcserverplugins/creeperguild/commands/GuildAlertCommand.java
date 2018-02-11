package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.GuildOwner;
import vip.creeper.mcserverplugins.creeperguild.GuildPlayer;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildPlayerManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperguild.utils.StrUtil;

/**
 * Created by July on 2018/1/20.
 */
public class GuildAlertCommand implements GuildCommand {
    private GuildPlayerManager guildPlayerManager;

    public GuildAlertCommand(CreeperGuild plugin) {
        this.guildPlayerManager = plugin.getGuildPlayerManager();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        String playerName = cs.getName();

        if (args.length == 2) {
            String msg = args[1];

            if (!guildPlayerManager.isExistsGuildPlayer(playerName)) {
                MsgUtil.sendMsg(cs, "&c广播失败: 您还没有加入公会.");
                return true;
            }

            GuildPlayer guildPlayer = guildPlayerManager.getOrLoadGuildPlayer(playerName);

            if (!(guildPlayer instanceof GuildOwner)) {
                MsgUtil.sendMsg(cs, "&c广播失败: 您不是会主.");
                return true;
            }

            guildPlayer.getGuild().alert(StrUtil.translateColorCode("&c[会内广播] §f" + msg + " —— " + playerName));
            return true;
        }

        return false;
    }

    public String getUsage() {
        return "alert <消息>";
    }

    public boolean isOnlyPlayerCanExecute() {
        return false;
    }
}
