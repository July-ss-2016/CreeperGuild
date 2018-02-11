package vip.creeper.mcserverplugins.creeperguild.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import vip.creeper.mcserverplugins.creeperguild.utils.ItemUtil;

/**
 * Created by July on 2018/2/9.
 */
public class PageItem {
    public static final ItemStack LAST_PAGE_ITEM = new ItemStack(Material.BOOK);
    public static final ItemStack NEST_PAGE_ITEM = new ItemStack(Material.BOOK);

    static {
        ItemUtil.setDisplayName(LAST_PAGE_ITEM, "&a上一页");
        ItemUtil.setDisplayName(NEST_PAGE_ITEM, "&c下一页");
        ItemUtil.setLore(LAST_PAGE_ITEM, new String[] {"§7- §f点击换页"});
        ItemUtil.setLore(NEST_PAGE_ITEM, new String[] {"§7- §f点击换页"});
    }
}
