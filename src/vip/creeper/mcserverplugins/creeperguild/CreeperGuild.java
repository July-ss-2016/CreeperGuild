package vip.creeper.mcserverplugins.creeperguild;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperguild.commands.GuildCreateCommand;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildCacheManager;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

import java.util.Date;

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
        MsgUtil.info("命令注册完毕.");
        MsgUtil.info("插件初始化完毕!");
        test();
    }

    private void test() {
        System.out.print(guildManager.createGuild("t", "t", new Date()));
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
