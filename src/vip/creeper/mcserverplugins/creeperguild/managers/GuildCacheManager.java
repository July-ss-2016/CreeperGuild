package vip.creeper.mcserverplugins.creeperguild.managers;

import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.Guild;

import java.util.HashMap;

public class GuildCacheManager {
    private static CreeperGuild plugin = CreeperGuild.getInstance();
    private HashMap<Integer, Guild> caches = new HashMap<>();

    public GuildCacheManager() {
        // put all guilds to cache
        for (Guild guild : plugin.getGuildManager().getGuilds()) {
            caches.put(guild.getGid(), guild);
        }
    }

    public Guild getGuildFromCache(int uid) {
        if (!caches.containsKey(uid)) {
            caches.put(uid, new Guild(uid));
        }

        return caches.get(uid);
    }

    public boolean removeCache(int uid) {
        caches.remove(uid);
    }
}
