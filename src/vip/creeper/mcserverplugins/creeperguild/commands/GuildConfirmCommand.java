package vip.creeper.mcserverplugins.creeperguild.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.CmdConfirmEntry;
import vip.creeper.mcserverplugins.creeperguild.CmdConfirmRequests;
import vip.creeper.mcserverplugins.creeperguild.CmdConfirmType;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.managers.CmdConfirmManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

import java.util.Objects;

/**
 * Created by July on 2018/2/8.
 */
public class GuildConfirmCommand implements GuildCommand {
    private CreeperGuild plugin;
    private CmdConfirmManager cmdConfirmManager;

    public GuildConfirmCommand(CreeperGuild plugin) {
        this.plugin = plugin;
        this.cmdConfirmManager = plugin.getCmdConfirmManager();
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        Player player = (Player) cs;
        String playerName = player.getName();

        if (args.length == 2) {
            boolean isValidType = false;

            for (CmdConfirmType type : CmdConfirmType.values()) {
                if (type.name().equals(args[1])) {
                    isValidType = true;
                    break;
                }
            }

            if (!isValidType) {
                MsgUtil.sendMsg(player, "&c确认失败: 确认类型不存在!");
                return true;
            }

            CmdConfirmType confirmType = CmdConfirmType.valueOf(args[1]);

            if (!cmdConfirmManager.isExistsRequest(playerName, confirmType)) {
                MsgUtil.sendMsg(player, "&c确认失败: &e" + confirmType.name() + " &c请求不存在!");
                return true;
            }

            CmdConfirmRequests cmdConfirmRequests = cmdConfirmManager.getRequests(playerName);
            CmdConfirmEntry confirmEntry = cmdConfirmRequests.getCmdConfirmEntry(confirmType);

            confirmEntry.setConfirmed(true);
            // 让玩家执行指令
            player.chat("/" + confirmEntry.getConfirmedCmd());
            cmdConfirmRequests.removeRequest(confirmType);
            return true;
        }

        return false;
    }

    public String getUsage() {
        return null;
    }

    public boolean isOnlyPlayerCanExecute() {
        return true;
    }
}
