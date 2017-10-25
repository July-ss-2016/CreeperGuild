package vip.creeper.mcserverplugins.creeperguild;

import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Date;

public class Guild {
    private String name;
    private int level;
    private Date creationDate;
    private OfflinePlayer owner;
    private ArrayList<OfflinePlayer> members;

    public Guild(String name, int level, Date creationDate, OfflinePlayer owner, ArrayList<OfflinePlayer> members) {
        this.name = name;
        this.level = level;
        this.creationDate = creationDate;
        this.owner = owner;
        this.members = members;
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
