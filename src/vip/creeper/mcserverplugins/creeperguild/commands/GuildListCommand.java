package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.guis.GuildListGui;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperguild.utils.StrUtil;

/**
 * Created by July on 2018/1/20.
 */
public class GuildListCommand implements GuildCommand {
    private static GuildListGui guildListGui;

    static {
        guildListGui = new GuildListGui();

        CreeperGuild.getInstance().getGuildCacheManager().addCacheUpdateTask(guildListGui, 300L);
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        Player player = (Player) cs;

        if (args.length == 1) {
            player.chat("/guild list 1");
            return true;
        }

        if (args.length == 2) {
            int maxPageNum = guildListGui.getPageNum();

            if (maxPageNum == 0) {
                MsgUtil.sendMsg(player, "&c列表失败: 当前没有一个公会!");
                return true;
            }

            if (!StrUtil.isNumStr(args[1])) {
                MsgUtil.sendMsg(player, "&c列表失败: 页数必须为数字!");
                return true;
            }

            int page = Integer.parseInt(args[1]);

            if (page <= 0 || page > maxPageNum) {
                MsgUtil.sendMsg(player, "&c列表失败: &e页数要求: 1" + "<页数<" + guildListGui.getPageNum() + "&c.");
                player.closeInventory();
                return true;
            }

            guildListGui.open(player, page);
            return true;
        }

        return false;
    }

    public String getUsage() {
        return "list [页数]";
    }

    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
