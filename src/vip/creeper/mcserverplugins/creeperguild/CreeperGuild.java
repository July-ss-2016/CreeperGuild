package vip.creeper.mcserverplugins.creeperguild;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperguild.commands.*;
import vip.creeper.mcserverplugins.creeperguild.configs.PluginConfig;
import vip.creeper.mcserverplugins.creeperguild.listeners.GuildApproveGuiListener;
import vip.creeper.mcserverplugins.creeperguild.listeners.GuildListGuiListener;
import vip.creeper.mcserverplugins.creeperguild.managers.*;

import java.io.File;
import java.io.IOException;

public class CreeperGuild extends JavaPlugin {
    private boolean firstLoad = true;
    private static CreeperGuild instance;
    private Economy vault;
    private PluginManager pluginManager;
    private CmdConfirmManager cmdConfirmManager;
    private ConfigManager configManager;
    private GuildManager guildManager;
    private GuildPlayerManager guildPlayerManager;
    private GuildCacheManager guildCacheManager;
    private vip.creeper.mcserverplugins.creeperguild.commands.CommandExecutor commandExecutor;
    private Settings settings;
    private String[] guildFolderPaths = new String[] {"data" + File.separator + "guilds", "data" + File.separator + "players"}; //需要创建的文件夹路径
    private String[] guildFilePaths = new String[] {"guild_counter.yml", "unapproved_players.yml"}; //需要创建的文件路径

    public void onEnable() {
        instance = this;
        this.pluginManager = Bukkit.getPluginManager();

        if (!hookVault()) {
            warring("Vault Hook 失败, 插件将被卸载!");
            setEnabled(false);
            return;
        }

        if (!initFiles()) {
            warring("文件(夹)初始化失败, 插件将被卸载!");
            setEnabled(false);
            return;
        }

        info("文件(夹)初始化完毕!");

        this.settings = new Settings();
        this.commandExecutor = new vip.creeper.mcserverplugins.creeperguild.commands.CommandExecutor();
        this.guildCacheManager = new GuildCacheManager(this);
        this.cmdConfirmManager = new CmdConfirmManager();
        this.configManager = new ConfigManager(this);
        this.guildPlayerManager = new GuildPlayerManager(this);
        this.guildManager = new GuildManager(this);

        guildManager.loadGuilds();
        registerConfigs();
        info("工会载入完毕!");
        configManager.loadConfigs();
        info("配置载入完毕!");
        getCommand("guild").setExecutor(commandExecutor);
        registerCommands();
        info("命令注册完毕!");
        registerEvents();
        info("事件注册完毕!");
        info("PlaceholderAPI Hook" + (new PlaceholderExpansion(this).hook() ? "成功" : "失败") + ".");
        info("插件初始化完毕!");
        test();
    }

    private void test() {
        for (int i = 0; i < 52; i ++) {
            //guildManager.createGuild(MathUtil.getRandomIntegerNum(1, 9999) + "", MathUtil.getRandomIntegerNum(1, 9999) + "");
        }
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelAllTasks();
        info("插件已被卸载!");
    }

    private boolean hookVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            return false;
        }

        vault = rsp.getProvider();

        return vault != null;
    }

    public Settings getSettings() {
        return settings;
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

    private void registerEvents() {
        pluginManager.registerEvents(new GuildApproveGuiListener(this), this);
        pluginManager.registerEvents(new GuildListGuiListener(), this);
    }

    private void registerCommands() {
        commandExecutor.registerCommand("create", new GuildCreateCommand(this));
        commandExecutor.registerCommand("confirm", new GuildConfirmCommand(this));
        commandExecutor.registerCommand("info", new GuildInfoCommand(this));
        commandExecutor.registerCommand("join", new GuildJoinCommand(this));
        commandExecutor.registerCommand("approve", new GuildApproveCommand(this));
        commandExecutor.registerCommand("gui", new GuildGuiCommand(this));
        commandExecutor.registerCommand("list", new GuildListCommand());
        commandExecutor.registerCommand("tpaAll", new GuildTpaAllCommand(this));
        commandExecutor.registerCommand("tpaAllAccept", new GuildTpAcceptCommand(this));
        commandExecutor.registerCommand("setSpawn", new GuildSetSpawnCommand(this));
        commandExecutor.registerCommand("setAdmin", new GuildAdminSetCommand(this));
        commandExecutor.registerCommand("deAdmin", new GuildAdminDeCommand(this));
        commandExecutor.registerCommand("alert", new GuildAlertCommand(this));
        commandExecutor.registerCommand("spawn", new GuildSpawnCommand(this));
        commandExecutor.registerCommand("setIntro", new GuildIntroductionSetCommand(this));
        commandExecutor.registerCommand("rmSpawn", new GuildRemoveSpawnCommand(this));
        commandExecutor.registerCommand("kick", new GuildKickCommand(this));
        commandExecutor.registerCommand("help", new GuildHelpCommand());
    }

    private void registerConfigs() {
        configManager.registerConfig(ConfigType.PLUGIN_CONFIG, new PluginConfig(this));
    }

    public static CreeperGuild getInstance() {
        return instance;
    }

    public GuildManager getGuildManager() {
        return guildManager;
    }

    public GuildPlayerManager getGuildPlayerManager() {
        return guildPlayerManager;
    }

    public GuildCacheManager getGuildCacheManager() {
        return guildCacheManager;
    }

    public Economy getVault() {
        return vault;
    }

    public CmdConfirmManager getCmdConfirmManager() {
        return cmdConfirmManager;
    }

    public static void info(String msg) {
        instance.getLogger().info(msg);
    }

    public static void warring(String msg) {
        instance.getLogger().warning(msg);
    }
}
