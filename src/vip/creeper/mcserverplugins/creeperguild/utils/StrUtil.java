package vip.creeper.mcserverplugins.creeperguild.utils;

import org.bukkit.ChatColor;

/**
 * Created by July on 2018/2/10.
 */
public class StrUtil {
    public static boolean isNumStr(String s) {
        return s.matches("[0-9]+");
    }

    public static String translateColorCode(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String[] translateColorCode(String[] strs) {
        for (int i = 0; i < strs.length; i ++) {
            strs[i] = translateColorCode(strs[i]);
        }

        return strs;
    }

    public static int parseIntStr(String s) {
        return !isNumStr(s) ? -1 : Integer.parseInt(s);
    }
}
