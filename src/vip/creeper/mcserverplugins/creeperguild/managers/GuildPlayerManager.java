package vip.creeper.mcserverplugins.creeperguild.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import vip.creeper.mcserverplugins.creeperguild.*;
import vip.creeper.mcserverplugins.creeperguild.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by July on 2018/1/30.
 */
public class GuildPlayerManager {
    private CreeperGuild plugin;
    private HashMap<String, GuildPlayer> caches; //缓存

    public GuildPlayerManager(CreeperGuild plugin) {
        this.plugin = plugin;
        this.caches = new HashMap<>();
    }

    public GuildPlayer getOrLoadGuildPlayer(String playerName) {
        String lowerCasePlayerName = playerName.toLowerCase();

        // 直接从caches获取
        if (caches.containsKey(lowerCasePlayerName)) {
            return caches.get(lowerCasePlayerName);
        }

        File playerDataFile = new File(FileUtil.PLAYERS_FOLDER_FILE, lowerCasePlayerName + ".yml");

        if (!playerDataFile.exists()) {
            return null;
        }

        YamlConfiguration playerDataYml = YamlConfiguration.loadConfiguration(playerDataFile);
        GuildPlayerPermission permission = GuildPlayerPermission.valueOf(playerDataYml.getString("permission"));
        GuildPlayer guildPlayer;

        switch (permission) {
            case ADMIN:
                guildPlayer = new GuildAdmin(lowerCasePlayerName);
                break;
            case OWNER:
                guildPlayer = new GuildOwner(lowerCasePlayerName);
                break;
            case MEMBER:
                guildPlayer = new GuildPlayer(lowerCasePlayerName);
                break;
            default:
                return null;
        }

        caches.put(lowerCasePlayerName, guildPlayer);
        return guildPlayer;
    }

    public void unloadGuildPlayer(String playerName) {
        caches.remove(playerName.toLowerCase());
    }

    public boolean isExistsGuildPlayer(String playerName) {
        return (new File(FileUtil.PLAYERS_FOLDER_FILE, playerName.toLowerCase() + ".yml")).exists();
    }

    public boolean createNewGuildPlayer(String playerName, String gid, GuildPlayerPermission permission) {
        if (playerName == null) {
            throw new IllegalArgumentException("玩家名不能为空!");
        }

        if (gid == null) {
            throw new IllegalArgumentException("GID不能为空!");
        }

        if (permission == null) {
            throw new IllegalArgumentException("权限不能为空!");
        }

        File playerDataFile = new File(FileUtil.PLAYERS_FOLDER_FILE, playerName.toLowerCase() + ".yml");

        try {
            if (!playerDataFile.createNewFile()) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        YamlConfiguration playerDataYml = YamlConfiguration.loadConfiguration(playerDataFile);

        playerDataYml.set("player_name", playerName);
        playerDataYml.set("gid", gid); //所在公会
        playerDataYml.set("permission", permission.name()); //公会权限

        try {
            playerDataYml.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean removeGuildPlayer(String playerName) {
        File playerDataFile = new File(FileUtil.PLAYERS_FOLDER_FILE, playerName.toLowerCase() + ".yml");

        if (!playerDataFile.exists()) {
            return false;
        }

        if (!playerDataFile.delete()) {
            return false;
        }

        removeGuildPlayerFromCache(playerName);
        return true;
    }

    public void removeGuildPlayerFromCache(String playerName) {
        caches.remove(playerName);
    }
}
