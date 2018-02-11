package vip.creeper.mcserverplugins.creeperguild.guis;

import org.bukkit.entity.Player;

/**
 * Created by July on 2018/2/10.
 */
public interface GuildPageGui {
    boolean open(Player player, int page);

    int getPageNum();
}
