package vip.creeper.mcserverplugins.creeperguild.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import vip.creeper.mcserverplugins.creeperguild.Guild;
import vip.creeper.mcserverplugins.creeperguild.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuildManager {
    private List<Guild> guilds;

    public GuildManager() {
        this.guilds = new ArrayList<>();
    }

    public boolean createGuild(String owner, String guildName, Date creationDate) {
        return false;
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

            guilds.add(new Guild(guildYml.getInt("uid")));
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
