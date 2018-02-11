package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.CmdConfirmType;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.GuildOwner;
import vip.creeper.mcserverplugins.creeperguild.GuildPlayer;
import vip.creeper.mcserverplugins.creeperguild.managers.CmdConfirmManager;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildPlayerManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

/**
 * Created by July on 2018/2/10.
 */
public class GuildTpAcceptCommand implements GuildCommand {
    private GuildPlayerManager guildPlayerManager;
    private CmdConfirmManager cmdConfirmManager;

    public GuildTpAcceptCommand(CreeperGuild plugin) {
        this.guildPlayerManager = plugin.getGuildPlayerManager();
        this.cmdConfirmManager = plugin.getCmdConfirmManager();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        String playerName = cs.getName();
        Player player = (Player) cs;

        if (!guildPlayerManager.isExistsGuildPlayer(playerName)) {
            MsgUtil.sendMsg(player, "&c接受失败: 您还没加入公会.");
            return true;
        }

        if (!cmdConfirmManager.isExistsRequest(playerName, CmdConfirmType.TPAALL_ACCEPT)) {
            MsgUtil.sendMsg(player, "&c接受失败: 请求不存在.");
            return true;
        }

        if (!cmdConfirmManager.getRequests(playerName).getCmdConfirmEntry(CmdConfirmType.TPAALL_ACCEPT).isConfirmed()) {
            MsgUtil.sendMsg(player, "&c接受失败: 未确认的请求.");
            return true;
        }

        GuildPlayer guildPlayer = guildPlayerManager.getOrLoadGuildPlayer(playerName);
        GuildOwner guildOwner = guildPlayer.getGuild().getOwner();

        if (!guildPlayer.isOnline()) {
            MsgUtil.sendMsg(player, "&c接受失败: 会主未在线.");
            return true;
        }

        guildPlayer.getBukkitPlayer().teleport(guildOwner.getBukkitPlayer());
        MsgUtil.sendMsg(player, "&d接受成功: 已传送!");
        return true;
    }

    public String getUsage() {
        return null;
    }

    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
