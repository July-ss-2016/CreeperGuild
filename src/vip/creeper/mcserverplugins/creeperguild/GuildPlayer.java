package vip.creeper.mcserverplugins.creeperguild;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.utils.FileUtil;

import java.io.File;
import java.io.IOException;

public class GuildPlayer {
    private CreeperGuild plugin = CreeperGuild.getInstance();
    private String name;
    private File playerFile;
    private YamlConfiguration playerYml;
    private Player bukkitPlayer;
    private String gid;
    private GuildPlayerPermission permission;

    public GuildPlayer(String fileName) {
        this.playerFile = new File(FileUtil.PLAYERS_FOLDER_PATH, fileName + ".yml");
        this.playerYml = YamlConfiguration.loadConfiguration(playerFile);
        this.name = playerYml.getString("player_name");
        this.bukkitPlayer = Bukkit.getPlayer(name);
        this.gid = playerYml.getString("gid");
        this.permission = GuildPlayerPermission.valueOf(playerYml.getString("permission"));
    }

    public String getName() {
        return name;
    }

    public Guild getGuild() {
        return plugin.getGuildManager().getGuild(gid);
    }

    public boolean setGuild(String gid) {
        playerYml.set("gid", gid);

        try {
            playerYml.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        this.gid = gid;
        return true;
    }

    public boolean isOnline() {
        return bukkitPlayer != null && bukkitPlayer.isOnline();
    }

    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }

    public GuildPlayerPermission getPermission() {
        return permission;
    }

    public boolean setPermission(GuildPlayerPermission permission) {
        playerYml.set("permission", permission.name());

        CreeperGuild.getInstance().getGuildPlayerManager().removeGuildPlayerFromCache(name);
        return save();
    }

    private boolean save() {
        try {
            playerYml.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
