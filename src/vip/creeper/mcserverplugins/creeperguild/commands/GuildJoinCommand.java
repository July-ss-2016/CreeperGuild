package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.Guild;
import vip.creeper.mcserverplugins.creeperguild.GuildOwner;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildManager;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildPlayerManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

/**
 * Created by July on 2018/1/20.
 */
public class GuildJoinCommand implements GuildCommand{
    private GuildPlayerManager guildPlayerManager;
    private GuildManager guildManager;

    public GuildJoinCommand(CreeperGuild plugin) {
        this.guildManager = plugin.getGuildManager();
        this.guildPlayerManager = plugin.getGuildPlayerManager();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        String playerName = cs.getName();

        if (args.length == 2) {
            String gid = args[1];
            
            if (guildPlayerManager.isExistsGuildPlayer(playerName)) {
                MsgUtil.sendMsg(cs, "&c申请失败: 您已经在一个公会了.");
                return true;
            }

            if (!guildManager.isExistsGuild(gid)) {
                MsgUtil.sendMsg(cs, "&c申请失败: GID不存在.");
                return true;
            }

            if (guildManager.getGuildUnapprovedPlayers(gid).size() == 54) {
                MsgUtil.sendMsg(cs, "&c申请失败: 该工会申请人数过多, 请等待管理员审批一部分后再申请.");
                return true;
            }

            if (guildManager.isExistsUnapprovedPlayer(gid, playerName)) {
                MsgUtil.sendMsg(cs, "&c申请失败: 您已向该工会发出加入请求, 请耐心等待审批.");
                return true;
            }

            if (!guildManager.addGuildUnapprovedPlayers(gid, playerName)) {
                MsgUtil.sendMsg(cs, "&c申请失败: 系统错误, 请联系管理员.");
                return true;
            }

            MsgUtil.sendMsg(cs,"&d申请成功: 请等待管理员审批!");

            Guild guild = guildManager.getGuild(gid);
            GuildOwner guildOwner = guild.getOwner();

            if (guildOwner.isOnline()) {
                MsgUtil.sendMsg(guildOwner.getBukkitPlayer(), "&e" + playerName + " &c申请加入您的工会, 请及时处理!");
                return true;
            }

            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "join <GID>";
    }

    @Override
    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
