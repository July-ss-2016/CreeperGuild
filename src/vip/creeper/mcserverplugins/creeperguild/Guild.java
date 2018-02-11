package vip.creeper.mcserverplugins.creeperguild;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperguild.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperguild.utils.YmlUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Guild {
    private CreeperGuild plugin;
    private File guildFile;
    private YamlConfiguration guildYml;
    private String gid;
    private String name;
    private long creationTime;
    private GuildOwner owner;
    private int maxMemberNum;
    private String introduction;
    private HashMap<String, GuildPlayer> members;
    private Location spawnLoc;
    private HashMap<String,  GuildAdmin> admins;

    public Guild(String gid) {
        this.plugin = CreeperGuild.getInstance();
        this.gid = gid;
        this.guildFile = new File(FileUtil.GUILDS_FOLDER_PATH, gid + ".yml");
        this.guildYml = YamlConfiguration.loadConfiguration(guildFile);

        //初始化变量
        this.name = guildYml.getString("name");
        this.creationTime = guildYml.getLong("creation_time");
        this.owner = new GuildOwner(guildYml.getString("owner"));
        this.maxMemberNum = guildYml.getInt("max_member_num", 10);
        this.introduction = guildYml.getString("introduction", "欢迎加入");
        this.members = new HashMap<>();
        this.admins = new HashMap<>();

        if (guildYml.isConfigurationSection("spawn_loc")) {
            this.spawnLoc = YmlUtil.getLocFromSection(guildYml.getConfigurationSection("spawn_loc"));
        }

        // 把会主也加入成员列表
        members.put(owner.getName(), owner);

        if (guildYml.isList("members")) {
            for (String memberName : guildYml.getStringList("members")) {
                GuildPlayer member = plugin.getGuildPlayerManager().getOrLoadGuildPlayer(memberName);

                if (member == null) {
                    break;
                }

                if (member instanceof GuildAdmin) {
                    admins.put(memberName.toLowerCase(), (GuildAdmin) member);
                }

                members.put(memberName.toLowerCase(), member);
            }
        }
    }

    public void alert(String msg) {
        for (GuildPlayer member : getOnlineMembers()) {
            member.getBukkitPlayer().sendMessage(msg);
        }
    }

    public int getAdminNum() {
        return admins.size();
    }

    public boolean isMember(String playerName) {
        return members.containsKey(playerName.toLowerCase());
    }

    public Location getSpawnLoc() {
        return spawnLoc;
    }

    public boolean setSpawnLoc(Location loc) {
        if (loc == null) {
            guildYml.set("spawn_loc", null);
            this.spawnLoc = null;
            return save();
        }

        if (spawnLoc == null) {
            guildYml.createSection("spawn_loc");
        }

        YmlUtil.setLocForSection(guildYml.getConfigurationSection("spawn_loc"), loc);
        this.spawnLoc = loc;
        return save();
    }

    public List<GuildPlayer> getOnlineMembers() {
        List<GuildPlayer> result = new ArrayList<>();

        for (GuildPlayer member : getMembers()) {
            if (member.isOnline()) {
                result.add(member);
            }
        }

        return result;
    }

    public boolean isAdmin(String playerName) {
        for (GuildAdmin admin : getAdmins()) {
            if (admin.getName().equalsIgnoreCase(playerName)) {
                return true;
            }
        }

        return false;
    }

    public List<GuildAdmin> getAdmins() {
        List<GuildAdmin> result = new ArrayList<>();

        result.addAll(admins.values());
        return result;
    }

    public boolean setAdmin(GuildPlayer guildPlayer) {
        String playerName = guildPlayer.getName().toLowerCase();

        if (!members.containsKey(playerName)) {
            throw new IllegalArgumentException("欲设置的管理员非该工会成员!");
        }

        if (!guildPlayer.setPermission(GuildPlayerPermission.ADMIN)) {
            return false;
        }

        // 删除cache，更新玩家为GuildAdmin
        plugin.getGuildPlayerManager().removeGuildPlayerFromCache(playerName);

        GuildPlayer newGuildPlayer = plugin.getGuildPlayerManager().getOrLoadGuildPlayer(playerName);

        // 更新本类存储的GuildPlayer，变为GuildAdmin
        members.remove(playerName);
        members.put(playerName, newGuildPlayer);
        admins.put(playerName, (GuildAdmin) newGuildPlayer);
        return true;
    }

    public boolean deAdmin(GuildAdmin guildAdmin) {
        String lowerCaseAdminName = guildAdmin.getName().toLowerCase();

        if (guildAdmin.getPermission() != GuildPlayerPermission.ADMIN) {
            throw new IllegalArgumentException("被取消者非管理员!");
        }

        if (!guildAdmin.setPermission(GuildPlayerPermission.MEMBER)) {
            return false;
        }

        // 删除cache，更新玩家为GuildPlayer
        plugin.getGuildPlayerManager().removeGuildPlayerFromCache(lowerCaseAdminName);

        GuildPlayer newGuildPlayer = plugin.getGuildPlayerManager().getOrLoadGuildPlayer(lowerCaseAdminName);

        // 更新本类存储的GuildAdmin，变为GuildPlayer
        members.remove(lowerCaseAdminName);
        members.put(lowerCaseAdminName, newGuildPlayer);
        admins.remove(lowerCaseAdminName);
        return true;
    }

    public int getPerCapitaMoney() {
        int total = 0;

        if (getMemberNum() == 0) {
            return 0;
        }

        for (GuildPlayer member : getMembers()) {
            //noinspection deprecation
            total += plugin.getVault().getBalance(member.getName());
        }

        return total / getMemberNum();
    }

    public List<GuildPlayer> getMembers() {
        List<GuildPlayer> result = new ArrayList<>();

        result.addAll(members.values());
        return result;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getMaxMemberNum() {
        return maxMemberNum;
    }

    public int getMemberNum() {
        return members.size();
    }

    public String getGid() {
        return gid;
    }

    public String getName() {
        return name;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public GuildOwner getOwner() {
        return owner;
    }

    public boolean setIntroduction(String text) {
        guildYml.set("introduction", text);

        if (!save()) {
            return false;
        }

        this.introduction = text;
        return true;
    }

    public boolean setName(String name) {
        guildYml.set("name", name);

        if (!save()) {
            return false;
        }

        this.name = name;
        return true;
    }

    public boolean setOwner(String owner) {
        guildYml.set("owner", owner);

        if (!save()) {
            return false;
        }

        this.owner = new GuildOwner(owner);
        return true;
    }

    public boolean removeMember(String playerName) {
        String lowerCasePlayerName = playerName.toLowerCase();
        List<String> memberNames = new ArrayList<>();

        for (GuildPlayer member : getMembers()) {
            memberNames.add(member.getName());
        }

        memberNames.remove(lowerCasePlayerName);
        guildYml.set("members", memberNames);

        if (!save()) {
            return false;
        }

        members.remove(lowerCasePlayerName);
        admins.remove(lowerCasePlayerName);
        plugin.getGuildPlayerManager().removeGuildPlayer(playerName);
        return true;
    }

    public boolean addMember(GuildPlayer guildPlayer) {
        List<String> memberNames = new ArrayList<>();
        String playerName = guildPlayer.getName();

        for (GuildPlayer member : getMembers()) {
            memberNames.add(member.getName());
        }

        memberNames.add(guildPlayer.getName().toLowerCase());
        guildYml.set("members", memberNames);

        if (!save()) {
            return false;
        }

        members.put(guildPlayer.getName().toLowerCase(), plugin.getGuildPlayerManager().getOrLoadGuildPlayer(playerName));
        return true;
    }

    private boolean save() {
        try {
            guildYml.save(guildFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
