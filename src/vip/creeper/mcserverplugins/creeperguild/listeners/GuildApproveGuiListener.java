package vip.creeper.mcserverplugins.creeperguild.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;
import vip.creeper.mcserverplugins.creeperguild.guis.GuildApproveGui;
import vip.creeper.mcserverplugins.creeperguild.guis.GuildListGui;
import vip.creeper.mcserverplugins.creeperguild.items.ApproveItem;
import vip.creeper.mcserverplugins.creeperguild.utils.ItemUtil;
import vip.creeper.mcserverplugins.creeperguild.utils.MsgUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by July on 2018/2/9.
 */
public class GuildApproveGuiListener implements Listener {
    private CreeperGuild plugin;

    public GuildApproveGuiListener(CreeperGuild plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();
        ClickType clickType = event.getClick();
        String title = inventory.getTitle();

        if (title.equals(GuildApproveGui.TITLE)) {
            String applicantName = getApplicantName(item);

            // 禁止拿走东西
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            player.updateInventory();

            if (applicantName == null) {
                return;
            }

            if (ApproveItem.APPROVED_ITEM.getItemMeta().getLore().equals(ItemUtil.getLores(item))) {
                return;
            }

            if (clickType == ClickType.LEFT) {
                setApproved(item);
                player.updateInventory();
                player.chat("/guild approve allow " +  applicantName);
                return;
            }

            if (clickType == ClickType.RIGHT) {
                setApproved(item);
                player.updateInventory();
                player.chat("/guild approve deny " +  applicantName);
            }
        }
    }

    private String getApplicantName(ItemStack item) {
        if (!ItemUtil.isValidItem(item)) {
            return null;
        }

        return ChatColor.stripColor(item.getItemMeta().getDisplayName());
    }

    private boolean setApproved(ItemStack item) {
        if (!ItemUtil.isValidItem(item)) {
            return false;
        }

        item.setType(Material.BARRIER);

        ItemMeta meta = item.getItemMeta();

        meta.setLore(Collections.singletonList("§7- §c已处理"));
        item.setItemMeta(meta);
        return true;
    }
}
