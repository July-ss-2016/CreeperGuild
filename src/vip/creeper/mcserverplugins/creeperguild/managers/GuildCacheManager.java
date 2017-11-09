package vip.creeper.mcserverplugins.creeperguild.managers;

import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.Guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuildCacheManager {
    private static CreeperGuild plugin = CreeperGuild.getInstance();
    private HashMap<Integer, Guild> caches = new HashMap<>();

    public GuildCacheManager() {
        //加载所有工会至缓存
        for (Guild guild : plugin.getGuildManager().getGuilds()) {
            caches.put(guild.getGid(), guild);
        }
    }

    public List<Guild> getGuilds() {
        List<Guild> guilds = new ArrayList<>();

        if (caches.size() == 0) {
            return null;
        }

        //values可能等于null
        for (Guild guild : caches.values()) {
            guilds.add(guild);
        }

        return guilds;
    }

    public Guild getGuild(int uid) {
        if (!caches.containsKey(uid)) {
            caches.put(uid, new Guild(uid));
        }

        return caches.get(uid);
    }

    //创建或更新缓存
    public void createOrUpdateCache(int gid) {
        caches.put(gid, plugin.getGuildManager().getGuild(gid));
    }

    //从缓存中删除工会
    public void removeGuildFromCache(int gid) {
        caches.remove(gid);
    }
}
