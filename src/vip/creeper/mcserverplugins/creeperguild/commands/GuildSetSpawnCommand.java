package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.*;
import vip.creeper.mcserverplugins.creeperguild.managers.CmdConfirmManager;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildPlayerManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

/**
 * Created by July on 2018/2/10.
 */
public class GuildSetSpawnCommand implements GuildCommand {
    private CreeperGuild plugin;
    private Settings settings;
    private GuildPlayerManager guildPlayerManager;
    private CmdConfirmManager cmdConfirmManager;

    public GuildSetSpawnCommand(CreeperGuild plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettings();
        this.guildPlayerManager = plugin.getGuildPlayerManager();
        this.cmdConfirmManager = plugin.getCmdConfirmManager();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        Player player = (Player) cs;
        String playerName = cs.getName();

        if (!guildPlayerManager.isExistsGuildPlayer(playerName)) {
            MsgUtil.sendMsg(cs, "&c设置出生点失败: 您还没有加入公会.");
            return true;
        }

        GuildPlayer guildPlayer = guildPlayerManager.getOrLoadGuildPlayer(playerName);
        Guild guild = guildPlayer.getGuild();

        if (!(guildPlayer instanceof GuildOwner)) {
            MsgUtil.sendMsg(cs, "&c设置出生点失败: 您不是会主.");
            return true;
        }

        if (plugin.getVault().getBalance(player) < settings.getSetSpawnCostMoney()) {
            MsgUtil.sendMsg(cs, "&c设置出生点失败: 金币余额不足 &e" + settings.getSetSpawnCostMoney() + "元&c.");
            return true;
        }

        if (!cmdConfirmManager.isExistsRequest(playerName, CmdConfirmType.SET_SPAWN) || !cmdConfirmManager.getRequests(playerName).getCmdConfirmEntry(CmdConfirmType.SET_SPAWN).isConfirmed()) {
            //确认申请公会
            MsgUtil.sendMsg(cs, "&e您将花费 &c" + settings.getSetSpawnCostMoney() + "元 &e来设置公会出生点.");
            MsgUtil.tellConfirmRawMsg(player, "guild confirm SET_SPAWN");
            cmdConfirmManager.putRequest(playerName, CmdConfirmType.SET_SPAWN, "guild setSpawn");
            return true;
        }

        //扣金币
        plugin.getVault().withdrawPlayer(player, settings.getSetSpawnCostMoney());

        if (guild.setSpawnLoc(player.getLocation())) {
            MsgUtil.sendMsg(cs, "&d设置公会主城成功!");
            return true;
        }

        MsgUtil.sendMsg(cs, "&c设置出生点失败: 系统错误, 请联系管理员!");
        return true;
    }

    public String getUsage() {
        return "setSpawn";
    }

    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
