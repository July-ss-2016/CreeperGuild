package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.*;
import vip.creeper.mcserverplugins.creeperguild.managers.CmdConfirmManager;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildPlayerManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

import java.util.List;

/**
 * Created by July on 2018/1/20.
 */
public class GuildTpaAllCommand implements GuildCommand {
    private CreeperGuild plugin;
    private GuildPlayerManager guildPlayerManager;
    private CmdConfirmManager cmdConfirmManager;

    public GuildTpaAllCommand(CreeperGuild plugin) {
        this.plugin = plugin;
        this.guildPlayerManager = plugin.getGuildPlayerManager();
        this.cmdConfirmManager = plugin.getCmdConfirmManager();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        String csName = cs.getName();

        if (!guildPlayerManager.isExistsGuildPlayer(csName)) {
            MsgUtil.sendMsg(cs, "&c执行失败: 您还没有加入公会.");
            return true;
        }

        GuildPlayer senderGuildPlayer = guildPlayerManager.getOrLoadGuildPlayer(csName);
        Guild guild = senderGuildPlayer.getGuild();

        if (senderGuildPlayer instanceof GuildAdmin || senderGuildPlayer instanceof GuildOwner) {
            List<GuildPlayer> onlineMembers = guild.getOnlineMembers();

            for (GuildPlayer member : onlineMembers) {
                Player memberBukkitPlayer = member.getBukkitPlayer();

                cmdConfirmManager.putRequest(memberBukkitPlayer.getName(), CmdConfirmType.TPAALL_ACCEPT, "guild tpaall_accept");
                MsgUtil.sendMsg(memberBukkitPlayer, "&e会长 &c" + guild.getOwner().getName() + " &e向你发送了传送到他那的请求!");
                MsgUtil.tellConfirmRawMsg(memberBukkitPlayer, "guild confirm TPAALL_ACCEPT");
            }

            MsgUtil.sendMsg(cs, "&c执行成功: 您向 &e" + onlineMembers.size() + "个 &c在线会员发出了传送到您这的请求!");
            return true;
        }

        MsgUtil.sendMsg(cs, "&c执行失败: 您没有权限.");
        return true;
    }

    public String getUsage() {
        return "tpaAll";
    }

    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
