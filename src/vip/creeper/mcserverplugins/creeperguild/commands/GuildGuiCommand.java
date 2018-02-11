package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.GuildAdmin;
import vip.creeper.mcserverplugins.creeperguild.GuildOwner;
import vip.creeper.mcserverplugins.creeperguild.GuildPlayer;
import vip.creeper.mcserverplugins.creeperguild.guis.GuildApproveGui;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildPlayerManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

/**
 * Created by July on 2018/2/9.
 */
public class GuildGuiCommand implements GuildCommand {
    private CreeperGuild plugin;
    private GuildPlayerManager guildPlayerManager;

    public GuildGuiCommand(CreeperGuild plugin) {
        this.plugin = plugin;
        this.guildPlayerManager = plugin.getGuildPlayerManager();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        if (args.length == 2) {
            Player player = (Player) cs;
            String playerName = player.getName();
            boolean isGuildPlayer = guildPlayerManager.isExistsGuildPlayer(player.getName());

            switch (args[1]) {
                case "approve":
                    if (!isGuildPlayer) {
                        MsgUtil.sendMsg(cs, "&c打开GUI失败: 您还没有加入公会!");
                        return true;
                    }

                    GuildPlayer guildPlayer = guildPlayerManager.getOrLoadGuildPlayer(playerName);

                    if (guildPlayer instanceof GuildOwner || guildPlayer instanceof GuildAdmin) {
                        new GuildApproveGui(guildPlayer.getGuild().getGid()).open(player);
                        return true;
                    }

                    MsgUtil.sendMsg(cs, "&c打开GUI失败: 您没有权限审批!");
                    return true;
            }
        }

        return false;
    }

    public String getUsage() {
        return "gui <approve>";
    }

    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
