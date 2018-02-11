package vip.creeper.mcserverplugins.creeperguild.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.UnapprovedPlayer;
import vip.creeper.mcserverplugins.creeperguild.managers.GuildManager;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

import java.util.Arrays;

/**
 * Created by July on 2018/2/8.
 */
public class GuildApproveGui implements GuildGui {
    public static final String TITLE = "§1§l成员审批";
    private static CreeperGuild plugin = CreeperGuild.getInstance();
    private GuildManager guildManager = plugin.getGuildManager();
    private Inventory inv;

    public GuildApproveGui(String gid) {
        if (!guildManager.isExistsGuild(gid)) {
            throw new IllegalArgumentException("公会不存在!");
        }

        this.inv = Bukkit.createInventory(null, 54, TITLE);
        int counter = 0;

        for (UnapprovedPlayer unapprovedPlayer : plugin.getGuildManager().getGuildUnapprovedPlayers(gid)) {
            ItemStack item = new ItemStack(Material.SKULL_ITEM);
            String playerName = unapprovedPlayer.getPlayerName();

            item.setDurability((short) 3);

            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

            // 设置头颅主人
            skullMeta.setDisplayName("§e" + playerName);
            skullMeta.setOwner(playerName);
            skullMeta.setLore(Arrays.asList("§7- §d申请者 §b> §d" + playerName, "§7- §d申请时间 §b> §d" + MsgUtil.formatDate(unapprovedPlayer.getApplicationTime()), "§7- §a左键同意 §b/ §c右键拒绝"));
            item.setItemMeta(skullMeta);
            inv.setItem(counter, item);
            counter ++;
        }
    }

    @Override
    public void open(Player player) {
        player.openInventory(inv);
    }
}
