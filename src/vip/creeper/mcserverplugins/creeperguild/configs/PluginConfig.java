package vip.creeper.mcserverplugins.creeperguild.configs;

import org.bukkit.configuration.file.YamlConfiguration;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.Settings;
import vip.creeper.mcserverplugins.creeperguild.utils.FileUtil;

import java.io.File;

/**
 * Created by July on 2018/1/29.
 */
public class PluginConfig implements Config {
    private CreeperGuild plugin;
    private Settings settings;

    public PluginConfig(CreeperGuild plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettings();
    }

    public boolean loadConfig() {
        File configFile = new File(FileUtil.PLUGIN_FOLDER_PATH, "config.yml");

        if (!configFile.exists()) {
            if (!FileUtil.copyJarResource("config.yml", configFile.getAbsolutePath())) {
                CreeperGuild.warring("文件复制失败: " + configFile.getAbsolutePath());
                return false;
            }
        }

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(configFile);

        settings.setCreateNameMinLen(yml.getInt("create.name_min_len"));
        settings.setCreateNameMaxLen(yml.getInt("create.name_max_len"));
        settings.setCreateCostMoney(yml.getInt("create.cost_money"));
        settings.setPlaceholderApiChatPrefixHasNot(yml.getString("placeholder_api.chat_prefix.has_not"));
        settings.setPlaceholderApiChatPrefixHas(yml.getString("placeholder_api.chat_prefix.has"));
        settings.setPlaceholderApiGuildNameHasNot(yml.getString("placeholder_api.guild_name.has_not"));
        settings.setPlaceholderApiGuildNameHas(yml.getString("placeholder_api.guild_name.has"));
        settings.setSetSpawnCostMoney(yml.getInt("set_spawn_cost_money"));
        settings.setMaxAdminNum(yml.getInt("max_admin_num"));
        return true;
    }
}
