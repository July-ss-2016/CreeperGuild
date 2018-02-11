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
public class GuildIntroductionSetCommand implements GuildCommand {
    private GuildPlayerManager guildPlayerManager;
    private CmdConfirmManager cmdConfirmManager;

    public GuildIntroductionSetCommand(CreeperGuild plugin) {
        this.guildPlayerManager = plugin.getGuildPlayerManager();
        this.cmdConfirmManager = plugin.getCmdConfirmManager();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        String playerName = cs.getName();
        Player player = (Player) cs;

        if (args.length >= 1 && args.length <= 2) {
            if (!guildPlayerManager.isExistsGuildPlayer(playerName)) {
                MsgUtil.sendMsg(cs, "&c设置失败: 您还没有加入公会.");
                return true;
            }

            GuildPlayer guildPlayer = guildPlayerManager.getOrLoadGuildPlayer(playerName);

            if (!(guildPlayer instanceof GuildOwner)) {
                MsgUtil.sendMsg(cs, "&c设置失败: 您不是会主!");
                return true;
            }

            if (args.length == 1 && (!cmdConfirmManager.isExistsRequest(playerName, CmdConfirmType.RM_INTRODUCTION) || !cmdConfirmManager.getRequests(playerName).getCmdConfirmEntry(CmdConfirmType.RM_INTRODUCTION).isConfirmed())) {
                cmdConfirmManager.putRequest(playerName, CmdConfirmType.RM_INTRODUCTION, "guild setIntro");
                MsgUtil.sendMsg(cs, "&e您将清除公会介绍信息.");
                MsgUtil.tellConfirmRawMsg(player, "guild confirm RM_INTRODUCTION");
                return true;
            }

            if (guildPlayer.getGuild().setIntroduction(args.length == 1 ? null : args[1])) {
                MsgUtil.sendMsg(cs, "&d设置成功!");
                return true;
            }

            MsgUtil.sendMsg(cs, "&c设置失败: 系统错误, 请联系管理员!");
            return true;
        }

        return false;
    }

    public String getUsage() {
        return "setIntro [介绍文字]";
    }

    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
