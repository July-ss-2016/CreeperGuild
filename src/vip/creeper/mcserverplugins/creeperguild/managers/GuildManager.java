package vip.creeper.mcserverplugins.creeperguild.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.Guild;
import vip.creeper.mcserverplugins.creeperguild.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuildManager {
    private CreeperGuild plugin;
    private File guildCounterFile;
    private YamlConfiguration guildCounterYml;
    private File guildsFolder;
    private List<Guild> guilds;

    public GuildManager(CreeperGuild plugin) {
        this.plugin = plugin;
        this.guilds = new ArrayList<>();
        this.guildCounterFile = new File(plugin.getDataFolder(), "guild_counter.yml"); //不需要考虑是否存在的问题，主程序已处理
        this.guildCounterYml = YamlConfiguration.loadConfiguration(guildCounterFile);
        this.guildsFolder = new File(FileUtil.getPluginDataFolderAbsolutePath() + File.separator + "data" + File.separator + "guilds");

        loadGuilds();
    }

    public boolean createGuild(String owner, String guildName, Date creationDate) {
        if (!guildsFolder.exists() && !guildsFolder.mkdirs()) {
            return false;
        }

        /*
        gid是唯一的公会标识符，为了确保其唯一性，公会文件不能被删除。
         */
        String gid = "000" + (getGuilds().size() + 1);
        File newGuildFile = new File(guildsFolder.getAbsolutePath() + File.separator + gid + ".yml");
        YamlConfiguration newGuildYml = YamlConfiguration.loadConfiguration(newGuildFile);

        try {
            if (!newGuildFile.createNewFile()) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        newGuildYml.set("gid", gid);

        newGuildYml.set("guild_name", guildName);

        return false;
    }

    public int getCreatedGuildCount() {
        return -1;
    }

    // file path = .../guilds
    public void loadGuilds() {
        File guildFolder = new File(FileUtil.getPluginDataFolderAbsolutePath());

        if (!guildFolder.exists()) {
            return;
        }

        File[] guildFiles = guildFolder.listFiles();

        if (guildFiles == null) {
            return;
        }

        guilds.clear();

        for (File guildFile : guildFiles) {
            YamlConfiguration guildYml = YamlConfiguration.loadConfiguration(guildFile);

            guilds.add(new Guild(guildYml.getInt("uid"))); //根据uid
        }


    }

    public Guild getGuild(int gid) {
        return null;
    }

    public List<Guild> getGuilds() {
        //File foloder = new File(FileUtil.getPluginDataFolderAbsolutePath() + File.separator + )


        return this.guilds;
    }
}
