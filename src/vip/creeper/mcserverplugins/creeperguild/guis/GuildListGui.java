package vip.creeper.mcserverplugins.creeperguild.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vip.creeper.mcserverplugins.creeperguild.Cache;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.Guild;
import vip.creeper.mcserverplugins.creeperguild.items.PageItem;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MathUtil;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by July on 2018/2/9.
 */
public class GuildListGui implements Cache, GuildPageGui {
    public static final String TITLE_PREFIX = "§1§l公会列表 - P";
    private static CreeperGuild plugin = CreeperGuild.getInstance();
    private GuildManager guildManager = plugin.getGuildManager();
    private List<Inventory> invs;

    public GuildListGui() {
        this.invs = new ArrayList<>();

        update();
    }

    public void update() {
        List<Guild> guilds = new ArrayList<>();

        guilds.addAll(guildManager.getGuilds());

        int guildSize = guilds.size();
        List<Inventory> tempInvs = new ArrayList<>();
        int pageCounter = guildSize % 52 == 0 ? guildSize / 52 : guildSize / 52 +1;
        int guildIndex = 0;

        for (int o = 0; o < pageCounter; o ++) {
            Inventory inv = Bukkit.createInventory(null, 54, TITLE_PREFIX + (o + 1));

            for (int i = 0; i < (o == pageCounter - 1 ? guildSize - o * 52 : 52); i ++) {
                inv.setItem(i, getGuildInfoItem(guilds.get(guildIndex)));

                guildIndex ++;
            }

            inv.setItem(52, PageItem.LAST_PAGE_ITEM);
            inv.setItem(53, PageItem.NEST_PAGE_ITEM);
            tempInvs.add(inv);
        }

        this.invs = tempInvs;
    }

    private ItemStack getGuildInfoItem(Guild guild) {
        ItemStack item = new ItemStack(Material.BANNER);
        ItemMeta meta = item.getItemMeta();

        item.setDurability((short) MathUtil.getRandomIntegerNum(1, 15));
        meta.setDisplayName("§e" + guild.getName());
        meta.setLore(Arrays.asList("§7- §dGID §b> §d" + guild.getGid(), "§7- §d会主 §b> §d" + guild.getOwner().getName(), "§7- §d人数 §b> §d" + guild.getMemberNum() + " §b/ §d" + guild.getMaxMemberNum(),
                "§7- §d人均财富 §b> §d" + guild.getPerCapitaMoney(), "§7- §d创建日期 §b> §d" + MsgUtil.formatDate(guild.getCreationTime()), "§7- §f" + guild.getIntroduction()));

        item.setItemMeta(meta);
        return item;
    }

    public int getPageNum() {
        return invs.size();
    }

    public boolean open(Player player, int page) {
        if (page > getPageNum()) {
            return false;
        }

        player.openInventory(invs.get(page - 1));
        return true;
    }
}
