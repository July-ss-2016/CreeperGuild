package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.CmdConfirmType;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.Guild;
import vip.creeper.mcserverplugins.creeperguild.Settings;
import vip.creeper.mcserverplugins.creeperguild.managers.CmdConfirmManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

public class GuildCreateCommand implements GuildCommand {
    private CreeperGuild plugin;
    private Settings settings;
    private CmdConfirmManager guildCmdConfirmManager;

    public GuildCreateCommand(CreeperGuild plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettings();
        this.guildCmdConfirmManager = plugin.getCmdConfirmManager();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        if (args.length == 2) {
            Player player = (Player) cs;
            String playerName = cs.getName();
            String guildName = args[1];
            int len = guildName.length();

            /*
            创建公会应该满足的条件：
            公会长度符合要求
            公会名没有重名
            个人没有公会
            有足够的钱
            个人没有加入任何公会
             */

            if (plugin.getGuildPlayerManager().isExistsGuildPlayer(playerName)) {
                MsgUtil.sendMsg(cs, "&c创建失败: 您已经创建或加入一个公会.");
                return true;
            }

            if (len > settings.getCreateNameMaxLen() || len < settings.getCreateNameMinLen()) {
                MsgUtil.sendMsg(cs, "&c创建失败: 工会名长度要求: &e" + settings.getCreateNameMinLen() + "<长度<" + settings.getCreateNameMaxLen() + "&c.");
                return true;
            }

            if (plugin.getVault().getBalance(player) < settings.getCreateCostMoney()) {
                MsgUtil.sendMsg(cs, "&c创建失败: 金币余额不足 &e" + settings.getCreateCostMoney() + "元&c.");
                return true;
            }

            for (Guild guild : plugin.getGuildManager().getGuilds()) {
                if (guild.getName().equalsIgnoreCase(guildName)) {
                    MsgUtil.sendMsg(cs, "&c创建失败: 公会重名, 请换一个公会名!");
                    return true;
                }
            }

            if (!guildCmdConfirmManager.isExistsRequest(playerName, CmdConfirmType.CREATE) || !guildCmdConfirmManager.getRequests(playerName).getCmdConfirmEntry(CmdConfirmType.CREATE).isConfirmed()) {
                //确认申请公会
                MsgUtil.sendMsg(cs, "&e您将花费 &c" + settings.getCreateCostMoney() + "元 &e来创建公会.");
                MsgUtil.tellConfirmRawMsg(player, "guild confirm CREATE");
                guildCmdConfirmManager.putRequest(playerName, CmdConfirmType.CREATE, "guild create " + guildName);
                return true;
            }

            //扣金币
            plugin.getVault().withdrawPlayer(player, settings.getCreateCostMoney());

            if (!plugin.getGuildManager().createGuild(cs.getName(), guildName)) {
                MsgUtil.sendMsg(cs, "&c创建失败: 未能预料的错误, 请联系管理员.");
                return true;
            }

            MsgUtil.broadcastMsg("&d恭喜玩家 &e" + playerName + " &d创建了 &e" + guildName + "(公会代码: " + plugin.getGuildManager().getPlayerOwnedGuild(playerName).getGid() + ")" + " &d公会!");
            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "create <公会名>";
    }

    @Override
    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
