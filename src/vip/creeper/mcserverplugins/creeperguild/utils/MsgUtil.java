package vip.creeper.mcserverplugins.creeperguild.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;

import java.util.logging.Logger;

public class MsgUtil {
    private static CreeperGuild plugin = CreeperGuild.getInstance();
    private static Logger logger = plugin.getLogger();

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void warring(String msg) {
        logger.warning(msg);
    }

    public static void sendMsgToCsWithPrefix(CommandSender cs, String msg) {
        cs.sendMessage("§a[CreeperGuild] §b" + ChatColor.translateAlternateColorCodes('&', msg));
    }
}
