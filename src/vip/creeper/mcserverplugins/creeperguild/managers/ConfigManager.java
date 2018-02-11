package vip.creeper.mcserverplugins.creeperguild.managers;

import org.bukkit.Bukkit;
import vip.creeper.mcserverplugins.creeperguild.ConfigType;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.configs.Config;
import vip.creeper.mcserverplugins.creeperguild.configs.PluginConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by July on 2018/1/29.
 */
public class ConfigManager {
    private CreeperGuild plugin;
    private HashMap<ConfigType, Config> configs;

    public ConfigManager(CreeperGuild plugin) {
        this.configs = new HashMap<>();
        this.plugin = plugin;
    }

    public void loadConfigs() {
        for (Map.Entry<ConfigType, Config> config : configs.entrySet()) {
            Bukkit.getScheduler().runTask(plugin, () -> config.getValue().loadConfig());
        }
    }

    public void registerConfig(ConfigType type, Config config) {
        configs.put(type, config);
    }
}
