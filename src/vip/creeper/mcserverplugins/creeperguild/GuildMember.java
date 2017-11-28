package vip.creeper.mcserverplugins.creeperguild;

import org.bukkit.OfflinePlayer;

public class GuildMember {
    private OfflinePlayer player;
    private Guild guild;

    public OfflinePlayer getPlayer() {
        return player;
    }

    public Guild getGuild() {
        return guild;
    }

    public GuildMember(OfflinePlayer player, Guild guild) {
        this.player = player;
        this.guild = guild;
    }

}
