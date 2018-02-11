package vip.creeper.mcserverplugins.creeperguild.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import sun.dc.pr.PRError;
import vip.creeper.mcserverplugins.creeperguild.guis.GuildApproveGui;
import vip.creeper.mcserverplugins.creeperguild.guis.GuildListGui;
import vip.creeper.mcserverplugins.creeperguild.items.ApproveItem;
import vip.creeper.mcserverplugins.creeperguild.items.PageItem;
import vip.creeper.mcserverplugins.creeperguild.utils.StrUtil;

/**
 * Created by July on 2018/2/10.
 */
public class GuildListGuiListener implements Listener {
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();
        String title = inventory.getTitle();

        if (title.startsWith(GuildListGui.TITLE_PREFIX)) {
            int currentPage = getCurrentPage(title);

            // 禁止拿走东西
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            player.updateInventory();

            if (PageItem.LAST_PAGE_ITEM.equals(item)) {
                player.chat("/guild list " + (currentPage - 1));
                return;
            }

            if (PageItem.NEST_PAGE_ITEM.equals(item)) {
                player.chat("/guild list " + (currentPage + 1));
            }
        }
    }

    private int getCurrentPage(String s) {
        if (s == null) {
            return -1;
        }

        return StrUtil.parseIntStr(s.replace(GuildListGui.TITLE_PREFIX, ""));
    }
}
