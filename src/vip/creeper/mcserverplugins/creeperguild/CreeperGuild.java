package vip.creeper.mcserverplugins.creeperguild;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperguild.commands.GuildCreateCommand;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildCacheManager;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreeperGuild extends JavaPlugin {
    private static CreeperGuild instance;
    private GuildManager guildManager;
    private GuildCacheManager guildCacheManager;
    private vip.creeper.mcserverplugins.creeperguild.commands.CommandExecutor commandExecutor;
    private String[] guildFolderPaths = new String[] {"data" + File.separator + "guilds", "data" + File.separator + "players"};
    private String[] guildFilePaths = new String[] {"guild_counter.yml"};

    public void onEnable() {
        instance = this;

        if (!initFiles()) {
            info("文件初始化失败,插件将被卸载!");
            setEnabled(false);
            return;
        }

        info("文件初始化完毕!");

        this.commandExecutor = new vip.creeper.mcserverplugins.creeperguild.commands.CommandExecutor();
        this.guildManager = new GuildManager(this);
        this.guildCacheManager = new GuildCacheManager();

        registerCommands();
        info("命令注册完毕!");
        info("插件初始化完毕!");
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelAllTasks();
    }

    private boolean initFiles() {
        File pluginFolder = getDataFolder();

        if (!pluginFolder.exists()) {
            if (!pluginFolder.mkdir()) {
                info("文件夹创建失败: " + pluginFolder.getAbsolutePath());
                return false;
            }
        }

        for (String filePath : guildFilePaths) {
            File file = new File(pluginFolder.getAbsolutePath() + File.separator + filePath);

            if (file.exists()) {
                continue;
            }

            try {
                if (!file.createNewFile()) {
                    info("文件创建失败: " + file.getAbsolutePath());
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        for (String folderPath : guildFolderPaths) {
            File file = new File(pluginFolder.getAbsolutePath() + File.separator + folderPath);

            if (file.exists()) {
                continue;
            }

            if (!file.mkdirs()) {
                info("文件夹创建失败: " + file.getAbsolutePath());
                return false;
            }
        }

        return true;
    }

    private void registerCommands() {
        commandExecutor.registerCommand("create", new GuildCreateCommand());
    }

    public static CreeperGuild getInstance() {
        return instance;
    }

    public GuildManager getGuildManager() {
        return guildManager;
    }

    public GuildCacheManager getGuildCacheManager() {
        return guildCacheManager;
    }

    public static void info(String msg) {
        instance.getLogger().info(msg);
    }

    public static void warring(String msg) {
        instance.getLogger().warning(msg);
    }
}
