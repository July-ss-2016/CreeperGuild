package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.Guild;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildManager;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildPlayerManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

/**
 * Created by July on 2018/2/10.
 */
public class GuildSpawnCommand implements GuildCommand {
    private GuildManager guildManager;
    private GuildPlayerManager guildPlayerManager;

    public GuildSpawnCommand(CreeperGuild plugin) {
        this.guildManager = plugin.getGuildManager();
        this.guildPlayerManager = plugin.getGuildPlayerManager();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        Player player = (Player) cs;
        String playerName = cs.getName();

        if (args.length == 1) {
            if (!guildPlayerManager.isExistsGuildPlayer(playerName)) {
                MsgUtil.sendMsg(cs, "&c传送失败: 您没有加入公会.");
                return true;
            }

            player.chat("/guild spawn " + guildPlayerManager.getOrLoadGuildPlayer(playerName).getGuild().getGid());
            return true;
        }

        if (args.length == 2) {
            String gid = args[1];

            if (!guildManager.isExistsGuild(gid)) {
                MsgUtil.sendMsg(cs, "&c传送失败: GID不存在!");
                return true;
            }

            Guild guild = guildManager.getGuild(gid);
            Location spawnLoc = guild.getSpawnLoc();

            if (spawnLoc == null) {
                MsgUtil.sendMsg(cs, "&c传送失败: 您所在的工会还没有主城传送点, 快让会主创建个吧~");
                return true;
            }

            player.teleport(spawnLoc);
            MsgUtil.sendMsg(cs, "&d传送成功: &f" + guild.getIntroduction());
            return true;
        }

        return false;
    }

    public String getUsage() {
        return "spawn [GID]";
    }

    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
