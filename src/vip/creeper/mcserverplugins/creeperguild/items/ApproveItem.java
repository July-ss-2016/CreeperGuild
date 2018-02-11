package vip.creeper.mcserverplugins.creeperguild.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

/**
 * Created by July on 2018/2/11.
 */
public class ApproveItem {
    public static final ItemStack APPROVED_ITEM = new ItemStack(Material.BARRIER) ;

    static {
        ItemMeta meta = APPROVED_ITEM.getItemMeta();

        meta.setLore(Collections.singletonList("§7- §c已处理"));
        APPROVED_ITEM.setItemMeta(meta);
    }
}
