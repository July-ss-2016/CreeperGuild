package vip.creeper.mcserverplugins.creeperguild.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by July on 2018/2/9.
 */
public class PlayerUtil {
    public static boolean isValidCs(CommandSender cs) {
        if (cs == null) {
            return false;
        }

        if (cs instanceof Player) {
            Player player = (Player) cs;

            return player.isOnline();
        }

        return true;
    }
}
