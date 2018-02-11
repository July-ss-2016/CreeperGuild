package vip.creeper.mcserverplugins.creeperguild.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by July on 2018/2/9.
 */
public class ItemUtil {
    public static boolean isValidItem(ItemStack item) {
        return item != null && item.getType() != Material.AIR;
    }

    public static boolean setDisplayName(ItemStack item, String displayName) {
        if (!isValidItem(item)) {
            return true;
        }

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(StrUtil.translateColorCode(displayName));

        item.setItemMeta(meta);

        return true;
    }

    public static boolean setLore(ItemStack item, String[] lores) {
        if (!isValidItem(item)) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();

        meta.setLore(Arrays.asList(StrUtil.translateColorCode(lores)));
        item.setItemMeta(meta);

        return true;
    }

    public static List<String> getLores(ItemStack item) {
        if (!isValidItem(item)) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();

        return meta.getLore();
    }
}
