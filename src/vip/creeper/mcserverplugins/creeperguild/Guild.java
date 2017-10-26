package vip.creeper.mcserverplugins.creeperguild;

import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Date;

public class Guild {
    private int gid;
    private String name;
    private int level;
    private Date creationDate;
    private OfflinePlayer owner;
    private ArrayList<OfflinePlayer> members;

    public Guild(int gid) {
        this.gid = gid;
    }

    public int getGid() {
        return gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public OfflinePlayer getOwner() {
        return owner;
    }

    public void setOwner(OfflinePlayer owner) {
        this.owner = owner;
    }

    public ArrayList<OfflinePlayer> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<OfflinePlayer> members) {
        this.members = members;
    }
}
