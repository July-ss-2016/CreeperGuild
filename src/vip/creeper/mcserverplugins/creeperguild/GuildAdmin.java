package vip.creeper.mcserverplugins.creeperguild;

import org.bukkit.OfflinePlayer;

public class GuildAdmin extends GuildMember {
    public GuildAdmin(OfflinePlayer player, Guild guild) {
        super(player, guild);
    }

    public boolean sendMsgToGuildMembers() {
        return false;
    }
}
