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
public class GuildRemoveSpawnCommand implements GuildCommand {
    private GuildPlayerManager guildPlayerManager;
    private CmdConfirmManager cmdConfirmManager;

    public GuildRemoveSpawnCommand(CreeperGuild plugin) {
        this.guildPlayerManager = plugin.getGuildPlayerManager();
        this.cmdConfirmManager = plugin.getCmdConfirmManager();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        Player player = (Player) cs;
        String playerName = cs.getName();

        if (!guildPlayerManager.isExistsGuildPlayer(playerName)) {
            MsgUtil.sendMsg(cs, "&c删除主城出生点失败: 您还没有加入公会.");
            return true;
        }

        GuildPlayer guildPlayer = guildPlayerManager.getOrLoadGuildPlayer(playerName);
        Guild guild = guildPlayer.getGuild();

        if (!(guildPlayer instanceof GuildOwner)) {
            MsgUtil.sendMsg(cs, "&c删除主城出生点失败: 您不是会主.");
            return true;
        }

        if (guild.getSpawnLoc() == null) {
            MsgUtil.sendMsg(cs, "&c删除主城出生点失败: 您还没有设置过主城出生点.");
            return true;
        }

        if (!cmdConfirmManager.isExistsRequest(playerName, CmdConfirmType.RM_SPAWN_LOC) || !cmdConfirmManager.getRequests(playerName).getCmdConfirmEntry(CmdConfirmType.RM_SPAWN_LOC).isConfirmed()) {
            //确认申请公会
            MsgUtil.sendMsg(cs, "&e您将删除公会出生点(不返还金币).");
            MsgUtil.tellConfirmRawMsg(player, "guild confirm RM_SPAWN_LOC");
            cmdConfirmManager.putRequest(playerName, CmdConfirmType.RM_SPAWN_LOC, "guild rmSpawn");
            return true;
        }

        if (guild.setSpawnLoc(null)) {
            MsgUtil.sendMsg(cs, "&d删除主城出生点成功!");
            return true;
        }

        MsgUtil.sendMsg(cs, "&c删除主城出生点出生点失败: 系统错误, 请联系管理员!");
        return true;
    }

    @Override
    public String getUsage() {
        return "removeSpawn";
    }

    @Override
    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
