package vip.creeper.mcserverplugins.creeperguild;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperguild.commands.GuildCreateCommand;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildCacheManager;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

public class CreeperGuild extends JavaPlugin {
    private static CreeperGuild instance;
    private GuildManager guildManager;
    private GuildCacheManager guildCacheManager;
    private vip.creeper.mcserverplugins.creeperguild.commands.CommandExecutor commandExecutor;

    public void onEnable() {
        instance = this;
        this.commandExecutor = new vip.creeper.mcserverplugins.creeperguild.commands.CommandExecutor();
        this.guildManager = new GuildManager();
        this.guildCacheManager = new GuildCacheManager();

        registerCommands();
        MsgUtil.info("commands registered!");
        MsgUtil.info("plugin initialization finished!");
    }

    private void registerCommands() {
        commandExecutor.registerCommand("create", new GuildCreateCommand());
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelAllTasks();
    }

    public static CreeperGuild getInstance() {
        return instance;
    }

    public GuildManager getGuildManager() {
        return guildManager;
    }
}
