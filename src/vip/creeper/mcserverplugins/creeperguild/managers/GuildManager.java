package vip.creeper.mcserverplugins.creeperguild.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.Guild;
import vip.creeper.mcserverplugins.creeperguild.GuildPlayerPermission;
import vip.creeper.mcserverplugins.creeperguild.UnapprovedPlayer;
import vip.creeper.mcserverplugins.creeperguild.exceptions.GuildCreationException;
import vip.creeper.mcserverplugins.creeperguild.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GuildManager {
    private GuildPlayerManager guildPlayerManager;
    private HashMap<String, Guild> guilds; // gid对应Guild类
    private File guildCounterFile;
    private YamlConfiguration guildCounterYml;
    private File unapprovedPlayersFile;
    private YamlConfiguration unapprovedPlayersYml;

    public GuildManager(CreeperGuild plugin) {
        this.guildPlayerManager = plugin.getGuildPlayerManager();
        this.guilds = new HashMap<>();
        this.guildCounterFile = new File(FileUtil.PLUGIN_FOLDER_PATH, "guild_counter.yml"); //不需要考虑是否存在的问题，主程序已处理
        this.guildCounterYml = YamlConfiguration.loadConfiguration(guildCounterFile);
        this.unapprovedPlayersFile = new File(FileUtil.PLUGIN_FOLDER_PATH, "unapproved_players.yml");
        this.unapprovedPlayersYml = YamlConfiguration.loadConfiguration(unapprovedPlayersFile);
    }

    public boolean isExistsGuild(String gid) {
        return guilds.containsKey(gid);
    }

    public boolean createGuild(String owner, String guildName) {
        if (owner == null) {
            throw new IllegalArgumentException("会主不能为空!");
        }

        if (guildName == null) {
            throw new IllegalArgumentException("公会名不能为空!");
        }

        if (!addCreatedGuildCount()) {
            throw new GuildCreationException("自增已创建公会数量失败!");
        }

        if (getPlayerOwnedGuild(owner) != null) {
            throw new GuildCreationException("一个玩家至多创建一个公会!");
        }

        String gid = "000" + getCreatedGuildCount();
        File newGuildFile = new File(FileUtil.GUILDS_FOLDER_PATH + File.separator + gid + ".yml");

        try {
            if (!newGuildFile.createNewFile()) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        YamlConfiguration newGuildYml = YamlConfiguration.loadConfiguration(newGuildFile);

        newGuildYml.set("gid", gid);
        newGuildYml.set("owner", owner);
        newGuildYml.set("name", guildName);
        newGuildYml.set("creation_time", System.currentTimeMillis());
        newGuildYml.set("max_member_num", 10);
        newGuildYml.createSection("members");

        try {
            newGuildYml.save(newGuildFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (!guildPlayerManager.createNewGuildPlayer(owner, gid, GuildPlayerPermission.OWNER)) {
            return false;
        }

        guilds.put(gid, new Guild(gid));
        return true;
    }

    //得到已创建公会的数量
    public int getCreatedGuildCount() {
        return guildCounterYml.getInt("count");
    }

    //自增已创建公会的数量，为了gid
    public boolean addCreatedGuildCount() {
        guildCounterYml.set("count", guildCounterYml.getInt("count", 0) + 1);

        return saveGuildCounterYml();
    }

    //载入所有工会
    public void loadGuilds() {
        File[] guildFiles = FileUtil.GUILDS_FOLDER_FILE.listFiles();

        if (guildFiles == null) {
            return;
        }

        guilds.clear();

        for (File guildFile : guildFiles) {
            YamlConfiguration guildYml = YamlConfiguration.loadConfiguration(guildFile);
            String gid = guildYml.getString("gid");

            guilds.put(gid, new Guild(gid));
        }
    }

    //得到玩家拥有(创建)的工会
    public Guild getPlayerOwnedGuild(String playerName) {
        if (playerName == null) {
            throw new IllegalArgumentException("玩家名不能为空!");
        }

        Collection<Guild> temp = getGuilds();

        if (guilds == null) {
            return null;
        }

        for (Guild guild : temp) {
            if (guild.getOwner().getName().equalsIgnoreCase(playerName)) {
                return guild;
            }
        }

        return null;
    }

    public Guild getGuild(String gid) {
        if (gid == null) {
            throw new IllegalArgumentException("GID不能为空!");
        }

        return guilds.get(gid);
    }

    public Collection<Guild> getGuilds() {
        return guilds.values();
    }

    //得到未审批的玩家
    public List<UnapprovedPlayer> getGuildUnapprovedPlayers(String gid) {
        List<UnapprovedPlayer> result = new ArrayList<>();

        if (!unapprovedPlayersYml.isConfigurationSection("guilds." + gid)) {
            return result;
        }

        Set<String> playerNames =  unapprovedPlayersYml.getConfigurationSection("guilds." + gid).getKeys(false);

        if (playerNames == null) {
            return result;
        }

        for (String playerName : playerNames) {
            result.add(new UnapprovedPlayer(playerName, unapprovedPlayersYml.getLong("guilds." + gid + "." + playerName + ".application_time")));
        }

        return result;
    }

    public boolean addGuildUnapprovedPlayers(String gid, String playerName) {
        unapprovedPlayersYml.set("guilds." + gid + "." + playerName + ".application_time", System.currentTimeMillis());

        return saveUnapprovedPlayersYml();
    }

    public boolean removeGuildUnapprovedPlayers(String gid, String playerName) {
        unapprovedPlayersYml.set("guilds." + gid + "." + playerName, null);

        return saveUnapprovedPlayersYml();
    }

    public boolean isExistsUnapprovedPlayer(String gid, String playerName) {
        return unapprovedPlayersYml.isConfigurationSection("guilds." + gid + "." + playerName);
    }

    private boolean saveGuildCounterYml() {
        try {
            guildCounterYml.save(guildCounterFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean saveUnapprovedPlayersYml() {
        try {
            unapprovedPlayersYml.save(unapprovedPlayersFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
