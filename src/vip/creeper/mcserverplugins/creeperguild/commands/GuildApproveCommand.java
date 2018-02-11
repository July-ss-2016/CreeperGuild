package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.Guild;
import vip.creeper.mcserverplugins.creeperguild.GuildPlayer;
import vip.creeper.mcserverplugins.creeperguild.GuildPlayerPermission;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildManager;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildPlayerManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

/**
 * Created by July on 2018/2/9.
 */
public class GuildApproveCommand implements GuildCommand {
    private GuildManager guildManager;
    private GuildPlayerManager guildPlayerManager;

    public GuildApproveCommand(CreeperGuild plugin) {
        this.guildManager = plugin.getGuildManager();
        this.guildPlayerManager = plugin.getGuildPlayerManager();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        if (args.length == 3) {
            String state = args[1]; // deny, allow
            String approverName = cs.getName(); // 审批者名字
            String targetName = args[2]; // 被审批者名字

            // 审批者判断
            if (!guildPlayerManager.isExistsGuildPlayer(approverName)) {
                MsgUtil.sendMsg(cs, "&c审批失败: 您还没有加入公会.");
                return true;
            }

            GuildPlayer approver = guildPlayerManager.getOrLoadGuildPlayer(approverName);
            Guild approverGuild = approver.getGuild();
            String gid = approverGuild.getGid();
            GuildPlayerPermission approverPer = approver.getPermission(); // 审批者权限

            if (approverPer != GuildPlayerPermission.OWNER && approverPer != GuildPlayerPermission.ADMIN) {
                MsgUtil.sendMsg(cs, "&c审批失败: 您没有权限审批.");
                return true;
            }

            if (!guildManager.isExistsUnapprovedPlayer(gid ,targetName)) {
                MsgUtil.sendMsg(cs, "&c审批失败: 审批请求不存在.");
                return true;
            }

            // 判断被审批者是否已经加入公会
            if (guildPlayerManager.isExistsGuildPlayer(targetName)) {
                MsgUtil.sendMsg(cs, "&c审批失败: 被审批者已经加入了一个公会.");
                guildManager.removeGuildUnapprovedPlayers(gid, targetName);
                return true;
            }

            // 拒绝审批
            if (state.equalsIgnoreCase("deny")) {
                MsgUtil.sendMsg(cs, "&c审批成功: &e" + targetName + " &c已被拒绝.");
                // 删除未审批玩家
                guildManager.removeGuildUnapprovedPlayers(gid, targetName);
                MsgUtil.sendMsg(Bukkit.getPlayer(targetName), "&e" +approverName + " &c拒绝了您的入会请求!");
                return true;
            }

            // 同意审批
            if (state.equalsIgnoreCase("allow")) {
                // 创建GuildPlayer
                if (!guildPlayerManager.createNewGuildPlayer(targetName, gid, GuildPlayerPermission.MEMBER)) {
                    MsgUtil.sendMsg(cs, "&c审批成功: 系统错误, 请联系管理员.");
                    return true;
                }

                // 添加成员
                approverGuild.addMember(guildPlayerManager.getOrLoadGuildPlayer(targetName));
                // 删除未审批玩家
                guildManager.removeGuildUnapprovedPlayers(gid, targetName);
                MsgUtil.sendMsg(cs, "&d审批成功: &e" + targetName + " &d已被同意.");
                MsgUtil.sendMsg(Bukkit.getPlayer(targetName), "&d您已通过审核, 欢迎来到 &e" + guildManager.getGuild(gid).getName() + " &d公会!");
                return true;
            }

            MsgUtil.sendMsg(cs, "&c审批失败: 参数错误.");
            return true;
        }

        return false;
    }

    public String getUsage() {
        return "approve <deny/allow> <玩家名>";
    }

    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
