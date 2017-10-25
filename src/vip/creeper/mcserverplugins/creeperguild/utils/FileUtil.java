package vip.creeper.mcserverplugins.creeperguild.utils;

import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;

import java.io.File;

public class FileUtil {
    private static CreeperGuild plugin = CreeperGuild.getInstance();

    public static String getPluginDataFolderAbsolutePath() {
        return plugin.getDataFolder().getAbsolutePath() + File.separator + "data";
    }
}
