package vip.creeper.mcserverplugins.creeperguild.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by July on 2018/2/10.
 */
public class YmlUtil {
    public static void setLocForSection(ConfigurationSection section, Location loc) {
        section.set("world", loc.getWorld().getName());
        section.set("x", loc.getX());
        section.set("y", loc.getY());
        section.set("z", loc.getZ());
        section.set("yaw", loc.getYaw());
        section.set("pitch", loc.getPitch());
    }

    public static Location getLocFromSection(ConfigurationSection section) {
        return new Location(Bukkit.getWorld(section.getString("world")), section.getDouble("x"), section.getDouble("y"), section.getDouble("z"), Float.parseFloat(section.getString("yaw")), Float.parseFloat(section.getString("pitch")));
    }
}
