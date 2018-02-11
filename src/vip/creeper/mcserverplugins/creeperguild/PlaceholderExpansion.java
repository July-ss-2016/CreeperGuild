package vip.creeper.mcserverplugins.creeperguild;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildPlayerManager;

/**
 * Created by July on 2018/2/9.
 */
public class PlaceholderExpansion extends EZPlaceholderHook {
    private GuildPlayerManager guildPlayerManager;
    private Settings settings;

    public PlaceholderExpansion(CreeperGuild plugin) {
        super(plugin, "creeperguild");

        this.guildPlayerManager = plugin.getGuildPlayerManager();
        this.settings = plugin.getSettings();
    }

    public String onPlaceholderRequest(Player player, String s) {
        String playerName = player.getName();
        boolean isInGuild = guildPlayerManager.isExistsGuildPlayer(playerName);

        GuildPlayer guildPlayer = guildPlayerManager.getOrLoadGuildPlayer(playerName);

        switch (s) {
            case "chat_prefix":
                return isInGuild ? replacePluginPlaceholder(settings.getPlaceholderApiChatPrefixHas(), guildPlayer) : settings.getPlaceholderApiChatPrefixHasNot();
            case "guild_name":
                return isInGuild ? replacePluginPlaceholder(settings.getPlaceholderApiGuildNameHas(), guildPlayer) : settings.getPlaceholderApiGuildNameHasNot();
            case "guild_permission":
                return isInGuild ? guildPlayer.getPermission().getChineseName() : "无";
        }

        return null;
    }

    private String replacePluginPlaceholder(String s, GuildPlayer guildPlayer) {
        return s.replace("{guild_name}", guildPlayer.getGuild().getName());
    }
}
