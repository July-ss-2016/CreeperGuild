package vip.creeper.mcserverplugins.creeperguild.managers;

import org.bukkit.Bukkit;
import vip.creeper.mcserverplugins.creeperguild.Cache;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;

/**
 * Created by July on 2018/2/9.
 */
public class GuildCacheManager {
    private CreeperGuild plugin;

    public GuildCacheManager(CreeperGuild plugin) {
        this.plugin = plugin;
    }

    public void addCacheUpdateTask(Cache cache, long interval) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> Bukkit.getScheduler().runTask(plugin, cache::update), interval * 20L, interval * 20L);
    }
}
