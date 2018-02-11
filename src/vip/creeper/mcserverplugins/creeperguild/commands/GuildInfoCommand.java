package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.Guild;
import vip.creeper.mcserverplugins.creeperguild.GuildPlayer;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

import java.util.Collection;
import java.util.List;

/**
 * Created by July on 2018/1/20.
 */
public class GuildInfoCommand implements GuildCommand {
    private CreeperGuild plugin;

    public GuildInfoCommand(CreeperGuild plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        String playerName = cs.getName();

        //gid为空，显示自公会信息
        if (args.length == 1) {
            GuildPlayer guildPlayer = plugin.getGuildPlayerManager().getOrLoadGuildPlayer(playerName);

            if (guildPlayer == null) {
                MsgUtil.sendMsg(cs, "&c查询失败: 您还没有加入公会.");
                return true;
            }

            MsgUtil.sendClsMsg(cs);
            MsgUtil.sendMsgsWithoutPrefix(cs, getGuildInfoMsgs(guildPlayer.getGuild()));
            return true;
        }

        if (args.length == 2) {
            Guild guild = plugin.getGuildManager().getGuild(args[1]);

            if (guild == null) {
                MsgUtil.sendMsg(cs, "&c查询失败: GID不存在!");
                return true;
            }

            MsgUtil.sendClsMsg(cs);
            MsgUtil.sendMsgsWithoutPrefix(cs, getGuildInfoMsgs(guild));
            return true;
        }

        return false;
    }


    public String getUsage() {
        return "info [GID]";
    }

    public boolean isOnlyPlayerCanExecute() {
        return true;
    }

    private String[] getGuildInfoMsgs(Guild guild) {
        StringBuilder memberInfo = new StringBuilder();
        List<GuildPlayer> members = guild.getMembers();

        for (int i = 0; i < members.size(); i ++) {
            GuildPlayer member = members.get(i);

            memberInfo.append("[").append(member.getPermission().getChineseName()).append("] ").append(member.getName());

            if (i != members.size() -1) {
                memberInfo.append(", ");
            }
        }

        return new String[] {MsgUtil.getLineSegmentMsg(), "&eGID(公会代码): " + guild.getGid() + "  " + "名称: " + guild.getName() + "  " + "会主: " + guild.getOwner().getName() + "  " + "创建时间: " + MsgUtil.formatDate(guild.getCreationTime()),
                "&e成员数量: " + guild.getMemberNum() + "/" + guild.getMaxMemberNum() +  "  " + "&e综合排名: -1/-1" + "  " + "金币储备: ¥ -1" + "  " + "&e人均财富: ¥ " + guild.getPerCapitaMoney(), "&e成员信息: " + memberInfo.toString(), "&e公会介绍: " + guild.getIntroduction(), MsgUtil.getLineSegmentMsg()};
    }
}
